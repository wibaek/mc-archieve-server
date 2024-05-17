package com.mcarchieve.mcarchieve.entity.user;

import java.time.Instant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mcarchieve.mcarchieve.type.LoginType;


@Getter
@Setter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    public User() {
    }

    public User(Long id, String email, Password password, Profile profile, Player player, LoginType loginType, Instant joinDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.player = player;
        this.loginType = loginType;
        this.joinDate = joinDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "password_id", nullable = true)
    private Password password;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @OneToOne
    @JoinColumn(name = "player_id", nullable = true)
    private Player player;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant joinDate;


}
