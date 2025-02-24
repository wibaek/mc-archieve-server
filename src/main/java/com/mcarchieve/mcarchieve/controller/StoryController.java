package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.session.StoryResponseDto;
import com.mcarchieve.mcarchieve.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseDto> getStoriesById(@PathVariable Long id) {
        StoryResponseDto story = storyService.findStoryById(id);
        return ResponseEntity.ok(story);
    }
}
