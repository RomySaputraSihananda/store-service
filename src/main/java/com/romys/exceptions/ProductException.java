package com.romys.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }
}