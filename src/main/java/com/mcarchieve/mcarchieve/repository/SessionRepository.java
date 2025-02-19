package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.domain.session.Session;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
