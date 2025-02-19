package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Story;

import lombok.*;

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
                .createdById(story.getCreatedBy() != null ? story.getCreatedBy().getId() : null)
                .sessionId(story.getSession().getId())
                .imageUri(story.getImage() != null ? imageRepositoryUri + story.getImage().getPath() : null)
                .build();
    }
}
