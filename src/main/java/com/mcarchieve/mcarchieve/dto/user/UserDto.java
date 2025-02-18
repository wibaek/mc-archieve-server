package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.type.LoginType;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
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
        this.profile = ProfileDto.fromEntity(user.getProfile());
//        this.player = user.getPlayer();
        this.loginType = user.getLoginType();
        this.joinDate = user.getJoinDate();
    }
}
