package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.domain.user.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
