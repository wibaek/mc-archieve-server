package com.mcarchieve.mcarchieve.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Image {

    @Column(nullable = false)
    private String path;

    @Column
    private String originalFileName;

    @Column
    private Long size;
}
