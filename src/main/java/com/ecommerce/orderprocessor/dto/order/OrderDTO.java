package com.ecommerce.orderprocessor.dto.order;

import com.ecommerce.orderprocessor.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
public class OrderDTO {

    private UUID id;

    private UUID orderId;

    private UUID customerId;

    private Double totalAmount;

    private OrderStatus orderStatus;

    private List<OrderItemDTO> itemList;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

}
