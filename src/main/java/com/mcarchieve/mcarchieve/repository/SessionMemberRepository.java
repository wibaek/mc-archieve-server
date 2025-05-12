package com.mcarchieve.mcarchieve.repository;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionMemberRepository extends JpaRepository<SessionMember, Long> {

    Optional<SessionMember> findBySessionAndUser(Session session, User user);

    Optional<SessionMember> findAllBySession(Session session);

    List<SessionMember> findAllBySessionAndStatus(Session session, MemberStatus status);

    List<SessionMember> findAllByUser(User user);
}
