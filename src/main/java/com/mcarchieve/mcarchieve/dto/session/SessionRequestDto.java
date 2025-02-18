package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.domain.session.Session;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class SessionRequestDto {
    private String name;
    private Long serverId;
    private LocalDate startDate;
    private LocalDate endDate;

    public Session toEntity() {
        Session session = new Session();
        session.setName(this.name);
        session.setStartDate(this.startDate);
        session.setEndDate(this.endDate);
        return session;
    }
}
