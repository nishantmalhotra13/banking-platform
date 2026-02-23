package com.banking.platform.auth.orchestrator.controller;

import com.banking.platform.auth.orchestrator.dto.*;
import com.banking.platform.auth.orchestrator.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User registration, login, management")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user info from JWT")
    public UserResponse me(Principal principal) {
        return userService.getUser(principal.getName());
    }

    @GetMapping("/users")
    @Operation(summary = "List all users (admin only)")
    public List<UserResponse> listUsers() {
        return userService.listUsers();
    }

    @PutMapping("/users/{id}/role")
    @Operation(summary = "Change user role (admin only)")
    public UserResponse changeRole(@PathVariable Long id, @RequestBody RoleChangeRequest request) {
        return userService.changeRole(id, request.role());
    }

    public record RoleChangeRequest(String role) {}
}

