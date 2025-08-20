package com.hextech.estoque_api.interfaces.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Estoque-API")
                        .version("v1")
                        .description("API RESTful para gerenciamento de estoque")
                        .contact(new Contact()
                                .name("Suporte Técnico")
                                .email("nathangc7@gmail.com , natanlucaslg1839@gmail.com")));
    }
}
