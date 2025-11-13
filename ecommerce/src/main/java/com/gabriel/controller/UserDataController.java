package com.gabriel.controller;

import com.gabriel.entity.UserData;
import com.gabriel.repository.UserDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/userdata", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserDataController {

    @Autowired
    private UserDataRepository userDataRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<UserData> users = userDataRepository.findAll();
            // Remove passwords from response for security
            users.forEach(user -> user.setPassword(null));
            log.info("Retrieved {} users", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            log.error("Failed to retrieve users: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Optional<UserData> user = userDataRepository.findById(id);
            if (user.isPresent()) {
                UserData userData = user.get();
                userData.setPassword(null); // Remove password for security
                log.info("Retrieved user with ID: {}", id);
                return ResponseEntity.ok(userData);
            } else {
                log.warn("User not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve user with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        try {
            Optional<UserData> user = userDataRepository.findByEmail(email);
            if (user.isPresent()) {
                UserData userData = user.get();
                userData.setPassword(null); // Remove password for security
                log.info("Retrieved user with email: {}", email);
                return ResponseEntity.ok(userData);
            } else {
                log.warn("User not found with email: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with email: " + email));
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve user with email {}: {}", email, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        try {
            Optional<UserData> user = userDataRepository.findByUsername(username);
            if (user.isPresent()) {
                UserData userData = user.get();
                userData.setPassword(null); // Remove password for security
                log.info("Retrieved user with username: {}", username);
                return ResponseEntity.ok(userData);
            } else {
                log.warn("User not found with username: {}", username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with username: " + username));
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve user with username {}: {}", username, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserData userData) {
        try {
            // Check if email already exists
            if (userDataRepository.existsByEmail(userData.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Email already exists: " + userData.getEmail()));
            }
            
            // Check if username already exists
            if (userDataRepository.existsByUsername(userData.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Username already exists: " + userData.getUsername()));
            }
            
            log.info("Creating user: {}", userData.getEmail());
            UserData savedUser = userDataRepository.save(userData);
            savedUser.setPassword(null); // Remove password from response
            log.info("User created successfully with ID: {}", savedUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception ex) {
            log.error("Failed to create user: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserData userData) {
        try {
            Optional<UserData> existingUser = userDataRepository.findById(id);
            if (existingUser.isPresent()) {
                UserData existing = existingUser.get();
                
                // Check if email is being changed and if it conflicts
                if (userData.getEmail() != null && !userData.getEmail().equals(existing.getEmail())) {
                    if (userDataRepository.existsByEmail(userData.getEmail())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("error", "Email already exists: " + userData.getEmail()));
                    }
                }
                
                // Check if username is being changed and if it conflicts
                if (userData.getUsername() != null && !userData.getUsername().equals(existing.getUsername())) {
                    if (userDataRepository.existsByUsername(userData.getUsername())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of("error", "Username already exists: " + userData.getUsername()));
                    }
                }
                
                userData.setId(id);
                // Preserve created date if not provided
                if (userData.getCreated() == null) {
                    userData.setCreated(existing.getCreated());
                }
                
                UserData updatedUser = userDataRepository.save(userData);
                updatedUser.setPassword(null); // Remove password from response
                log.info("User updated successfully with ID: {}", id);
                return ResponseEntity.ok(updatedUser);
            } else {
                log.warn("User not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to update user with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<UserData> user = userDataRepository.findById(id);
            if (user.isPresent()) {
                userDataRepository.deleteById(id);
                log.info("User deleted successfully with ID: {}", id);
                return ResponseEntity.ok().body(Map.of("message", "User deleted successfully"));
            } else {
                log.warn("User not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to delete user with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + ex.getMessage());
        }
    }
}

