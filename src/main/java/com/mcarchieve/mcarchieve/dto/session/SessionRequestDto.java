package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.entity.session.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SessionRequestDto {
    private String name;
    private Long ownerId;
    private Long serverId;
    private Instant startDate;
    private Instant endDate;

    public Session toEntity() {
        Session session = new Session();
        session.setName(this.name);
        session.setStartDate(this.startDate);
        session.setEndDate(this.endDate);

        return session;
    }
}
