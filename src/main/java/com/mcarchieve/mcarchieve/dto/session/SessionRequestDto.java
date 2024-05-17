package com.mcarchieve.mcarchieve.dto.session;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import com.mcarchieve.mcarchieve.entity.session.Session;



@Getter
@Setter
public class SessionRequestDto {
    private String name;
    private Long ownerId;
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
