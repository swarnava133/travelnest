package com.travelnest.api_gateway.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component

public class JwtUtil {
    // 🔑 Secret key — used to sign and verify JWT tokens
    // Must be same secret key used in user-service when creating tokens!
    private static final String SECRET =
            "TravelNestSecretKeyForJWTTokenGeneration2024";

    // Convert secret string into a cryptographic Key object
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ✅ Validate token — returns true if valid, false if expired/invalid
    public boolean isTokenValid(String token) {
        try {
            // Try to parse the token — if it fails, token is invalid
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 📦 Extract all data (claims) from token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())   // use same secret key
                .build()
                .parseClaimsJws(token)            // parse and verify
                .getBody();                        // get the data inside
    }

    // 👤 Extract username/email from token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
}
