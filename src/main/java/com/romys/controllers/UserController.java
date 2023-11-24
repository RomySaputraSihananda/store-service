package com.romys.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.romys.DTOs.UserDetailDTO;
import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
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
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> getInfo(HttpServletRequest request)
                        throws IOException {
                ElasticHit<UserModel> user = this.jwtService.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));

                return new ResponseEntity<>(
                                new BodyResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(),
                                                String.format("all data from %s", user.source().getUsername()),
                                                user),
                                HttpStatus.OK);
        }

        @PutMapping
        @Operation(summary = "Update info self", description = "API for update info self")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> updateInfo(@RequestBody UserDetailDTO userDetail,
                        HttpServletRequest request) throws IOException {
                ElasticHit<UserModel> hit = this.jwtService.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));

                return new ResponseEntity<>(
                                new BodyResponse<>(
                                                HttpStatus.OK.getReasonPhrase(),
                                                HttpStatus.OK.value(),
                                                String.format("success update info %s", hit.source().getUsername()),
                                                service.updateUser(userDetail, hit, request)),
                                HttpStatus.OK);
        }

        @PostMapping
        @Operation(summary = "Reset Password", description = "API for Reset Password")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> resetPassword(@RequestBody UserDetailDTO userDetail,
                        HttpServletRequest request) throws IOException {
                return null;
        }

        @GetMapping("/logs")
        @Operation(summary = "Get Log", description = "API for get Log")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> getLogs(@RequestBody UserDetailDTO userDetail,
                        HttpServletRequest request) throws IOException {
                return null;
        }
}