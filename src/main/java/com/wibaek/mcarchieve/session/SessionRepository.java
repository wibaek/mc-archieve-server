package com.wibaek.mcarchieve.session;

import com.wibaek.mcarchieve.session.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Long countBy();
}
