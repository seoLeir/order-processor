package com.ecommerce.orderprocessor.service;

import com.ecommerce.orderprocessor.dto.ResponseIdDTO;
import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {

    Page<OrderDTO> getOrdersPageList(Pageable pageable);

    ResponseIdDTO<UUID> createOrder(OrderDTO orderDTO, String userLogin, OrderStatus orderStatus);

}
