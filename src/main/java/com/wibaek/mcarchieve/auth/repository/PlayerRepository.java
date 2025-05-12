package com.wibaek.mcarchieve.auth.repository;

import com.wibaek.mcarchieve.auth.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
