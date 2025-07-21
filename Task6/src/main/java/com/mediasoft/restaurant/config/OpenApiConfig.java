package com.mediasoft.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantRatingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Система оценки ресторанов API")
                        .description("RESTful API для системы оценки ресторанов. " +
                                "Позволяет управлять посетителями, ресторанами и отзывами.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mediasoft")
                                .email("info@mediasoft.com")
                                .url("https://mediasoft.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
} 