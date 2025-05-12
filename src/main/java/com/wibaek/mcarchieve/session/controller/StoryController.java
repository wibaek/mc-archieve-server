package com.wibaek.mcarchieve.session.controller;

import com.wibaek.mcarchieve.session.dto.story.StoryResponse;
import com.wibaek.mcarchieve.session.service.StoryService;
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
    public ResponseEntity<StoryResponse> getStoryById(@PathVariable Long id) {
        StoryResponse story = storyService.findStoryById(id);
        return ResponseEntity.ok(story);
    }
}
