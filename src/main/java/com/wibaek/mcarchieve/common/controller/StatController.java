package com.wibaek.mcarchieve.common.controller;

import com.wibaek.mcarchieve.common.dto.StatResponse;
import com.wibaek.mcarchieve.session.SessionRepository;
import com.wibaek.mcarchieve.repository.StoryRepository;
import com.wibaek.mcarchieve.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stats")
@RequiredArgsConstructor
public class StatController {

    private final SessionRepository sessionRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<StatResponse> getTotalStats() {
        Long sessionCount = sessionRepository.countBy();
        Long storyCount = storyRepository.countBy();
        Long userCount = userRepository.countBy();

        StatResponse response = StatResponse.from(sessionCount, storyCount, userCount);
        return ResponseEntity.ok(response);
    }
}
