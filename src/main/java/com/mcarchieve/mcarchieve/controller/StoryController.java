package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.StoryCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.StoryResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/v1/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;
    private final UserRepository userRepository;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoryResponse> createStory(
            @RequestPart("request") @Valid StoryCreateRequest request,
            @RequestPart(value = "file") MultipartFile imageFile,
            Principal principal
    ) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StoryResponse response = storyService.createStory(request, imageFile, user);
        // TODO: .created() 메소드를 사용하여 생성된 리소스의 URI를 반환하도록 수정
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponse> getStoryById(@PathVariable Long id) {
        StoryResponse story = storyService.findStoryById(id);
        return ResponseEntity.ok(story);
    }
}
