package com.ecommerce.orderprocessor.service;

import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.order.OrderItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {

    Page<OrderItemDTO> getOrderItemsPageList(UUID orderId, Pageable pageable);

    ResponseDTO createOrderItem(UUID orderId, List<OrderItemDTO> orderItemList, String createdBy);

}
