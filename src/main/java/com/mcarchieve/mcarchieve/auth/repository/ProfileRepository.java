package com.mcarchieve.mcarchieve.auth.repository;

import com.mcarchieve.mcarchieve.auth.domain.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
