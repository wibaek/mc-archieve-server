package com.wibaek.mcarchieve.auth.controller;

import com.wibaek.mcarchieve.auth.dto.user.MyInfoResponse;
import com.wibaek.mcarchieve.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/v1/my")
    public ResponseEntity<MyInfoResponse> getMyInfo(Principal principal) {
        MyInfoResponse response = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(response);
    }
}