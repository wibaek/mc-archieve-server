package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.session.StoryResponseDto;
import com.mcarchieve.mcarchieve.service.StoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stories")
public class StoryResource {

    StoryService storyService;

    public StoryResource(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseDto> getStoriesById(@PathVariable Long id) {
        StoryResponseDto story = storyService.findStoryById(id);
        return ResponseEntity.ok(story);
    }
}
