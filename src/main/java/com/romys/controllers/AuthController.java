package com.romys.controllers;

import java.io.IOException;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.DTOs.UserDTO;
import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
import com.romys.payloads.responses.BodyResponses;
import com.romys.payloads.responses.TokenResponse;
import com.romys.services.JwtService;
import com.romys.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
        @Autowired
        private UserService service;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private UserDetailsService userDetailsService;

        private String builder(HttpServletResponse response, String username, String password)
                        throws AuthenticationException {
                Authentication authentication = this.authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password));
                if (authentication.isAuthenticated()) {
                        UserDetails user = this.userDetailsService.loadUserByUsername(username);
                        String token = this.jwtService.generateToken(username);
                        response.setHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",
                                        token));
                        return token;
                }
                throw new AuthenticationException("Failed to authenticate user");
        }

        /*
         * signin account
         */
        @PostMapping("/signin")
        @Operation(summary = "Signin user", description = "API for Signin user")
        public ResponseEntity<BodyResponse<TokenResponse>> signin(
                        @RequestBody(required = true) UserDTO user, HttpServletResponse response)
                        throws AuthenticationException {

                System.out.println(response);

                return new ResponseEntity<>(
                                new BodyResponse<>("ok", 200, "ok",
                                                new TokenResponse(this.builder(response, user.getUsername(),
                                                                user.getPassword()))),
                                HttpStatus.OK);
        }

        /*
         * signup account
         */
        @PostMapping("/signup")
        @Operation(summary = "Signup new user", description = "API for Signup new user")
        public ResponseEntity<BodyResponses<ElasticHit<UserModel>>> signup(
                        @RequestBody(required = true) UserDTO user) throws IOException {
                List<ElasticHit<UserModel>> response = this.service.createUser(user);

                return new ResponseEntity<>(
                                new BodyResponses<>("ok", HttpStatus.CREATED.value(),
                                                String.format("product with id success created"), response),
                                HttpStatus.CREATED);
        }
}
