package com.ecommerce.orderprocessor.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
public class BaseApplicationException extends RuntimeException {

    private final String message;

    private final HttpStatus httpStatus;

    private final Instant timestamp;

    public BaseApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = Instant.now();
    }
}
