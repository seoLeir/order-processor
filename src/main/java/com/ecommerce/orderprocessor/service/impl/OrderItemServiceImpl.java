package com.ecommerce.orderprocessor.service.impl;

import com.ecommerce.orderprocessor.dao.OrderItemDAO;
import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.order.OrderItemDTO;
import com.ecommerce.orderprocessor.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {


    private final OrderItemDAO orderItemDAO;

    @Override
    public Page<OrderItemDTO> getOrderItemsPageList(UUID orderId, Pageable pageable) {
        return orderItemDAO.getOrderItemsPageList(orderId, pageable);
    }

    @Override
    public ResponseDTO createOrderItem(UUID orderId, List<OrderItemDTO> orderItemList, String createdBy) {
        return orderItemDAO.createOrderItem(orderId, orderItemList, createdBy);
    }
}
