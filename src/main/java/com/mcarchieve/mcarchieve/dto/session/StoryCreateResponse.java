package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.dto.user.ProfileResponse;

public record StoryCreateResponse(
        Long id,
        String caption,
        String imageUrl,
        ProfileResponse createdBy
) {

    public static StoryCreateResponse from(Story story) {
        return new StoryCreateResponse(
                story.getId(),
                story.getCaption(),
                story.getImage().getPath(), // TODO: 저장소 uri와 합치기
                ProfileResponse.from(story.getCreatedBy())
        );
    }
}
