package com.mcarchieve.mcarchieve.dto.session;

import lombok.*;

import com.mcarchieve.mcarchieve.entity.session.Story;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
public class StoryResponseDto {
    private Long id;
    private String description;
    private String imageUri;
    private Long createdById;
    private Long sessionId;

    public static StoryResponseDto fromEntity(Story story, String imageRepositoryUri) {
        return StoryResponseDto.builder()
                .id(story.getId())
                .description(story.getDescription())
                .createdById(story.getCreatedBy().getId())
                .sessionId(story.getSession().getId())
                .imageUri(story.getImage() != null ? imageRepositoryUri + story.getImage().getPath() : null)
                .build();
    }
}
