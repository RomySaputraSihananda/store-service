package com.romys.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }
}