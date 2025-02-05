package com.ecommerce.orderprocessor.util;

import com.ecommerce.orderprocessor.dto.AbstractResponse;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T extends AbstractResponse> ResponseEntity<T> wrapResponse(T t) {
        return new ResponseEntity<>(t, t.getHttpStatus());
    }

}
