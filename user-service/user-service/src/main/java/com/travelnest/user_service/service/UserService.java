package com.travelnest.user_service.service;

import com.travelnest.user_service.dto.AuthResponse;
import com.travelnest.user_service.dto.LoginRequest;
import com.travelnest.user_service.dto.RegisterRequest;
import com.travelnest.user_service.model.User;
import com.travelnest.user_service.repository.UserRepository;
import com.travelnest.user_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor        // Lombok: auto generates constructor for final fields
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 📝 REGISTER
    public String register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Create new user object
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())  // encrypt password!
        );
        user.setRole("ROLE_USER");   // default role for new users

        // Save to database
        userRepository.save(user);

        return "User registered successfully!";
    }

    // 🔐 LOGIN
    public AuthResponse login(LoginRequest request) {

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        // Return token + user info
        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}