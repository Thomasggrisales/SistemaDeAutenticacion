package org.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todas las rutas (/auth/login, /auth/register, etc.)
                        .allowedOrigins("http://localhost:3000") // Permite explicitamente a tu Frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite todos los verbos HTTP
                        .allowedHeaders("*") // Permite cualquier cabecera
                        .allowCredentials(true); // Permite el manejo de cookies/sesiones si es necesario
            }
        };
    }
}