package com.ecommerce.orderprocessor.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEventDTO implements Serializable {

    private UUID itemId;

    private Integer quantity;

    private Double price;

}
