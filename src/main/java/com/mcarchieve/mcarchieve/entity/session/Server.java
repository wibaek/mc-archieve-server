package com.mcarchieve.mcarchieve.entity.session;

import com.mcarchieve.mcarchieve.entity.user.User;
import jakarta.persistence.*;

@Entity
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;
}
