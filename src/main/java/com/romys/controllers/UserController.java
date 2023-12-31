package com.romys.controllers;

import java.io.IOException;
import java.util.List;

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

import com.romys.DTOs.PasswordDTO;
import com.romys.DTOs.UserDetailDTO;
import com.romys.models.LogModel;
import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.payloads.responses.BodyResponse;
import com.romys.services.JwtService;
import com.romys.services.LogService;
import com.romys.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
        @Autowired
        private UserService userService;

        @Autowired
        private LogService logService;

        @Autowired
        private JwtService jwtService;

        /*
         * get self info
         */
        @GetMapping
        @Operation(summary = "Get self info", description = "API for get self info")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> getInfo(HttpServletRequest request)
                        throws IOException {
                ElasticHit<UserModel> user = this.jwtService.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));

                return new ResponseEntity<>(
                                new BodyResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(),
                                                String.format("all data from %s", user.source().getUsername()),
                                                user),
                                HttpStatus.OK);
        }

        /*
         * Update self info
         */
        @PutMapping
        @Operation(summary = "Update self info", description = "API for update self")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> updateInfo(@RequestBody UserDetailDTO userDetail,
                        HttpServletRequest request) throws IOException {
                ElasticHit<UserModel> hit = this.jwtService.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));

                return new ResponseEntity<>(
                                new BodyResponse<>(
                                                HttpStatus.OK.getReasonPhrase(),
                                                HttpStatus.OK.value(),
                                                String.format("success update info %s", hit.source().getUsername()),
                                                userService.updateUser(userDetail, hit, request)),
                                HttpStatus.OK);
        }

        /*
         * Reset Password
         */
        @PostMapping
        @Operation(summary = "Reset Password", description = "API for Reset Password")
        public ResponseEntity<BodyResponse<ElasticHit<UserModel>>> resetPassword(@RequestBody PasswordDTO password,
                        HttpServletRequest request) throws IOException {

                ElasticHit<UserModel> hit = this.jwtService.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));

                return new ResponseEntity<>(
                                new BodyResponse<>(
                                                HttpStatus.OK.getReasonPhrase(),
                                                HttpStatus.OK.value(),
                                                String.format("success update info %s", hit.source().getUsername()),
                                                this.userService.resetPassword(hit, password)),
                                HttpStatus.OK);
        }

        /*
         * cek self logs
         */
        @GetMapping("/logs")
        @Operation(summary = "Get self Log", description = "API for get self Log")
        public ResponseEntity<BodyResponse<List<ElasticHit<LogModel>>>> getLogs(
                        HttpServletRequest request) throws IOException {

                String username = this.jwtService.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));

                return new ResponseEntity<>(
                                new BodyResponse<>(
                                                HttpStatus.OK.getReasonPhrase(),
                                                HttpStatus.OK.value(),
                                                String.format("log %s", username),
                                                this.logService.getByUsername(username)),
                                HttpStatus.OK);
        }
}