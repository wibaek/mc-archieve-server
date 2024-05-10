package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.application.name}")
    private String issuer;

    private final SecretKey secretKey;

    @Value("${service.jwt.expiration}")
    private Long expiration;

    public JwtService(@Value("${service.jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createJwt(String email) {
//        String email = user.getEmail();

        String jwt = Jwts.builder()
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 15분
                .subject(email)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

        return jwt;
    }

    public Jws<Claims> isValidToken(String jwt) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);
            return jws;
        } catch (JwtException e) {
            return null;
        }
    }

}
