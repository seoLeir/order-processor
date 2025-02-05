package com.ecommerce.orderprocessor.dto.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class BaseApplicationErrorDTO {

    private String message;

    private Integer statusCode;

    private Instant timestamp;

    private String path;

}
