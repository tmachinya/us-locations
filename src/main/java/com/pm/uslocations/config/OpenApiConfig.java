package com.pm.uslocations.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("US Locations API")
                        .description("REST API for querying and managing US geographic data — states, counties, places, and climate statistics.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("US Locations")
                                .email("admin@us-locations.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local development server")
                ));
    }
}
