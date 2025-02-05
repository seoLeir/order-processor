package com.ecommerce.orderprocessor.controller;

import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.security.Permissions;
import com.ecommerce.orderprocessor.service.OrderService;
import com.ecommerce.orderprocessor.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/pageList")
    @PreAuthorize("hasAuthority('" + Permissions.ORDER_VIEW + "')")
    public ResponseEntity<List<OrderDTO>> getOrdersPageList(Pageable pageable) {
        Page<OrderDTO> page = orderService.getOrdersPageList(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(page.getContent());
    }
}
