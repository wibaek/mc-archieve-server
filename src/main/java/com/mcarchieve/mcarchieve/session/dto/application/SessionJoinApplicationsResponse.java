package com.mcarchieve.mcarchieve.session.dto.application;

import com.mcarchieve.mcarchieve.session.domain.SessionMember;

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
