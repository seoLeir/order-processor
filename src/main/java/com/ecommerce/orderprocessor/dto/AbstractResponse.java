package com.ecommerce.orderprocessor.dto;

import org.springframework.http.HttpStatus;

public interface AbstractResponse {

    Boolean getSuccess();

    String getMessage();

    HttpStatus getHttpStatus();

}
