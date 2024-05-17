package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.entity.user.Password;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
