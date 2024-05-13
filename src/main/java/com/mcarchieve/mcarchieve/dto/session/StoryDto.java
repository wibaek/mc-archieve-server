package com.mcarchieve.mcarchieve.dto.session;


import com.mcarchieve.mcarchieve.entity.Image;
import com.mcarchieve.mcarchieve.entity.user.User;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
