package com.mcarchieve.mcarchieve.entity.session;


import jakarta.persistence.*;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mcarchieve.mcarchieve.entity.Image;
import com.mcarchieve.mcarchieve.entity.user.User;


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
