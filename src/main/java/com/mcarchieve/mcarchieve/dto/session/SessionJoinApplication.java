package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.dto.user.ProfileResponse;

import java.time.LocalDateTime;

public record SessionJoinApplication(
        Long id,
        ProfileResponse user,
        MemberStatus status,
        LocalDateTime createdAt
) {
    public static SessionJoinApplication from(SessionMember sessionMember) {
        return new SessionJoinApplication(
                sessionMember.getId(),
                ProfileResponse.from(sessionMember.getUser()),
                sessionMember.getStatus(),
                sessionMember.getCreatedAt()
        );
    }
}