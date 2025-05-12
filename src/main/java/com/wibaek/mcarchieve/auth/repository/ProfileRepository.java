package com.wibaek.mcarchieve.auth.repository;

import com.wibaek.mcarchieve.auth.domain.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
