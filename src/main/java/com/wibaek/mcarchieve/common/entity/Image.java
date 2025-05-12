package com.wibaek.mcarchieve.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Image {

    @Column()
    private String path;

    @Column
    private String originalFileName;

    @Column
    private Long size;

    public Image(String path, String originalFileName, Long size) {
        this.path = path;
        this.originalFileName = originalFileName;
        this.size = size;
    }
}
