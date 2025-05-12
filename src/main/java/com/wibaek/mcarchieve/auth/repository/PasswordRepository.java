package com.wibaek.mcarchieve.auth.repository;

import com.wibaek.mcarchieve.auth.domain.Password;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
