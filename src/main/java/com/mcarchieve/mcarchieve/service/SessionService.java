package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionResponse createSession(SessionCreateRequest sessionCreateRequest, User owner) {
        Session session = sessionCreateRequest.toEntity(owner);
        session = sessionRepository.save(session);
        return SessionResponse.from(session);
    }

    public List<SessionResponse> findAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(SessionResponse::from)
                .collect(Collectors.toList());
    }

    public SessionResponse findSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("세션을 찾을 수 없습니다."));

        return SessionResponse.from(session);
    }
}
