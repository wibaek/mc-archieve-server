package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.UserDto;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my")
    public UserDto getMyInfo(Principal principal) {
        UserDto userDto = userService.getUserByEmail(principal.getName());
        return userDto;
    }
}