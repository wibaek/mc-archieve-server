package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.user.MyInfoResponse;
import com.mcarchieve.mcarchieve.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/my")
    public ResponseEntity<MyInfoResponse> getMyInfo(Principal principal) {
        MyInfoResponse myInfoResponse = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(myInfoResponse);
    }
}