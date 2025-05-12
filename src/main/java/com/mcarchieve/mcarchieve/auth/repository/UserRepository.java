package com.mcarchieve.mcarchieve.auth.repository;

import com.mcarchieve.mcarchieve.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Long countBy();
}
