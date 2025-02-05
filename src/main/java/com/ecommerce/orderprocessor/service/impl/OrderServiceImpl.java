package com.ecommerce.orderprocessor.service.impl;

import com.ecommerce.orderprocessor.dao.OrderDAO;
import com.ecommerce.orderprocessor.dto.ResponseIdDTO;
import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.model.OrderStatus;
import com.ecommerce.orderprocessor.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Override
    public Page<OrderDTO> getOrdersPageList(Pageable pageable) {
        return orderDAO.getOrdersPageList(pageable);
    }

    @Override
    public ResponseIdDTO<UUID> createOrder(OrderDTO orderDTO, String userLogin, OrderStatus orderStatus) {
        return orderDAO.createOrder(orderDTO, userLogin, orderStatus);
    }
}
