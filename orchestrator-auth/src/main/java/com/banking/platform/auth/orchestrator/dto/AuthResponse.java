package com.banking.platform.auth.orchestrator.dto;

public record AuthResponse(String accessToken, String tokenType, long expiresIn) {
    public AuthResponse(String accessToken, long expiresIn) {
        this(accessToken, "Bearer", expiresIn);
    }
}

