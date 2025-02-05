package com.ecommerce.orderprocessor.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderItemDTO {

    private UUID id;

    private UUID orderId;

    private UUID itemId;

    private Integer quantity;

    private Double price;

    private String createdBy;

    private LocalDateTime createdAt;

}
