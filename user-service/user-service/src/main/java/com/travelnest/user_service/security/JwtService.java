package com.travelnest.user_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    //  Must be SAME secret as in api-gateway JwtUtil!
    private static final String SECRET =
            "TravelNestSecretKeyForJWTTokenGeneration2024";

    // Token valid for 24 hours (in milliseconds)
    private static final long EXPIRATION = 86400000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //  Create a JWT token for the logged in user
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)                    // store email inside token
                .claim("role", role)                  // store role inside token
                .setIssuedAt(new Date())              // when token was created
                .setExpiration(new Date(
                        System.currentTimeMillis() + EXPIRATION  // expires in 24hrs
                ))
                .signWith(getSigningKey(),            // sign with secret key
                        SignatureAlgorithm.HS256)   // using HS256 algorithm
                .compact();                           // build and return token string
    }
}
