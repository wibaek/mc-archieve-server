package com.wibaek.mcarchieve.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wibaek.mcarchieve.exception.CustomException;
import com.wibaek.mcarchieve.exception.ErrorCode;
import com.wibaek.mcarchieve.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorResponse errorResponse =
                new ErrorResponse(new CustomException(ErrorCode.AUTHENTICATION_FAILED));
        response.setStatus(ErrorCode.AUTHENTICATION_FAILED.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}

