package com.mcarchieve.mcarchieve.auth.dto.user;

import com.mcarchieve.mcarchieve.auth.domain.LoginType;
import com.mcarchieve.mcarchieve.auth.domain.User;

import java.time.LocalDateTime;

public record MyInfoResponse(
        Long id,
        String email,
        LoginType loginType,
        LocalDateTime joinDate,
        ProfileResponse profile
) {

    public static MyInfoResponse from(User user) {
        return new MyInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getLoginType(),
                user.getCreatedAt(),
                ProfileResponse.from(user)
        );
    }
}
