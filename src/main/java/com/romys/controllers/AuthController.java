package com.romys.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.DTOs.UserDTO;
import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
import com.romys.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
        @Autowired
        private UserService service;

        /*
         * signin account
         */
        @PostMapping("/signin")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> signin(
                        @RequestBody(required = true) UserDTO user) throws IOException {
                List<ElasticHit<UserModel>> response = this.service.createUser(user);

                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.CREATED.value(),
                                                String.format("product with id success created"),
                                                response),
                                HttpStatus.CREATED);
        }

        /*
         * signup account
         */
        @PostMapping("/signup")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> signup(
                        @RequestBody(required = true) UserDTO user) throws IOException {
                List<ElasticHit<UserModel>> response = this.service.createUser(user);

                return new ResponseEntity<>(
                                new BodyResponse<>("ok", HttpStatus.CREATED.value(),
                                                String.format("product with id success created"),
                                                response),
                                HttpStatus.CREATED);
        }
}
