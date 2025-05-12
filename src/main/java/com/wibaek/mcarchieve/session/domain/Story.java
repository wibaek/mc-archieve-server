package com.wibaek.mcarchieve.session.domain;

import com.wibaek.mcarchieve.common.entity.BaseEntity;
import com.wibaek.mcarchieve.common.entity.Image;
import com.wibaek.mcarchieve.auth.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Story extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    @Embedded
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    public Story(String caption, Image image, User createdBy, Session session) {
        this.caption = caption;
        this.image = image;
        this.createdBy = createdBy;
        this.session = session;
    }
}
