package com.mcarchieve.mcarchieve.entity.session;

import com.mcarchieve.mcarchieve.entity.user.User;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
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
}
