package com.outercode.ApiMongoDB.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return  new OpenAPI().info(new Info()
                .title("API REST with MongoDB")
                .description("API from manager posts and comments of users")
                .version("1.0.0")
                .contact(new Contact().name("Vladimir Monteiro").email("vladimir.monteiro021@gmail.com"))
        );

    }
}
