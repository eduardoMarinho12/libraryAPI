package com.library.libraryapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library API")
                        .version("1.0.0")
                        .description("API REST para cadastro de livros, usuarios e controle de emprestimos/devolucoes.")
                        .contact(new Contact().name("Library API")))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Ambiente local")));
    }
}
