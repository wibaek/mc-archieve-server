package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.user.UserDto;
import com.mcarchieve.mcarchieve.service.UserService;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/my")
    public ResponseEntity<UserDto> getMyInfo(Principal principal) {
        UserDto userDto = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(userDto);
    }
}