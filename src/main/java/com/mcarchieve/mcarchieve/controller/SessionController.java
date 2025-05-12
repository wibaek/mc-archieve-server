package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.auth.domain.User;
import com.mcarchieve.mcarchieve.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.SessionJoinApplicationsResponse;
import com.mcarchieve.mcarchieve.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.dto.session.StoryBulkCreateResponse;
import com.mcarchieve.mcarchieve.dto.session.StoryResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.auth.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.SessionJoinService;
import com.mcarchieve.mcarchieve.service.SessionService;
import com.mcarchieve.mcarchieve.service.StoryService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final SessionJoinService sessionJoinService;
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

    @GetMapping("/my")
    public ResponseEntity<List<SessionResponse>> getAllMySessions(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<SessionResponse> sessions = sessionService.findAllMySessions(user);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long id) {
        SessionResponse session = sessionService.findSessionById(id);
        return ResponseEntity.ok(session);
    }

    @PostMapping(path = "/{id}/stories",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoryResponse> createStory(
            @PathVariable Long id,
            @RequestPart("caption") @Nullable String caption,
            @RequestPart(value = "file") MultipartFile imageFile,
            Principal principal
    ) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StoryResponse response = storyService.createStory(id, imageFile, user, caption);
        // TODO: .created() 메소드를 사용하여 생성된 리소스의 URI를 반환하도록 수정
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "{id}/stories/bulk",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoryBulkCreateResponse> createStoryBulk(
            @PathVariable Long id,
            @RequestPart(value = "files") List<MultipartFile> imageFiles,
            Principal principal
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        StoryBulkCreateResponse responses = storyService.createStories(id, imageFiles, user);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}/stories")
    public ResponseEntity<List<StoryResponse>> getStoriesBySessionId(@PathVariable Long id) {
        List<StoryResponse> stories = storyService.findStoriesBySessionId(id);
        return ResponseEntity.ok(stories);
    }

    // 세션 참가 신청 관련
    @PostMapping("/{id}/join")
    public ResponseEntity<?> requestToJoinSession(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        sessionJoinService.requestToJoinSession(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/join-applications")
    public ResponseEntity<SessionJoinApplicationsResponse> getJoinRequests(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        SessionJoinApplicationsResponse response = sessionJoinService.findJoinRequestsBySessionId(id);
        return ResponseEntity.ok(response);
    }


}
