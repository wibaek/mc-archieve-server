package com.mcarchieve.mcarchieve.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;

    private String playerName;

}
