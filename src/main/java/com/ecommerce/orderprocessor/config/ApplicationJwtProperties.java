package com.ecommerce.orderprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "order.processor.jwt")
public class ApplicationJwtProperties {

    private Duration lifetime;

    private String key;

}
