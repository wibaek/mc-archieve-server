package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.session.SessionRequestDto;
import com.mcarchieve.mcarchieve.dto.session.SessionResponseDto;
import com.mcarchieve.mcarchieve.dto.session.StoryRequestDto;
import com.mcarchieve.mcarchieve.dto.session.StoryResponseDto;
import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.SessionService;
import com.mcarchieve.mcarchieve.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/sessions")
public class SessionResource {

    private SessionService sessionService;

    private StoryService storyService;

    private UserRepository userRepository;

    public SessionResource(SessionService sessionService, StoryService storyService, UserRepository userRepository) {
        this.sessionService = sessionService;
        this.storyService = storyService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<SessionResponseDto> createSession(@Valid @RequestBody SessionRequestDto sessionDto) {
        SessionResponseDto createdSessionDto = sessionService.createSession(sessionDto);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSessionDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdSessionDto);
    }

    @GetMapping
    public ResponseEntity<List<SessionResponseDto>> getAllSessions() {
        List<SessionResponseDto> sessions = sessionService.findAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDto> getSessionById(@PathVariable Long id) {
        SessionResponseDto session = sessionService.findSessionById(id);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
        boolean isDeleted = sessionService.deleteSessionById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
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

            StoryResponseDto storyResponseDto = storyService.createStory(storyRequestDto);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(storyResponseDto.getId())
                    .toUri();

            return ResponseEntity.created(location).body(storyResponseDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
