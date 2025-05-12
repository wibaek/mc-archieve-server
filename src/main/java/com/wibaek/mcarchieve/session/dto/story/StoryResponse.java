package com.wibaek.mcarchieve.session.dto.story;

import com.wibaek.mcarchieve.session.domain.Story;
import com.wibaek.mcarchieve.auth.dto.user.ProfileResponse;

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
