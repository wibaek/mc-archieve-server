package com.mcarchieve.mcarchieve.session.service;

import com.mcarchieve.mcarchieve.session.domain.MemberStatus;
import com.mcarchieve.mcarchieve.session.domain.Session;
import com.mcarchieve.mcarchieve.session.domain.SessionMember;
import com.mcarchieve.mcarchieve.auth.domain.User;
import com.mcarchieve.mcarchieve.session.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.session.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.SessionMemberRepository;
import com.mcarchieve.mcarchieve.auth.repository.UserRepository;
import com.mcarchieve.mcarchieve.session.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMemberRepository sessionMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public SessionResponse createSession(SessionCreateRequest sessionCreateRequest, User owner) {
        Session session = sessionCreateRequest.toEntity(owner);
        SessionMember sessionMember = new SessionMember(session, owner, MemberStatus.APPROVED);
        session = sessionRepository.save(session);
        sessionMemberRepository.save(sessionMember);
        return SessionResponse.from(session);
    }

    public List<SessionResponse> findAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(SessionResponse::from)
                .collect(Collectors.toList());
    }

    public List<SessionResponse> findAllMySessions(User user) {
        return sessionMemberRepository.findAllByUser(user)
                .stream()
                .map(sessionMember -> SessionResponse.from(sessionMember.getSession()))
                .collect(Collectors.toList());
    }

    public SessionResponse findSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        return SessionResponse.from(session);
    }


}
