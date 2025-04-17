package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.SessionMember;

import java.util.List;

public record SessionJoinApplicationsResponse(
        List<SessionJoinApplication> sessionJoinApplications
) {
    public static SessionJoinApplicationsResponse from(List<SessionMember> sessionMembers) {
        List<SessionJoinApplication> applications = sessionMembers.stream()
                .map(SessionJoinApplication::from)
                .toList();

        return new SessionJoinApplicationsResponse(applications);
    }
}
