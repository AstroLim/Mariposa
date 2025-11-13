package com.gabriel.controller;

import com.gabriel.model.User;
import com.gabriel.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("Register request for: " + user.getEmail());
        HttpStatus httpStatus;
        Object responseBody;
        
        try {
            // Validate input
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new RuntimeException("Username is required");
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new RuntimeException("Password is required");
            }
            
            User registeredUser = authService.register(user);
            // Don't send password back to client
            registeredUser.setPassword(null);
            httpStatus = HttpStatus.OK;
            responseBody = registeredUser;
            log.info("Registration successful for: " + user.getEmail());
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            responseBody = new ErrorResponse(e.getMessage());
            log.error("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody = new ErrorResponse("Registration failed. Please try again.");
            log.error("Registration error: " + e.getMessage(), e);
        }
        
        return ResponseEntity.status(httpStatus).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for email: " + loginRequest.getEmail());
        HttpStatus httpStatus;
        Object responseBody;
        
        try {
            // Validate input
            if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                throw new RuntimeException("Password is required");
            }
            
            User user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
            // Don't send password back to client
            user.setPassword(null);
            httpStatus = HttpStatus.OK;
            responseBody = user;
            log.info("Login successful for: " + loginRequest.getEmail());
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            responseBody = new ErrorResponse(e.getMessage());
            log.warn("Login failed: " + e.getMessage());
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody = new ErrorResponse("Login failed. Please try again.");
            log.error("Login error: " + e.getMessage(), e);
        }
        
        return ResponseEntity.status(httpStatus).body(responseBody);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        boolean exists = authService.emailExists(email);
        return ResponseEntity.ok(new EmailCheckResponse(exists));
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username) {
        boolean exists = authService.usernameExists(username);
        return ResponseEntity.ok(new UsernameCheckResponse(exists));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User user) {
        log.info("Update user request for user ID: " + userId);
        HttpStatus httpStatus;
        Object responseBody;
        
        try {
            // Validate input
            if (user.getUsername() != null && user.getUsername().trim().isEmpty()) {
                throw new RuntimeException("Username cannot be empty");
            }
            if (user.getEmail() != null && user.getEmail().trim().isEmpty()) {
                throw new RuntimeException("Email cannot be empty");
            }
            
            User updatedUser = authService.updateUser(userId, user);
            // Don't send password back to client
            updatedUser.setPassword(null);
            httpStatus = HttpStatus.OK;
            responseBody = updatedUser;
            log.info("User updated successfully: " + userId);
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            responseBody = new ErrorResponse(e.getMessage());
            log.error("Update failed: " + e.getMessage());
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody = new ErrorResponse("Update failed. Please try again.");
            log.error("Update error: " + e.getMessage(), e);
        }
        
        return ResponseEntity.status(httpStatus).body(responseBody);
    }

    // Inner classes for request/response
    private static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private static class EmailCheckResponse {
        private boolean exists;

        public EmailCheckResponse(boolean exists) {
            this.exists = exists;
        }

        public boolean isExists() {
            return exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
    }

    private static class UsernameCheckResponse {
        private boolean exists;

        public UsernameCheckResponse(boolean exists) {
            this.exists = exists;
        }

        public boolean isExists() {
            return exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
    }
}

