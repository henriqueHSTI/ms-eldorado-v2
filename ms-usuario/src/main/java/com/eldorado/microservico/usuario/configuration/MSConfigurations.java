package com.eldorado.microservico.usuario.configuration;

import com.eldorado.commons.security.AuthUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MSConfigurations {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("MS-Usuário")
                .description("MS - Usuario")
                .version("1.0.0"));
    }

    @Bean
    public AuthUtils authUtils() {
        return new AuthUtils();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
