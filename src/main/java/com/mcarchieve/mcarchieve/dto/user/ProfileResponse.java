package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.domain.user.User;

public record ProfileResponse(
        String nickname,
        String profileImageUrl
) {
    public static ProfileResponse from(User user) {
        return new ProfileResponse(
                user.getProfile().getNickname(),
                user.getProfile().getProfileImage().getPath()
        );
    }
}
