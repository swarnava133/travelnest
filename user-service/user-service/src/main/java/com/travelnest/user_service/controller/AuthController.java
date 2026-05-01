package com.travelnest.user_service.controller;
import com.travelnest.user_service.dto.AuthResponse;
import com.travelnest.user_service.dto.LoginRequest;
import com.travelnest.user_service.dto.RegisterRequest;
import com.travelnest.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")       // base URL for all endpoints in this controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //  REGISTER endpoint
    // POST http://localhost:8080/api/users/register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String message = userService.register(request);
        return ResponseEntity.ok(message);
    }

    //  LOGIN endpoint
    // POST http://localhost:8080/api/users/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    //  Health check — test if service is running
    // GET http://localhost:8080/api/users/health
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Service is running!");
    }
}
