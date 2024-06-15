package com.demo.createUser.service.impl;

import com.demo.createUser.model.entity.User;
import com.demo.createUser.repository.UserRepository;
import com.demo.createUser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public User createUser(User user) {

        if (!Pattern.matches(EMAIL_REGEX, user.getEmail())) {
            throw new RuntimeException("{\"mensaje\": \"Correo no válido\"}");
        }
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("{\"mensaje\": \"El correo ya registrado\"}");
        }
        if (!Pattern.matches(PASSWORD_REGEX, user.getPassword())) {
            throw new RuntimeException("{\"mensaje\": \"Contraseña no válida\"}");
        }

        user.setId(UUID.randomUUID());
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        user.setActive(true);
        user.setPhones(user.getPhones());

        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
