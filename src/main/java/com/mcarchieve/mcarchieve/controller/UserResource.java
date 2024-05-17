package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.user.UserDto;
import com.mcarchieve.mcarchieve.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/my")
    public UserDto getMyInfo(Principal principal) {
        UserDto userDto = userService.getUserByEmail(principal.getName());
        return userDto;
    }
}