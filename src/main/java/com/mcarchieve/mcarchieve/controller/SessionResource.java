package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.SessionDto;
import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionResource {
    private SessionRepository sessionRepository;

    public SessionResource(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @PostMapping
    public ResponseEntity<Session> createSession(@Valid @RequestBody SessionDto sessionDto) {
        Session session = sessionDto.toEntity();
        session.setId(null);
        Session savedSession = sessionRepository.save(session);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSession.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedSession);
    }

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long id) {
        return sessionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
        return sessionRepository.findById(id)
                .map(session -> {
                    sessionRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
