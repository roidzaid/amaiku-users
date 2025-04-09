package com.amaiku.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Permite todos los orígenes
                .allowedMethods("*") // Permite todos los métodos (GET, POST, PUT, DELETE, etc.)
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(false); // Permite credenciales (cookies, autorización)
    }
}
