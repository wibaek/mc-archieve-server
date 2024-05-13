package com.mcarchieve.mcarchieve.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mcarchieve.mcarchieve.entity.session.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{
}
