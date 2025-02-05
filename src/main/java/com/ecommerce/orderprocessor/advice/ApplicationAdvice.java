package com.ecommerce.orderprocessor.advice;

import com.ecommerce.orderprocessor.dto.exception.BaseApplicationErrorDTO;
import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class ApplicationAdvice {

    @ExceptionHandler(BaseApplicationException.class)
    @ResponseStatus
    public ResponseEntity<BaseApplicationErrorDTO> handleBaseApplicationException(BaseApplicationException ex, WebRequest webRequest) {
        log.error("Core application was thrown: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseApplicationErrorDTO.builder()
                        .timestamp(ex.getTimestamp())
                        .message(ex.getMessage())
                        .statusCode(ex.getHttpStatus().value())
                        .path(((ServletWebRequest) webRequest).getRequest().getRequestURI())
                        .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus
    public ResponseEntity<BaseApplicationErrorDTO> handleRuntimeException(RuntimeException ex, WebRequest webRequest) {
        log.error("RuntimeException was thrown: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseApplicationErrorDTO.builder()
                        .timestamp(Instant.now())
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .path(((ServletWebRequest) webRequest).getRequest().getRequestURI())
                        .build()
                );
    }

}
