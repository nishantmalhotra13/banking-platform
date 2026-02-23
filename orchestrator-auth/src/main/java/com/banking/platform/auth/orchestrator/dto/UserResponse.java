package com.banking.platform.auth.orchestrator.dto;

public record UserResponse(Long id, String username, String email, String fullName, String role, boolean enabled) {}

