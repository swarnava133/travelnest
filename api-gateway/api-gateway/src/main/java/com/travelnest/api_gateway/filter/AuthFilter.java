package com.travelnest.api_gateway.filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AuthFilter extends
        AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ These routes are PUBLIC — no JWT needed
    private static final List<String> OPEN_ROUTES = List.of(
            "/api/users/register",
            "/api/users/login",
            "/api/users/health",
            "/api/hotels/health"
    );

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();

            // 1️⃣ Check if this route is public — skip JWT check
            boolean isOpenRoute = OPEN_ROUTES.stream()
                    .anyMatch(path::startsWith);

            if (isOpenRoute) {
                // Public route → forward directly without checking token
                return chain.filter(exchange);
            }

            // 2️⃣ For protected routes — check Authorization header
            if (!exchange.getRequest().getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 3️⃣ Extract token from header
            String authHeader = exchange.getRequest().getHeaders()
                    .get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authHeader.substring(7); // remove "Bearer "

            // 4️⃣ Validate token
            if (!jwtUtil.isTokenValid(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 5️⃣ Valid token → forward request
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}