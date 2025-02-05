package com.ecommerce.orderprocessor.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Order {

    private UUID orderId;

    private UUID consumerId;

    private BigDecimal amount;

    private OrderStatus status;

    private LocalDateTime createdAt;

}
