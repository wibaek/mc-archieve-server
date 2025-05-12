package com.wibaek.mcarchieve.auth.domain;

import com.wibaek.mcarchieve.common.entity.BaseEntity;
import com.wibaek.mcarchieve.session.domain.SessionMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id", nullable = true)
    private Password password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = true)
    private Player player;

    @OneToMany(mappedBy = "user")
    private List<SessionMember> sessions = new ArrayList<>();

    private User(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public static User createUser(LoginType loginType, String nickname) {
        User user = new User(loginType);
        Profile profile = new Profile(user, nickname);
        return user;
    }

    public static User createEmailUser(String email, Password password, String nickname) {
        User user = createUser(LoginType.BASIC, nickname);
        user.email = email;
        user.password = password;
        return user;
    }
}
