package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.session.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findBySessionId(Long sessionId);

    Long countBy();
}
