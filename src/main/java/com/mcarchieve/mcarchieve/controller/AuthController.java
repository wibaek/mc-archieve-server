package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.user.EmailLoginRequest;
import com.mcarchieve.mcarchieve.dto.user.SignupDto;
import com.mcarchieve.mcarchieve.service.JwtService;
import com.mcarchieve.mcarchieve.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/v1/login")
    public String authenticate(@RequestBody EmailLoginRequest emailLoginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailLoginRequest.email(), emailLoginRequest.password());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.createJwt(authentication);

        return jwt;
    }

    @PostMapping("/v1/signup")
    public ResponseEntity<User> basicSignup(@RequestBody SignupDto signupDto) {

        return ResponseEntity.ok(userService.signup(signupDto));
    }

    @PostMapping("/v1/validate")
    public boolean validateToken(@RequestParam("jwt") String jwt) {
        return jwtService.isValidToken(jwt);
    }

}
