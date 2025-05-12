package com.wibaek.mcarchieve.common.service.image;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wibaek.mcarchieve.common.entity.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ImageStorageService implements ImageStorageService {

    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
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
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(bucketName, key, file.getInputStream(), metadata);

            return new Image(key, file.getOriginalFilename(), file.getSize());
        } catch (IOException e) {
            log.error("S3 이미지 업로드 중 IOException 발생: {}", e.getMessage(), e);
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }

    @Recover
    public Image recover(SdkClientException e, MultipartFile file, String key) {
        log.error("S3 이미지 업로드 중 SdkClientException 발생: {}", e.getMessage(), e);
        throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
    }
}
