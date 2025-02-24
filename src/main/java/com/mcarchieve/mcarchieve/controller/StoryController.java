package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.StoryCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.StoryCreateResponse;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;
    private final UserRepository UserRepository;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoryCreateResponse> createStory(
            @RequestPart("request") @Valid StoryCreateRequest request,
            @RequestPart(value = "file") MultipartFile imageFile) {

        // TODO: 유저 정보 가져오기
        User user = UserRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        StoryCreateResponse response = storyService.createStory(request, imageFile, user);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<StoryResponseDto> getStoriesById(@PathVariable Long id) {
//        StoryResponseDto story = storyService.findStoryById(id);
//        return ResponseEntity.ok(story);
//    }
}
