package com.example.vila_tour.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VilaTourConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("VilaTour API")
                        .description("VilaTour API REST")
                        .contact(new Contact()
                                .name("Team AJO")
                                .email("contacto@vilaTour.es")
                                .url("www.vilatour.es"))
                        .version("1.0"));
    }
}