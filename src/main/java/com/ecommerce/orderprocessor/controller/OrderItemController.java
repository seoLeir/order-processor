package com.ecommerce.orderprocessor.controller;

import com.ecommerce.orderprocessor.dto.order.OrderItemDTO;
import com.ecommerce.orderprocessor.security.Permissions;
import com.ecommerce.orderprocessor.service.OrderItemService;
import com.ecommerce.orderprocessor.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders/{orderId}/items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/pageList")
    @PreAuthorize("hasAuthority('" + Permissions.ORDER_ITEM_VIEW + "')")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsPageList(@PathVariable UUID orderId,
                                                                    Pageable pageable) {
        Page<OrderItemDTO> page = orderItemService.getOrderItemsPageList(orderId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(page.getContent());
    }

}
