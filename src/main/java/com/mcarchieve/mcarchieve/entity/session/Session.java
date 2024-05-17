package com.mcarchieve.mcarchieve.entity.session;

import com.mcarchieve.mcarchieve.entity.user.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = true)
    private Server server;

    private LocalDate startDate;

    private LocalDate endDate;

    public Session() {
    }

    public Session(Long id, String name, User owner, Server server, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.server = server;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
