package com.travelnest.api_gateway.filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter  extends
        AbstractGatewayFilterFactory<AuthFilter.Config>  {

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            // 1️⃣ Get the request
            var request = exchange.getRequest();

            // 2️⃣ Check if Authorization header exists
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                // No token → block request → return 401 Unauthorized
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 3️⃣ Extract the token from header
            // Header looks like: "Bearer eyJhbGci..."
            // We remove "Bearer " to get just the token
            String authHeader = request.getHeaders()
                    .get(HttpHeaders.AUTHORIZATION)
                    .get(0);
            String token = authHeader.substring(7); // remove "Bearer "

            // 4️⃣ Validate the token
            if (!jwtUtil.isTokenValid(token)) {
                // Invalid/expired token → block → return 401
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 5️⃣ Token is valid → forward request to correct service
            return chain.filter(exchange);
        };
    }

    // Required config class — can be empty for now
    public static class Config {
    }

}
