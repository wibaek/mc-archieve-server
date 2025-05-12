package com.mcarchieve.mcarchieve.common.service.image;

import com.mcarchieve.mcarchieve.common.entity.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class R2ImageStorageService implements ImageStorageService {

    private final S3Client s3Client;

    @Value("${cloud.cloudflare.r2.bucket}")
    private String bucketName;

    @Override
    public Image storeImage(MultipartFile file, FileUploadPath path) {
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString();
        String key = path.getPath() + uniqueFileName + ext;
        return uploadImage(file, key);
    }

    @Retryable(value = {SdkClientException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000))
    private Image uploadImage(MultipartFile file, String key) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return new Image(key, file.getOriginalFilename(), file.getSize());
        } catch (IOException e) {
            log.error("R2 이미지 업로드 중 IOException 발생: {}", e.getMessage(), e);
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }

    @Recover
    public Image recover(SdkClientException e, MultipartFile file, String key) {
        log.error("R2 이미지 업로드 중 SdkClientException 발생: {}", e.getMessage(), e);
        throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
    }
}
