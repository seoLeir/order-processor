package com.ecommerce.orderprocessor.listener;

import com.ecommerce.orderprocessor.dto.ResponseIdDTO;
import com.ecommerce.orderprocessor.dto.kafka.OrderEventDTO;
import com.ecommerce.orderprocessor.dto.order.OrderDTO;
import com.ecommerce.orderprocessor.mapper.dto.OrderMapper;
import com.ecommerce.orderprocessor.model.OrderStatus;
import com.ecommerce.orderprocessor.service.OrderItemService;
import com.ecommerce.orderprocessor.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProcessorListener {

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(
            topics = "orders",
            groupId = "order-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional(rollbackFor = Exception.class)
    public void consume(ConsumerRecord<String, OrderEventDTO> record, Acknowledgment ack) {
        log.info("Incoming info: " + record.value().toString());

        try {
            OrderDTO orderDTO = OrderMapper.orderMapperFromEvent(record.value());

            // processing
            log.info("Saving order: {}", record.value());
            ResponseIdDTO<UUID> orderResponse = orderService.createOrder(orderDTO, "KAFKA", OrderStatus.NEW);

            log.info(orderResponse.getMessage());

            orderItemService.createOrderItem(orderResponse.getId(), orderDTO.getItemList(), "KAFKA");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            sendToDeadLetterQueue(String.format("Cause : %s, failed message : %s", e.getCause(), e.getMessage()), record.value(), "orders-dlq");
        } finally {
            ack.acknowledge();
        }
    }

    @DltHandler
    public void sendToDeadLetterQueue(String message, OrderEventDTO orderEventDTO, String topic) {
        String topicMessage = "Event on dlt topic= %s, payload=%s, message: %s".formatted(topic, orderEventDTO, message);
        log.error("Dead letter queue: {}", message);
        kafkaTemplate.executeInTransaction(operations -> operations.send(topic, topicMessage));
    }

}
