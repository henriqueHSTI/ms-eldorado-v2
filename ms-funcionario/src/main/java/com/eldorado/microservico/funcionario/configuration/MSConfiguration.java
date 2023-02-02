package com.eldorado.microservico.funcionario.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MSConfiguration {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("MS-Funcionario")
                .description("MS - Funcionario")
                .version("1.0.0"));
    }

}
