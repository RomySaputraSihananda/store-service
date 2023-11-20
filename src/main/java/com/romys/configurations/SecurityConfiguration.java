package com.romys.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.romys.components.AccessDeniedHandlerComponent;
import com.romys.components.AuthEntryPointComponent;
import com.romys.filters.JwtAuthenticationFilter;
import com.romys.services.implement.UserServiceImplement;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
        @Autowired
        @Lazy
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private AccessDeniedHandlerComponent accessDeniedHandler;

        @Autowired
        private AuthEntryPointComponent authenticationEntryPoint;

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                return (new UserServiceImplement());
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(this.userDetailsService());
                authenticationProvider.setPasswordEncoder(this.passwordEncoder());
                return authenticationProvider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return (new BCryptPasswordEncoder());
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/docs",
                                                                "/docs/swagger-ui/**",
                                                                "/swagger-resources/*",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/api/v1/auth/**")
                                                .permitAll())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/v1/product/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .authenticationProvider(this.authenticationProvider())
                                .addFilterBefore(jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(exception -> exception
                                                .accessDeniedHandler(this.accessDeniedHandler)
                                                .authenticationEntryPoint(this.authenticationEntryPoint))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(
                                                                SessionCreationPolicy.STATELESS))
                                .build();
        }
}
