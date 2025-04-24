package com.mcarchieve.mcarchieve.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.secret}")
    private SecretKey secretKey;

    @Value("${service.jwt.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${service.jwt.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    public boolean isValidAccessToken(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return false;
        }

        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt);
            return jws.getPayload().getExpiration().before(new Date()) ? false : true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String generateAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwt = Jwts.builder()
                // Payload
                .issuer(issuer) // iss: 토큰 발급자
                .subject(authentication.getName()) // sub: 토큰 제목
                .issuedAt(new Date()) // iat: 토큰 발급 시간
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)) // exp: 토큰 만료 시간
                .claim("authorities", authorities) // 추가 데이터
                // Signature
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

        return jwt;
    }

    public String generateRefreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwt = Jwts.builder()
                // Payload
                .issuer(issuer) // iss: 토큰 발급자
                .subject(authentication.getName()) // sub: 토큰 제목
                .issuedAt(new Date()) // iat: 토큰 발급 시간
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION                                  )) // exp: 토큰 만료 시간
                .claim("authorities", authorities) // 추가 데이터
                // Signature
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

        return jwt;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("authorities").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());

//        User principal = new User(claims.getSubject(), "", authorities);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public String createJwtByEmail(String email) {
        String jwt = Jwts.builder()
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 15분
                .subject(email)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

        return jwt;
    }



}
