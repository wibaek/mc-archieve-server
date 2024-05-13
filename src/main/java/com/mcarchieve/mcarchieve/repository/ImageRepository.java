package com.mcarchieve.mcarchieve.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mcarchieve.mcarchieve.entity.Image;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
