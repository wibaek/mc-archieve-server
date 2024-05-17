package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.dto.session.SessionRequestDto;
import com.mcarchieve.mcarchieve.dto.session.SessionResponseDto;
import com.mcarchieve.mcarchieve.repository.SessionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionResponseDto createSession(SessionRequestDto sessionDto) {
        Session session = sessionDto.toEntity();
        session = sessionRepository.save(session);
        return SessionResponseDto.fromEntity(session);
    }

    public List<SessionResponseDto> findAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream()
                .map(SessionResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public SessionResponseDto findSessionById(Long id) {
        Session session = sessionRepository.findById(id).orElse(null);
        return session != null ? SessionResponseDto.fromEntity(session) : null;
    }

    public boolean deleteSessionById(Long id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isPresent()) {
            sessionRepository.delete(session.get());
            return true;
        } else {
            return false;
        }
    }
}
