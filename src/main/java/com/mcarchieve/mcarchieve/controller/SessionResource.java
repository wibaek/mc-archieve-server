package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.session.SessionDto;
import com.mcarchieve.mcarchieve.dto.session.StoryRequestDto;
import com.mcarchieve.mcarchieve.dto.session.StoryResponseDto;
import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.entity.session.Story;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.StoryRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionResource {
    private SessionRepository sessionRepository;

    private StoryService storyService;

    private UserRepository userRepository;

    public SessionResource(SessionRepository sessionRepository, StoryRepository storyRepository, StoryService storyService, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.storyService = storyService;
        this.userRepository = userRepository;
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

    @GetMapping("/{id}/stories")
    public ResponseEntity<List<StoryResponseDto>> getStoriesBySessionId(@PathVariable Long id) {
        List<StoryResponseDto> stories = storyService.findStoriesBySessionId(id);
        return ResponseEntity.ok(stories);
    }

    @PostMapping(path = "/{id}/stories", consumes = "multipart/form-data")
    public ResponseEntity<StoryResponseDto> createStory(StoryRequestDto storyRequestDto, Principal principal, @PathVariable Long id) {
        try {
            User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
            storyRequestDto.setCreatedById(user.getId());
            storyRequestDto.setSessionId(id);
            Story story = storyService.createStory(storyRequestDto);
            StoryResponseDto storyResponseDto = new StoryResponseDto(story.getId(), story.getDescription(), story.getImage().getPath(), story.getCreatedBy().getId(), story.getSession().getId());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(story.getId())
                    .toUri();

            return ResponseEntity.created(location).body(storyResponseDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
