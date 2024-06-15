package com.demo.createUser.service;

import com.demo.createUser.model.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser(User user);
    Optional<User> findByEmail(String email);
}
