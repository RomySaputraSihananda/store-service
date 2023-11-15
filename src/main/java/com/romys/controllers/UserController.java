package com.romys.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.models.UserModel;
import com.romys.payloads.responses.BodyResponse;
import com.romys.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<BodyResponse<UserModel>> getAll() throws IOException {
        return new ResponseEntity<>(
                new BodyResponse<>("ok", HttpStatus.OK.value(), "all data of products", this.service.getUsers()),
                HttpStatus.OK);
    }
}
