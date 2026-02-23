package com.banking.platform.auth.ccsl.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
public class TokenController {

    private final SecretKey key;
    private final long expirationMs;

    public TokenController(
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.expiration-ms:300000}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    @PostMapping("/auth/token")
    public TokenResponse issueToken(@RequestBody TokenRequest request) {
        Date now = new Date();
        String token = Jwts.builder()
                .subject(request.clientId())
                .claim("scope", "ccsl:read")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
        return new TokenResponse(token, "Bearer", expirationMs / 1000);
    }

    public record TokenRequest(String clientId, String clientSecret) {}
    public record TokenResponse(String accessToken, String tokenType, long expiresIn) {}
}

