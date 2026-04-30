package com.travelnest.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration corsConfig = new CorsConfiguration();

        // Allow ALL origins
        corsConfig.setAllowedOriginPatterns(List.of("*"));

        // Allow ALL methods
        corsConfig.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        // Allow ALL headers
        corsConfig.setAllowedHeaders(List.of("*"));

        corsConfig.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Apply to ALL routes
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}