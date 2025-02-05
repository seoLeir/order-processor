package com.ecommerce.orderprocessor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO implements AbstractResponse {

    private Boolean success;

    private String message;

    @JsonIgnore
    private HttpStatus httpStatus;

}
