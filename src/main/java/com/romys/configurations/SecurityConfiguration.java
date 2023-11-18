package com.romys.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return (http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/docs",
                                "/docs/swagger-ui/**",
                                "/swagger-resources/*",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/v1/**")
                        .permitAll())
                // .authorizeHttpRequests(auth -> auth.requestMatchers("//"))
                .build());
    }
}
