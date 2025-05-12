package com.wibaek.mcarchieve.repository;

import com.wibaek.mcarchieve.session.domain.MemberStatus;
import com.wibaek.mcarchieve.session.domain.Session;
import com.wibaek.mcarchieve.session.domain.SessionMember;
import com.wibaek.mcarchieve.auth.domain.User;
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
