package com.romys.components;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romys.payloads.responses.BodyResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointComponent implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                final ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(
                                response.getOutputStream(),
                                new BodyResponse<>(
                                                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                                                HttpStatus.UNAUTHORIZED.value(), authException.getMessage(),
                                                List.of(
                                                                request.getServletPath(),
                                                                authException.getClass().getName())));
        }
}