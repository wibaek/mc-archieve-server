package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AuthController {

    JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam("email") String email) {

        return jwtService.createJwt(email);
    }

    @PostMapping("/validate")
    public Jws<Claims> validateToken(@RequestParam("jwt") String jwt){
        return jwtService.isValidToken(jwt);
    }

}
