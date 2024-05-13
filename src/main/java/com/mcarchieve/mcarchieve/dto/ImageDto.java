package com.mcarchieve.mcarchieve.dto;

import com.mcarchieve.mcarchieve.entity.Image;

public class ImageDto {
    private Long id;
    private String fileName;
    private String originalFileName;

    public ImageDto(Long id, String fileName, String originalFileName) {
        this.id = id;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }

    public Image toEntity() {
        return new Image(id, fileName, originalFileName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
