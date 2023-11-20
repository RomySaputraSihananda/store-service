package com.romys.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfiguration {
        final private String securitySchemeName = "bearerAuth";

        @Bean
        public OpenAPI apiInfo() {
                return new OpenAPI()
                                .addSecurityItem(new SecurityRequirement()
                                                .addList(this.securitySchemeName))
                                .components(new Components()
                                                .addSecuritySchemes(this.securitySchemeName, new SecurityScheme()
                                                                .name(this.securitySchemeName)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")))
                                .info(new Info()
                                                .title("Store Service Rest API")
                                                .version("v1.0.0")
                                                .description("simple service for managing products such as store apps"));
        }
}
