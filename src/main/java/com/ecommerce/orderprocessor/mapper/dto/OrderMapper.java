package com.ecommerce.orderprocessor.mapper.dto;

import com.ecommerce.orderprocessor.dto.kafka.OrderEventDTO;
import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.dto.order.OrderItemDTO;

import java.util.List;

public class OrderMapper {

    public static OrderDTO orderMapperFromEvent(OrderEventDTO orderEventDTO) {
        OrderDTO dto = new OrderDTO();

        dto.setOrderId(orderEventDTO.getOrderId());
        dto.setCustomerId(orderEventDTO.getCustomerId());
        dto.setTotalAmount(orderEventDTO.getTotalAmount());

        List<OrderItemDTO> orderItems = orderEventDTO.getItemList().stream()
                .map(orderItemEventDTO -> {
                    OrderItemDTO itemDTO = new OrderItemDTO();

                    itemDTO.setItemId(orderItemEventDTO.getItemId());
                    itemDTO.setQuantity(orderItemEventDTO.getQuantity());
                    itemDTO.setPrice(orderItemEventDTO.getPrice());

                    return itemDTO;
                }).toList();
        dto.setItemList(orderItems);

        return dto;
    }

}
