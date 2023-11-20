package com.romys.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
import com.romys.payloads.responses.BodyResponses;
import com.romys.services.JwtService;
import com.romys.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    @Operation(summary = "Get info self", description = "API for get info self")
    public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> getSelfInfo(HttpServletRequest request)
            throws IOException {
        return new ResponseEntity<>(
                new BodyResponse<>("ok", HttpStatus.OK.value(), "all data of products",
                        this.service.getUserByName(
                                this.jwtService.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION)))),
                HttpStatus.OK);
    }

    // @GetMapping("/{id}")
    // @Operation(summary = "Get user by id", description = "API for get user by
    // id")
    // public ResponseEntity<BodyResponse<ElasticHit<UserModel>>>
    // getById(@PathVariable String id) throws IOException {
    // return new ResponseEntity<>(
    // new BodyResponse<>("ok", HttpStatus.OK.value(), String.format("data of users
    // by id %s", id),
    // this.service.getUserByid(id)),
    // HttpStatus.OK);
    // }
}
