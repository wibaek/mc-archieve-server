package com.mcarchieve.mcarchieve.domain.session;

import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.user.User;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToOne
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}
