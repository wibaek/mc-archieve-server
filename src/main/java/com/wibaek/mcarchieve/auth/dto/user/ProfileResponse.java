package com.wibaek.mcarchieve.auth.dto.user;

import com.wibaek.mcarchieve.common.entity.Image;
import com.wibaek.mcarchieve.auth.domain.User;

import java.util.Optional;

public record ProfileResponse(
        String nickname,
        String profileImageUrl
) {

    public static ProfileResponse from(User user) {
        return new ProfileResponse(
                user.getProfile().getNickname(),
                Optional.ofNullable(user.getProfile().getProfileImage())
                        .map(Image::getPath)
                        .orElse(null));
    }
}
