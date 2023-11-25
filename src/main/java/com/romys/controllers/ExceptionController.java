package com.romys.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.romys.payloads.responses.BodyResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequestMapping("/api")
public class ExceptionController {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BodyResponse<?>> common(Throwable exception, HttpServletRequest request) {

        HttpStatus status = null;
        if (exception.getClass().getSimpleName() == "UserException") {
            status = HttpStatus.CONFLICT;
        }

        // return this.builder(exception, request, status);
        return new ResponseEntity<>(
                new BodyResponse<>(
                        exception.getMessage(), status.value(), "failed",
                        "error"),
                status);
    }

    private ResponseEntity<BodyResponse<?>> builder(Throwable exception, HttpServletRequest request,
            HttpStatus status) {
        return new ResponseEntity<>(
                new BodyResponse<>(
                        exception.getMessage(), status.value(), "failed",
                        "error"),
                status);
    }

}
