package com.mcarchieve.mcarchieve.session.dto.application;

import com.mcarchieve.mcarchieve.session.domain.MemberStatus;
import com.mcarchieve.mcarchieve.session.domain.SessionMember;
import com.mcarchieve.mcarchieve.auth.dto.user.ProfileResponse;

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