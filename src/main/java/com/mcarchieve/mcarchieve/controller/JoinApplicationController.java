package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.SessionJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/join-applications")
@RequiredArgsConstructor
public class JoinApplicationController {

    private final SessionJoinService sessionJoinService;
    private final UserRepository userRepository;

    @PostMapping("/join-applications/{applicationId}/approve")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long sessionId,
            @PathVariable Long applicationId,
            Principal principal) {
        User requester = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        sessionJoinService.approveJoinRequest(applicationId, requester);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join-applications/{applicationId}/reject")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long sessionId,
            @PathVariable Long applicationId,
            Principal principal) {
        User requester = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        sessionJoinService.rejectJoinRequest(applicationId, requester);
        return ResponseEntity.ok().build();
    }
}
