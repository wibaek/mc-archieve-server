package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.user.LoginDto;
import com.mcarchieve.mcarchieve.dto.user.SignupDto;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.service.JwtService;
import com.mcarchieve.mcarchieve.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    JwtService jwtService;

    UserService userService;

    AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(JwtService jwtService, UserService userService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.createJwt(authentication);

        return jwt;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> basicSignup(@RequestBody SignupDto signupDto) {

        return ResponseEntity.ok(userService.signup(signupDto));
    }

    @PostMapping("/validate")
    public boolean validateToken(@RequestParam("jwt") String jwt) {
        return jwtService.isValidToken(jwt);
    }

}
