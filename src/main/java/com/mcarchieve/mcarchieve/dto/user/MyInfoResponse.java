package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.type.LoginType;

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
