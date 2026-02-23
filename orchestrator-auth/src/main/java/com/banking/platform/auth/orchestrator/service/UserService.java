package com.banking.platform.auth.orchestrator.service;

import com.banking.platform.auth.orchestrator.domain.UserEntity;
import com.banking.platform.auth.orchestrator.domain.UserEntity.Role;
import com.banking.platform.auth.orchestrator.dto.*;
import com.banking.platform.auth.orchestrator.repository.UserRepository;
import com.banking.platform.common.exception.ResourceNotFoundException;
import com.banking.platform.common.exception.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ValidationException("Username already exists: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new ValidationException("Email already registered: " + request.email());
        }

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .passwordHash(passwordEncoder.encode(request.password()))
                .email(request.email())
                .fullName(request.fullName())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, jwtService.getExpirationSeconds());
    }

    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ValidationException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ValidationException("Invalid credentials");
        }
        if (!user.isEnabled()) {
            throw new ValidationException("Account is disabled");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, jwtService.getExpirationSeconds());
    }

    public UserResponse getUser(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", username));
        return toResponse(user);
    }

    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse changeRole(Long id, String newRole) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id.toString()));
        user.setRole(Role.valueOf(newRole.toUpperCase()));
        userRepository.save(user);
        return toResponse(user);
    }

    private UserResponse toResponse(UserEntity u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getFullName(), u.getRole().name(), u.isEnabled());
    }
}

