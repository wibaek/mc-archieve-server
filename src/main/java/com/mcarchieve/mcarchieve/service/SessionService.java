package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.SessionMemberRepository;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
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
