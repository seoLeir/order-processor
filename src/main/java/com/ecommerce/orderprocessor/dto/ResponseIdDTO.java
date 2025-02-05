package com.ecommerce.orderprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseIdDTO<T> implements AbstractResponse {
    private T id;

    private Boolean success;

    private String message;

    private HttpStatus httpStatus;

}
