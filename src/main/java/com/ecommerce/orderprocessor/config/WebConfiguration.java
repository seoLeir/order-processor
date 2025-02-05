package com.ecommerce.orderprocessor.config;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements ServletContextInitializer {

    private final Environment env;

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.setRequestCharacterEncoding(StandardCharsets.UTF_8.name());
        servletContext.setResponseCharacterEncoding(StandardCharsets.UTF_8.name());

        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
