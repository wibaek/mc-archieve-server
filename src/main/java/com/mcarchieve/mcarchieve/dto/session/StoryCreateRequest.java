package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.domain.user.User;
import jakarta.validation.constraints.NotNull;

public record StoryCreateRequest(

        @NotNull(message = "세션 ID를 설정해주세요.")
        Long sessionId,

        String caption
) {

    public Story toEntity(Image image, User createdBy, Session session) {
        Story story = new Story(
                caption,
                image,
                createdBy,
                session
        );
        return story;
    }
}
