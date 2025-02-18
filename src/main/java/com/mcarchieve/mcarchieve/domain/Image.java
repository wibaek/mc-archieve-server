package com.mcarchieve.mcarchieve.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String originalFileName;

    public Image() {
    }

    public Image(Long id, String path, String originalFileName) {
        this.id = id;
        this.path = path;
        this.originalFileName = originalFileName;
    }
}
