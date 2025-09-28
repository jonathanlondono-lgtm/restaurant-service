package com.plazoleta.plazoleta_service.infraestructure.driven.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserRestClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

