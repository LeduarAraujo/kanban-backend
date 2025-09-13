package com.facilit.kanban_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration {

    /*
     - addMapping: Essa propriedade mapeia os endpoints que serão aplicados a configuração, ao adicionar “/**” estamos definindo que apartir da raiz todos os endpoints serão afetados.
     - allowedOrigins: Nessa propriedade podemos passar os domínios que poderão acessar a aplicação, no nosso caso estamos usando o coringa * que significa que qualquer domínio poderá consumir os recursos.
     - allowedHeaders: Aqui você pode definir quais os headers que você aceitará na requisição, nesse caso estamos definindo que vamos aceitar todos os headers.
     - allowedMethods: Aqui é possível definir os métodos HTTP que serão permitidos, nessa caso também estamos aceitando todos.
    */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry
                        .addMapping("/**") // addMapping("/api/v1/**")
                        .allowedOrigins("*") // allowedOrigins("example1.com", "examplo2.com")
                        .allowedHeaders("*") // allowedHeaders("Authorization", "Content-Type", "Accept")
                        .allowedMethods("*"); // allowedMethods("GET", "POST", "PUT")
            }
        };
    }
}