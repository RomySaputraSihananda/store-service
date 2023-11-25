package com.romys.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.romys.payloads.responses.BodyResponse;
import com.romys.payloads.responses.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequestMapping("/api")
public class ExceptionController {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BodyResponse<ExceptionResponse>> common(Throwable exception, HttpServletRequest request) {
        HttpStatus status = exception.getClass().getAnnotation(ResponseStatus.class).value();

        return new ResponseEntity<>(
                new BodyResponse<>(
                        status.getReasonPhrase(), status.value(), exception.getMessage(),
                        new ExceptionResponse(
                                request.getServletPath(),
                                exception.getClass().getName())),
                status);
    }
}
