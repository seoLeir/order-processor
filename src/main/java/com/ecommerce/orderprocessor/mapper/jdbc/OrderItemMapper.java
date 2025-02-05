package com.ecommerce.orderprocessor.mapper.jdbc;

import com.ecommerce.orderprocessor.dto.order.OrderItemDTO;
import com.ecommerce.orderprocessor.util.JdbcUtils;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.UUID;

public final class OrderItemMapper {

    @SneakyThrows
    public static OrderItemDTO orderItemMapper(ResultSet rs, Integer rowNum) {
        OrderItemDTO dto = new OrderItemDTO();

        dto.setId(JdbcUtils.getValue(UUID.class, "id", rs));
        dto.setOrderId(JdbcUtils.getValue(UUID.class, "order_id", rs));
        dto.setItemId(JdbcUtils.getValue(UUID.class, "order_item_id", rs));
        dto.setQuantity(JdbcUtils.getValue(Integer.class, "order_item_quantity", rs));
        dto.setPrice(JdbcUtils.getValue(Double.class, "order_item_price", rs));
        dto.setCreatedBy(JdbcUtils.getValue(String.class, "order_item_created_by", rs));
        dto.setCreatedAt(JdbcUtils.getValue(LocalDateTime.class, "order_item_created_at", rs));

        return dto;
    }
}
