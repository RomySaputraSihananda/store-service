package com.romys.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Store Service Rest API").version("v1.0.0"));
    }
}
