package com.facilit.kanban_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kanban Backend API")
                        .description("API para gerenciamento de Projetos, Responsáveis e Resumos através de um quadro Kanban")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Facilit")
                                .email("contato@facilit.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor de Desenvolvimento"),
                        new Server().url("https://api.kanban.facilit.com").description("Servidor de Produção")
                ));
    }
}
