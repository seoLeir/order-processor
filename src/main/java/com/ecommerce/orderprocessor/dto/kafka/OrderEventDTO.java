package com.ecommerce.orderprocessor.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO implements Serializable {

    private UUID orderId;

    private UUID customerId;

    private Double totalAmount;

    private List<OrderItemEventDTO> itemList;

}
