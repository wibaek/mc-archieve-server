package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.dto.user.ProfileResponse;

public record StoryResponse(
        Long id,
        String caption,
        String imageUrl,
        ProfileResponse createdBy
) {

    public static StoryResponse from(Story story) {
        return new StoryResponse(
                story.getId(),
                story.getCaption(),
                story.getImage().getPath(), // TODO: 저장소 uri와 합치기
                ProfileResponse.from(story.getCreatedBy())
        );
    }
}
