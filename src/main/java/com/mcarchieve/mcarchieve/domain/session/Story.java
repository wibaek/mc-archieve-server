package com.mcarchieve.mcarchieve.domain.session;

import com.mcarchieve.mcarchieve.domain.BaseEntity;
import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    public Story(String caption, Image image, User createdBy) {
        this.caption = caption;
        this.image = image;
        this.createdBy = createdBy;
    }
}
