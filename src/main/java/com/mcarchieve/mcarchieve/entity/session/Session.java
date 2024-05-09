package com.mcarchieve.mcarchieve.entity.session;

import com.mcarchieve.mcarchieve.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Session {

    public Session() {
    }

    public Session(Long id, String name, User owner, Server server) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.server = server;
    }

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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
