package com.gabriel.serviceimpl;

import com.gabriel.entity.UserData;
import com.gabriel.model.User;
import com.gabriel.repository.UserDataRepository;
import com.gabriel.service.AuthService;
import com.gabriel.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDataRepository userDataRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    Transform<UserData, User> transformUserData = new Transform<>(User.class);
    Transform<User, UserData> transformUser = new Transform<>(UserData.class);

    @Override
    public User register(User user) {
        log.info("Register user: " + user.getEmail());
        
        // Check if email already exists
        if (userDataRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        // Check if username already exists
        if (userDataRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        
        UserData userData = transformUser.transform(user);
        UserData savedUserData = userDataRepository.save(userData);
        
        log.info("User registered successfully: " + savedUserData.getId());
        return transformUserData.transform(savedUserData);
    }

    @Override
    public User login(String email, String password) {
        log.info("Login attempt for email: " + email);
        
        Optional<UserData> userDataOptional = userDataRepository.findByEmailAndPassword(email, password);
        
        if (userDataOptional.isPresent()) {
            UserData userData = userDataOptional.get();
            log.info("Login successful for user: " + userData.getUsername());
            return transformUserData.transform(userData);
        } else {
            log.warn("Login failed for email: " + email);
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public boolean emailExists(String email) {
        return userDataRepository.existsByEmail(email);
    }

    @Override
    public boolean usernameExists(String username) {
        return userDataRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public User updateUser(int userId, User user) {
        log.info("Update user request for user ID: " + userId);
        log.info("Update data - Username: {}, Email: {}, Password: {}", 
            user.getUsername(), user.getEmail(), user.getPassword() != null ? "***" : "null");
        
        Optional<UserData> userDataOptional = userDataRepository.findById(userId);
        if (!userDataOptional.isPresent()) {
            log.error("User not found with ID: " + userId);
            throw new RuntimeException("User not found");
        }
        
        UserData existingUserData = userDataOptional.get();
        log.info("Existing user data - Username: {}, Email: {}", 
            existingUserData.getUsername(), existingUserData.getEmail());
        
        boolean hasChanges = false;
        
        // Update email if provided
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            String newEmail = user.getEmail().trim();
            String currentEmail = existingUserData.getEmail() != null ? existingUserData.getEmail() : "";
            
            // Check if email is being changed
            if (!newEmail.equals(currentEmail)) {
                // Check if new email already exists for another user
                if (emailExistsForOtherUser(newEmail, userId)) {
                    throw new RuntimeException("Email already registered to another user");
                }
                log.info("Changing email from '{}' to '{}'", currentEmail, newEmail);
                hasChanges = true;
            }
            // Always set the email (even if same, to ensure persistence)
            existingUserData.setEmail(newEmail);
            log.info("Email set to '{}' for user {}", newEmail, userId);
        }
        
        // Update username if provided
        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            String newUsername = user.getUsername().trim();
            String currentUsername = existingUserData.getUsername() != null ? existingUserData.getUsername() : "";
            
            // Check if username is being changed
            if (!newUsername.equals(currentUsername)) {
                // Check if new username already exists for another user
                if (usernameExistsForOtherUser(newUsername, userId)) {
                    throw new RuntimeException("Username already taken by another user");
                }
                log.info("Changing username from '{}' to '{}'", currentUsername, newUsername);
                hasChanges = true;
            }
            // Always set the username (even if same, to ensure persistence)
            existingUserData.setUsername(newUsername);
            log.info("Username set to '{}' for user {}", newUsername, userId);
        }
        
        // Update password only if provided
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            existingUserData.setPassword(user.getPassword().trim());
            hasChanges = true;
            log.info("Password set for user " + userId);
        }
        
        if (!hasChanges) {
            log.warn("No changes detected for user " + userId + " - but will still save");
        }
        
        // Force save and flush the entity to ensure immediate database write
        UserData updatedUserData = userDataRepository.saveAndFlush(existingUserData);
        
        // Refresh entity to ensure we have the latest data from database
        entityManager.refresh(updatedUserData);
        
        log.info("User saved and flushed - ID: {}, Username: {}, Email: {}", 
            updatedUserData.getId(), updatedUserData.getUsername(), updatedUserData.getEmail());
        
        // Verify the save by reading back from database (fresh read)
        entityManager.clear(); // Clear persistence context to force fresh read
        Optional<UserData> verifyUser = userDataRepository.findById(userId);
        if (verifyUser.isPresent()) {
            UserData verified = verifyUser.get();
            log.info("Verified user data from DB - ID: {}, Username: {}, Email: {}", 
                verified.getId(), verified.getUsername(), verified.getEmail());
        } else {
            log.error("Failed to verify user after save - user not found in database!");
        }
        
        return transformUserData.transform(updatedUserData);
    }

    @Override
    public boolean emailExistsForOtherUser(String email, int userId) {
        Optional<UserData> userDataOptional = userDataRepository.findByEmail(email);
        return userDataOptional.isPresent() && userDataOptional.get().getId() != userId;
    }

    @Override
    public boolean usernameExistsForOtherUser(String username, int userId) {
        Optional<UserData> userDataOptional = userDataRepository.findByUsername(username);
        return userDataOptional.isPresent() && userDataOptional.get().getId() != userId;
    }
}

