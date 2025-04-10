package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.dto.user.ProfileResponse;

public record StoryResponse(
        Long id,
        String caption,
        String imageUrl,
        ProfileResponse createdBy
) {

    public static StoryResponse from(Story story, String storageUri) {
        return new StoryResponse(
                story.getId(),
                story.getCaption(),
                storageUri + story.getImage().getPath(),
                ProfileResponse.from(story.getCreatedBy())
        );
    }
}
