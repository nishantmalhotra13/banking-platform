package com.banking.platform.auth.orchestrator.service;

import com.banking.platform.auth.orchestrator.domain.UserEntity;
import com.banking.platform.auth.orchestrator.domain.UserEntity.Role;
import com.banking.platform.auth.orchestrator.dto.*;
import com.banking.platform.auth.orchestrator.repository.UserRepository;
import com.banking.platform.common.exception.ResourceNotFoundException;
import com.banking.platform.common.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService(userRepository, passwordEncoder, jwtService);
    }

    // --- register ---

    @Test
    void register_createsUserAndReturnsToken() {
        RegisterRequest request = new RegisterRequest("newuser", "pass123", "new@test.com", "New User");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("$encoded$");
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn("jwt-token");
        when(jwtService.getExpirationSeconds()).thenReturn(3600L);

        AuthResponse response = service.register(request);

        assertThat(response.accessToken()).isEqualTo("jwt-token");
        assertThat(response.expiresIn()).isEqualTo(3600L);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getUsername()).isEqualTo("newuser");
        assertThat(captor.getValue().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void register_throwsWhenUsernameExists() {
        RegisterRequest request = new RegisterRequest("taken", "pass", "e@e.com", "User");
        when(userRepository.existsByUsername("taken")).thenReturn(true);

        assertThatThrownBy(() -> service.register(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Username already exists");
    }

    @Test
    void register_throwsWhenEmailExists() {
        RegisterRequest request = new RegisterRequest("newuser", "pass", "taken@e.com", "User");
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("taken@e.com")).thenReturn(true);

        assertThatThrownBy(() -> service.register(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email already registered");
    }

    // --- login ---

    @Test
    void login_returnsTokenForValidCredentials() {
        LoginRequest request = new LoginRequest("admin", "admin123");
        UserEntity user = buildUser("admin", Role.ADMIN, true);

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("admin123", user.getPasswordHash())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt");
        when(jwtService.getExpirationSeconds()).thenReturn(3600L);

        AuthResponse response = service.login(request);

        assertThat(response.accessToken()).isEqualTo("jwt");
    }

    @Test
    void login_throwsWhenUserNotFound() {
        LoginRequest request = new LoginRequest("noone", "pass");
        when(userRepository.findByUsername("noone")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Invalid credentials");
    }

    @Test
    void login_throwsWhenPasswordWrong() {
        LoginRequest request = new LoginRequest("admin", "wrong");
        UserEntity user = buildUser("admin", Role.ADMIN, true);

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", user.getPasswordHash())).thenReturn(false);

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Invalid credentials");
    }

    @Test
    void login_throwsWhenAccountDisabled() {
        LoginRequest request = new LoginRequest("disabled", "pass");
        UserEntity user = buildUser("disabled", Role.USER, false);

        when(userRepository.findByUsername("disabled")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", user.getPasswordHash())).thenReturn(true);

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("disabled");
    }

    // --- getUser ---

    @Test
    void getUser_returnsUserResponse() {
        UserEntity user = buildUser("admin", Role.ADMIN, true);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        UserResponse response = service.getUser("admin");

        assertThat(response.username()).isEqualTo("admin");
        assertThat(response.role()).isEqualTo("ADMIN");
    }

    @Test
    void getUser_throwsWhenNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUser("ghost"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // --- listUsers ---

    @Test
    void listUsers_returnsAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(
                buildUser("admin", Role.ADMIN, true),
                buildUser("user1", Role.USER, true)
        ));

        List<UserResponse> result = service.listUsers();

        assertThat(result).hasSize(2);
    }

    // --- changeRole ---

    @Test
    void changeRole_updatesAndReturnsUser() {
        UserEntity user = buildUser("user1", Role.USER, true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = service.changeRole(1L, "ADMIN");

        assertThat(response.role()).isEqualTo("ADMIN");
        verify(userRepository).save(user);
    }

    @Test
    void changeRole_throwsWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.changeRole(99L, "ADMIN"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // --- helper ---

    private UserEntity buildUser(String username, Role role, boolean enabled) {
        return UserEntity.builder()
                .id(1L)
                .username(username)
                .passwordHash("$2a$10$encoded")
                .email(username + "@test.com")
                .fullName(username.toUpperCase())
                .role(role)
                .enabled(enabled)
                .build();
    }
}

