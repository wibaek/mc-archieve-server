package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.entity.session.Story;
import org.springframework.web.multipart.MultipartFile;

public class StoryResponseDto {
    private Long id;
    private String description;
    private String imageUri;
    private Long createdById;
    private Long sessionId;

    public StoryResponseDto() {
    }

    public StoryResponseDto(Long id, String description, String imageUri, Long createdById, Long sessionId) {
        this.id = id;
        this.description = description;
        this.imageUri = imageUri;
        this.createdById = createdById;
        this.sessionId = sessionId;
    }

    public StoryResponseDto(Story story) {
        this.id = story.getId();
        this.description = story.getDescription();
        if (story.getImage() != null){
            this.imageUri = story.getImage().getPath();
        }
        this.createdById = story.getCreatedBy().getId();
        this.sessionId = story.getSession().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
