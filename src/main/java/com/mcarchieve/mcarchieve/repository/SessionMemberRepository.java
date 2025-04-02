package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionMemberRepository extends JpaRepository<SessionMember, Long> {

    Optional<SessionMember> findBySessionAndUser(Session session, User user);

    List<SessionMember> findAllByUser(User user);
}
