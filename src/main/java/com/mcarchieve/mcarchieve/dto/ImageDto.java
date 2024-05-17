package com.mcarchieve.mcarchieve.dto;

import com.mcarchieve.mcarchieve.entity.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
