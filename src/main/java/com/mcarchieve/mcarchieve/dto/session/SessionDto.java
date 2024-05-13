package com.mcarchieve.mcarchieve.dto.session;

import com.mcarchieve.mcarchieve.entity.session.Session;

import java.time.Instant;
import java.time.LocalDateTime;

public class SessionDto {
    private Long id;
    private String name;
    private Long ownerId;
    private Long serverId;
    private Instant startDate;
    private Instant endDate;

    public SessionDto() {
    }

    public SessionDto(Long id, String name, Long ownerId, Long serverId, Instant startDate, Instant endDate) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.serverId = serverId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session toEntity() {
        Session session = new Session();
        session.setId(this.id);
        session.setName(this.name);
        session.setStartDate(this.startDate);
        session.setEndDate(this.endDate);
        return session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwner(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServer(Long serverId) {
        this.serverId = serverId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
