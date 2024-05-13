package com.mcarchieve.mcarchieve.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mcarchieve.mcarchieve.entity.user.Password;


@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
