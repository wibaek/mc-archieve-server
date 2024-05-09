package com.mcarchieve.mcarchieve.entity.user;

import com.mcarchieve.mcarchieve.type.LoginType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class User {

    public User() {
    }

    public User(Long id, String email, Password password, Player player, LoginType loginType, LocalDateTime joinDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.player = player;
        this.loginType = loginType;
        this.joinDate = joinDate;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name="password_id", nullable = true)
    private Password password;

    @OneToOne
    @JoinColumn(name="profile_id", nullable = false)
    private Profile profile;

    @OneToOne
    @JoinColumn(name="player_id", nullable = true)
    private Player player;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @CreatedDate
    private LocalDateTime joinDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
