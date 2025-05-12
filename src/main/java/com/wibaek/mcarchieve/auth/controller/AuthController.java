package com.wibaek.mcarchieve.auth.controller;

import com.wibaek.mcarchieve.auth.domain.User;
import com.wibaek.mcarchieve.auth.dto.auth.EmailLoginRequest;
import com.wibaek.mcarchieve.auth.dto.auth.EmailSignUpRequest;
import com.wibaek.mcarchieve.auth.dto.auth.LoginResponse;
import com.wibaek.mcarchieve.auth.dto.user.ProfileResponse;
import com.wibaek.mcarchieve.exception.CustomException;
import com.wibaek.mcarchieve.exception.ErrorCode;
import com.wibaek.mcarchieve.auth.service.JwtService;
import com.wibaek.mcarchieve.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    //    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid EmailLoginRequest emailLoginRequest) {
        // AuthenticationManager로 인증 시도, 이는 기존의 username/password 방식으로 인증을 시도하는 것과 동일
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            emailLoginRequest.email(),
                            emailLoginRequest.password()
                    )
            );

            // 인증 성공 시 SecurityContext 에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 생성
            String accessToken = jwtService.generateAccessToken(authentication);
            LoginResponse loginResponse = new LoginResponse(accessToken);

            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorCode.EMAIL_LOGIN_FAILED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ProfileResponse> basicSignup(@RequestBody @Valid EmailSignUpRequest emailSignUpRequest) {
        User user = userService.signUp(emailSignUpRequest);

        // TODO: 프로필 반환에서 변경 필요
        return ResponseEntity.ok(ProfileResponse.from(user));
    }

    @PostMapping("/validate")
    public boolean validateToken(@RequestParam("jwt") String jwt) {
        return jwtService.isValidAccessToken(jwt);
    }

}
