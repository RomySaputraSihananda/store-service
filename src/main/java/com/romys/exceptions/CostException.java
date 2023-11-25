package com.romys.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CostException extends RuntimeException {

    public CostException(String message) {
        super(message);
    }
}