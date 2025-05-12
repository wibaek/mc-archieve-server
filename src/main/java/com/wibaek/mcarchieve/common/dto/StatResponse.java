package com.wibaek.mcarchieve.common.dto;

public record StatResponse(
        Long sessionCount,
        Long storyCount,
        Long userCount
) {
    public static StatResponse from(Long sessionCount, Long storyCount, Long userCount) {
        return new StatResponse(sessionCount, storyCount, userCount);
    }
}
