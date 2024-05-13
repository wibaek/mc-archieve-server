package com.mcarchieve.mcarchieve.dto.user;

import com.mcarchieve.mcarchieve.entity.user.Profile;

public class ProfileDto {
    private Long id;

    private String nickname;

    private String profileImageUrl;

    public ProfileDto(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public ProfileDto(Profile profile) {
        this.id = profile.getId();
        this.nickname = profile.getNickname();
        this.profileImageUrl = profile.getProfileImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
