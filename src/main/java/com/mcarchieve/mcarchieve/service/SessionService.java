package com.mcarchieve.mcarchieve.service;

import org.springframework.stereotype.Service;

import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.dto.session.SessionRequestDto;
import com.mcarchieve.mcarchieve.dto.session.SessionResponseDto;
import com.mcarchieve.mcarchieve.repository.SessionRepository;

@Service
public class SessionService {

    SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public SessionResponseDto createSession(SessionRequestDto sessionDto) {
//        sessionDto.setId(null);
        Session session = sessionDto.toEntity();
        session = sessionRepository.save(session);
        return SessionResponseDto.fromEntity(session);
    }
}
