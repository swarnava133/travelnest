package com.travelnest.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;    // JWT token sent back to frontend after login
    private String email;
    private String role;
}
