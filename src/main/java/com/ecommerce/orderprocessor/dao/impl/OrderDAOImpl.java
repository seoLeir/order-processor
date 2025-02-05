package com.ecommerce.orderprocessor.dao.impl;

import com.ecommerce.orderprocessor.dao.OrderDAO;
import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.ResponseIdDTO;
import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import com.ecommerce.orderprocessor.mapper.jdbc.OrderMapper;
import com.ecommerce.orderprocessor.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.StatementEvent;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<OrderDTO> getOrdersPageList(Pageable pageable) {
        String sql = """
                SELECT
                  o.id AS id,
                  o.order_id AS order_id,
                  o.customer_id AS order_customer_id,
                  o.total_amount AS order_total_amount,
                  o.status AS order_status,
                  o.created_by AS order_created_by,
                  o.created_at AS order_created_at
                FROM orders o
                WHERE o.status <> ?
                ORDER BY o.created_at DESC
                """;

        String countSQL = """
                SELECT
                  count(o.id) AS cnt
                FROM orders o
                WHERE o.status <> ?
                """;

        List<OrderDTO> orderList = jdbcTemplate.query(sql, OrderMapper::orderMapper, OrderStatus.DELETED.getValue());
        Long orderCount = jdbcTemplate.queryForObject(countSQL, Long.class, OrderStatus.DELETED.getValue());

        return new PageImpl<>(orderList, pageable, orderCount);
    }

    @Override
    public ResponseIdDTO<UUID> createOrder(OrderDTO orderDTO, String createdBy, OrderStatus orderStatus) {
        String sql = """
                INSERT INTO orders
                 (
                  order_id,
                  customer_id,
                  total_amount,
                  status,
                  created_by
                 )
                VALUES (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insertedRowsCount = jdbcTemplate.update(con -> {
                    PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    pst.setObject(1, orderDTO.getOrderId());
                    pst.setObject(2, orderDTO.getCustomerId());
                    pst.setObject(3, orderStatus.getValue());
                    pst.setObject(4, createdBy);
                    pst.setLong(5, orderStatus.getValue());

                    return pst;
                }, keyHolder);

        if (keyHolder.getKeys().isEmpty() || insertedRowsCount != 1) {
            UUID id = (UUID) keyHolder.getKeys().get("id");
            return new ResponseIdDTO<>(id, true, "Successfully created order", HttpStatus.CREATED);
        } else {
            log.error("Could not insert order: {}", orderDTO);
            throw new BaseApplicationException("Could not insert order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
