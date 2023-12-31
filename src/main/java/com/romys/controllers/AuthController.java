package com.romys.controllers;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.DTOs.UserDTO;
import com.romys.models.LogModel;
import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
import com.romys.payloads.responses.TokenResponse;
import com.romys.services.JwtService;
import com.romys.services.LogService;
import com.romys.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
        @Autowired
        private UserService userService;

        @Autowired
        private LogService logService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtService jwtService;

        private String builder(HttpServletResponse response, HttpServletRequest request, String username,
                        String password)
                        throws AuthenticationException, IOException {
                Authentication authentication = this.authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password));
                if (authentication.isAuthenticated()) {
                        String token = this.jwtService.generateToken(username);
                        this.logService.create(new LogModel(username, request));
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
                        @RequestBody(required = true) UserDTO user, HttpServletResponse response,
                        HttpServletRequest request)
                        throws AuthenticationException, IOException {

                return new ResponseEntity<>(
                                new BodyResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(),
                                                "success login",
                                                new TokenResponse(this.builder(response, request, user.getUsername(),
                                                                user.getPassword()))),
                                HttpStatus.OK);
        }

        /*
         * signup account
         */
        @PostMapping("/signup")
        @Operation(summary = "Signup new user", description = "API for Signup new user")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> signup(
                        @RequestBody(required = true) UserDTO user) throws IOException {
                ElasticHit<UserModel> response = this.userService.createUser(user);

                return new ResponseEntity<>(
                                new BodyResponse<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED.value(),
                                                String.format("new user success created"), response),
                                HttpStatus.CREATED);
        }
}
