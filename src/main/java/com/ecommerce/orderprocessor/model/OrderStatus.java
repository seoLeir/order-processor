package com.ecommerce.orderprocessor.model;


import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum OrderStatus {

    NEW(1L),

    COMPLETED(2L),

    FAILED(3L),

    DELETED(4L);

    @Getter
    @JsonValue
    private final Long value;

    OrderStatus(Long value) {
        this.value = value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderStatus valueDeserializer(Long value) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.getValue().equals(value))
                .findAny()
                .orElseThrow(() -> new BaseApplicationException("Invalid order status", HttpStatus.BAD_REQUEST));
    }
}
