package com.mcarchieve.mcarchieve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Embeddable
public class Image {

    @Column(nullable = false)
    private String path;

    @Column
    private String originalFileName;

    @Column
    private Long size;

}
