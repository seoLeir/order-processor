package com.ecommerce.orderprocessor.model;

import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum RoleEnum {

    USER(1L),

    ADMIN(2L);


    @JsonValue
    private final Long value;

    RoleEnum(Long value) {
        this.value = value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RoleEnum valueDeserializer(Long value) {
        return Arrays.stream(RoleEnum.values())
                .filter(role -> role.getValue().equals(value))
                .findAny()
                .orElseThrow(() -> new BaseApplicationException("Invalid user role", HttpStatus.BAD_REQUEST));
    }


}
