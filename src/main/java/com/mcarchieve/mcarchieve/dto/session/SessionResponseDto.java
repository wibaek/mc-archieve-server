package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
public class SessionResponseDto {
    private Long id;
    private String name;
    private Long ownerId;
    private Long serverId;
    private LocalDate startDate;
    private LocalDate endDate;

    public static SessionResponseDto fromEntity(Session session) {
        return SessionResponseDto.builder()
                .id(session.getId())
                .name(session.getName())
                .startDate(session.getStartDate())
                .endDate(session.getEndDate())
                .ownerId(session.getOwner() != null ? session.getOwner().getId() : null)
                .serverId(session.getServer() != null ? session.getServer().getId() : null)
                .build(
                );
    }
}
