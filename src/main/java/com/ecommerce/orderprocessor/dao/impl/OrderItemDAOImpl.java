package com.ecommerce.orderprocessor.dao.impl;

import com.ecommerce.orderprocessor.dao.OrderItemDAO;
import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.order.OrderItemDTO;
import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import com.ecommerce.orderprocessor.mapper.jdbc.OrderItemMapper;
import com.ecommerce.orderprocessor.model.OrderStatus;
import com.ecommerce.orderprocessor.util.JdbcUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderItemDAOImpl implements OrderItemDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<OrderItemDTO> getOrderItemsPageList(UUID orderId, Pageable pageable) {
        String sql = """
                SELECT
                  oe.id AS id,
                  oe.order_id AS order_id,
                  oe.item_id AS order_item_id,
                  oe.quantity AS order_item_quantity,
                  oe.price AS order_item_price,
                  oe.created_by AS order_item_created_by,
                  oe.created_at AS order_item_created_at
                FROM order_items oe
                JOIN orders o on o.id = oe.order_id
                WHERE o.status <> ?
                ORDER BY o.created_at DESC
                """;

        String countSQL = """
                SELECT
                  count(oe.id) AS cnt
                FROM order_items oe
                JOIN orders o on o.id = oe.order_id
                WHERE o.status <> ?
                ORDER BY o.created_at DESC
                """;

        List<OrderItemDTO> orderItemList = jdbcTemplate.query(sql, OrderItemMapper::orderItemMapper, OrderStatus.DELETED.getValue());
        Long orderItemCount = jdbcTemplate.queryForObject(countSQL, Long.class, OrderStatus.DELETED.getValue());

        return new PageImpl<>(orderItemList, pageable, orderItemCount);
    }

    @Override
    public ResponseDTO createOrderItem(UUID orderId, List<OrderItemDTO> orderItemList, String createdBy) {
        String sql = """
                INSERT INTO order_items
                 (
                  order_id,
                  item_id,
                  quantity,
                  price,
                  created_by
                 )
                VALUES (?, ?, ?, ?, ?)
                """;

        int[][] batchAffectedRows = jdbcTemplate.batchUpdate(sql,
                orderItemList,
                orderItemList.size(),
                (ps, argument) -> {
                    ps.setObject(1, orderId);
                    ps.setObject(2, argument.getItemId());
                    ps.setInt(3, argument.getQuantity());
                    ps.setDouble(4, argument.getPrice());
                    ps.setString(5, createdBy);
                });

        ResponseDTO responseDTO = JdbcUtils.batchOperationResponse(batchAffectedRows);

        if (responseDTO.getSuccess()) {
            log.error("Could not insert order items: {}", orderItemList);
            throw new BaseApplicationException("Could not insert order item", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseDTO;
    }
}
