package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryDto {
    private Long id;
    private String description;
    private Image image;
    private User createdBy;
    private Long sessionId;

    public StoryDto() {
    }

    public StoryDto(Long id, String description, Image image, User createdBy, Long sessionId) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.createdBy = createdBy;
        this.sessionId = sessionId;
    }
}
