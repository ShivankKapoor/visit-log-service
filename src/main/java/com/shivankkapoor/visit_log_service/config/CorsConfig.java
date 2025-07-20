package com.shivankkapoor.visit_log_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${ALLOWED_HOSTS:}")
    private String allowedHosts;

    @Value("${DEV_MODE:false}")
    private boolean devMode;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (devMode) {
                    registry.addMapping("/**")
                            .allowedOrigins("*")
                            .allowedMethods("GET", "POST");
                } else {
                    String[] hosts = allowedHosts.split(",");
                    registry.addMapping("/**")
                            .allowedOrigins(hosts)
                            .allowedMethods("GET", "POST");
                }
            }
        };
    }
}
