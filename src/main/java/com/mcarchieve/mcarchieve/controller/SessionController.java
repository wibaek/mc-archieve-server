package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.dto.session.StoryResponse;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.SessionService;
import com.mcarchieve.mcarchieve.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final StoryService storyService;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<SessionResponse> createSession(@Valid @RequestBody SessionCreateRequest sessionCreateRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        SessionResponse response = sessionService.createSession(sessionCreateRequest, user);
        // TODO: .created() 메소드를 사용하여 생성된 리소스의 URI를 반환하도록 수정
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions() {
        List<SessionResponse> sessions = sessionService.findAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long id) {
        SessionResponse session = sessionService.findSessionById(id);
        return ResponseEntity.ok(session);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
//        boolean isDeleted = sessionService.deleteSessionById(id);
//        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }

    @GetMapping("/{id}/stories")
    public ResponseEntity<List<StoryResponse>> getStoriesBySessionId(@PathVariable Long id) {
        List<StoryResponse> stories = storyService.findStoriesBySessionId(id);
        return ResponseEntity.ok(stories);
    }
}
