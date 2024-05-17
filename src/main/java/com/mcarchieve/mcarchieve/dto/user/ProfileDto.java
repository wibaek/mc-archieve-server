package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.entity.user.Profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    public ProfileDto(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static ProfileDto fromEntity(Profile profile) {
        return new ProfileDto(profile.getId(), profile.getNickname(), profile.getProfileImageUrl());
    }
}
