package com.ecommerce.orderprocessor.controller;

import com.ecommerce.orderprocessor.dto.kafka.OrderEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producer")
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public void sendMessage(@RequestBody OrderEventDTO orderEventDTO) {
        kafkaTemplate.executeInTransaction(operations -> operations.send("orders", orderEventDTO));
    }

}
