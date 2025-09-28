package com.plazoleta.plazoleta_service.infraestructure.driver.rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Restaurant Service API",
        version = "1.0",
        description = "API para gestión de una plazoleta"
    )
)
@Configuration
public class OpenApiConfig {
}

