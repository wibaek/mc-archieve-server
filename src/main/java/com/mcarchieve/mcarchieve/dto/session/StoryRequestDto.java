package com.mcarchieve.mcarchieve.dto.session;

import org.springframework.web.multipart.MultipartFile;

public class StoryRequestDto {
    private Long id;
    private String description;
    private MultipartFile image;
    private Long createdById;
    private Long sessionId;

    public StoryRequestDto() {
    }

    public StoryRequestDto(Long id, String description, MultipartFile image, Long createdById, Long sessionId) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.createdById = createdById;
        this.sessionId = sessionId;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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
