package com.mcarchieve.mcarchieve.dto.session;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
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
}
