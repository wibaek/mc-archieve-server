package com.mcarchieve.mcarchieve.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @LastModifiedDate
    private Instant updatedAt;

    public Password() {
    }

    public Password(Long id, String password, Instant updatedAt) {
        this.id = id;
        this.password = password;
        this.updatedAt = updatedAt;
    }
}
