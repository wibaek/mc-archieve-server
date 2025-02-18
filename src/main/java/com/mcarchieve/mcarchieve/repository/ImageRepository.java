package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.domain.Image;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
