package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.entity.Image;
import com.mcarchieve.mcarchieve.repository.ImageRepository;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3Service {

    private AmazonS3Client s3Client;

    private ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    String bucketName;

    public S3Service(AmazonS3Client s3Client,
                     ImageRepository imageRepository) {
        this.s3Client = s3Client;
        this.imageRepository = imageRepository;
    }

    public Image uploadFile(MultipartFile file, String path) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());


        s3Client.putObject(bucketName, path, file.getInputStream(), metadata);

//        String uploadPath = s3Client.getUrl(bucketName, path).toString();
        Image image = new Image(null, path, file.getOriginalFilename());
        return imageRepository.save(image);
    }
}
