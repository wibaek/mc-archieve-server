package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.user.User;

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
