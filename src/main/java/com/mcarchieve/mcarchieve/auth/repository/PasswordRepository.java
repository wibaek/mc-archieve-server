package com.mcarchieve.mcarchieve.auth.repository;

import com.mcarchieve.mcarchieve.auth.domain.Password;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
