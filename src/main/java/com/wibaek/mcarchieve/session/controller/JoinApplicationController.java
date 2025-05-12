package com.wibaek.mcarchieve.session.controller;

import com.wibaek.mcarchieve.auth.domain.User;
import com.wibaek.mcarchieve.exception.CustomException;
import com.wibaek.mcarchieve.exception.ErrorCode;
import com.wibaek.mcarchieve.auth.repository.UserRepository;
import com.wibaek.mcarchieve.session.service.SessionJoinService;
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

    @PostMapping("/{applicationId}/approve")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long applicationId,
            Principal principal) {
        User requester = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        sessionJoinService.approveJoinRequest(applicationId, requester);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/reject")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long applicationId,
            Principal principal) {
        User requester = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        sessionJoinService.rejectJoinRequest(applicationId, requester);
        return ResponseEntity.ok().build();
    }
}
