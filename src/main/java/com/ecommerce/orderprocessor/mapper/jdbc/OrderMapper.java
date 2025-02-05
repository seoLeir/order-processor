package com.ecommerce.orderprocessor.mapper.jdbc;

import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.model.OrderStatus;
import com.ecommerce.orderprocessor.util.JdbcUtils;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.UUID;

public final class OrderMapper {

    @SneakyThrows
    public static OrderDTO orderMapper(ResultSet rs, Integer rowNum) {
        OrderDTO dto = new OrderDTO();

        dto.setId(JdbcUtils.getValue(UUID.class, "id", rs));
        dto.setOrderId(JdbcUtils.getValue(UUID.class, "order_id", rs));
        dto.setCustomerId(JdbcUtils.getValue(UUID.class, "order_customer_id", rs));
        dto.setTotalAmount(JdbcUtils.getValue(Double.class, "order_total_amount", rs));
        dto.setOrderStatus(OrderStatus.valueDeserializer(JdbcUtils.getValue(Long.class, "order_status", rs)));
        dto.setCreatedBy(JdbcUtils.getValue(String.class, "order_created_by", rs));
        dto.setCreatedAt(JdbcUtils.getValue(LocalDateTime.class, "order_created_at", rs));

        return dto;
    }
}
