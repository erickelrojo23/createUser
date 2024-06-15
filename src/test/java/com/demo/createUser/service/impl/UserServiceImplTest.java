package com.demo.createUser.service.impl;

import com.demo.createUser.model.entity.User;
import com.demo.createUser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User user;

    private static final String VALID_EMAIL = "test@example.com";
    private static final String INVALID_EMAIL = "invalid-email";
    private static final String VALID_PASSWORD = "Abc123!@#";
    private static final String INVALID_PASSWORD = "123";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("hi");
        user.setId(UUID.randomUUID());
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setActive(true);
        user.setPhones(new ArrayList<>());
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void createUser_shouldThrowExceptionForInvalidEmail() {
        user.setEmail(INVALID_EMAIL);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("{\"mensaje\": \"Correo no válido\"}", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowExceptionForInvalidPassword() {
        user.setPassword(INVALID_PASSWORD);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("{\"mensaje\": \"Contraseña no válida\"}", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowExceptionForExistingEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("{\"mensaje\": \"El correo ya registrado\"}", exception.getMessage());
    }

    @Test
    void createUser_shouldCreateUserSuccessfully() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashed_password");
        User createdUser = userService.createUser(user);

        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getCreated());
        assertNotNull(createdUser.getModified());
        assertNotNull(createdUser.getLastLogin());
        assertNotNull(createdUser.getToken());
        assertTrue(createdUser.isActive());
        assertEquals("hashed_password", createdUser.getPassword());
        //assertTrue(new BCryptPasswordEncoder().matches(VALID_PASSWORD, createdUser.getPassword()));
    }
}