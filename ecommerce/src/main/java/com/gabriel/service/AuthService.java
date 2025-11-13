package com.gabriel.service;

import com.gabriel.model.User;

public interface AuthService {
    User register(User user);
    User login(String email, String password);
    User updateUser(int userId, User user);
    boolean emailExists(String email);
    boolean emailExistsForOtherUser(String email, int userId);
    boolean usernameExists(String username);
    boolean usernameExistsForOtherUser(String username, int userId);
}

