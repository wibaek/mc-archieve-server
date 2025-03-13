package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.dto.user.ProfileResponse;

import java.time.LocalDate;

public record SessionResponse(
        Long id,
        String name,
        ProfileResponse owner,
        LocalDate startDate,
        LocalDate endDate
) {

    public static SessionResponse from(Session session) {
        return new SessionResponse(
                session.getId(),
                session.getName(),
                ProfileResponse.from(session.getOwner()),
                session.getStartDate(),
                session.getEndDate()
        );
    }
}
