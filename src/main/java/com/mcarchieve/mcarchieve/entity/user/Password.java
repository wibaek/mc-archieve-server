package com.mcarchieve.mcarchieve.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Password {
    @Id
    @GeneratedValue
    private Long id;

    private String password;

    private LocalDateTime updatedAt;
}
