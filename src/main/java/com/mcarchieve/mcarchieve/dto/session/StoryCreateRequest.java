package com.mcarchieve.mcarchieve.dto.session;

import jakarta.validation.constraints.NotNull;

public record StoryCreateRequest(

        @NotNull(message = "세션 ID를 설정해주세요.")
        Long sessionId,

        String caption
) {
}
