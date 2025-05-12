package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.stat.StatResponse;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.StoryRepository;
import com.mcarchieve.mcarchieve.auth.repository.UserRepository;
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
