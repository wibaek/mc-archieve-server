package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Story;

import java.util.List;

public record StoryBulkCreateResponse(
        List<StoryResponse> stories,
        boolean isSuccess
) {
    public static StoryBulkCreateResponse from(List<Story> stories, String storageUrl) {
        List<StoryResponse> storyResponses = stories.stream()
                .map(story -> StoryResponse.from(story, storageUrl))
                .toList();

        return new StoryBulkCreateResponse(storyResponses, true);
    }
}
