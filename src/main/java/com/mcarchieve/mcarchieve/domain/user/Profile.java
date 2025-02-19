package com.mcarchieve.mcarchieve.domain.user;

import com.mcarchieve.mcarchieve.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Profile {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String nickname;

    @Embedded
    private Image profileImage;

    public Profile(User user, String nickname) {
        this.user = user;
        this.nickname = nickname;
    }
}
