package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.dto.user.ProfileDto;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.type.LoginType;

import java.time.Instant;

public class UserDto {

    private Long id;
    private String email;
    private LoginType loginType;
    private Instant joinDate;
    private ProfileDto profile;

    public UserDto(Long id, String email, LoginType loginType, Instant joinDate, ProfileDto profile) {
        this.id = id;
        this.email = email;
        this.loginType = loginType;
        this.joinDate = joinDate;
        this.profile = profile;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
//        this.password = user.getPassword();
        this.profile = new ProfileDto(user.getProfile());
//        this.player = user.getPlayer();
        this.loginType = user.getLoginType();
        this.joinDate = user.getJoinDate();
    }

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

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }
}
