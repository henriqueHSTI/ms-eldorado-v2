package com.eldorado.microservico.usuario;

import com.eldorado.commons.configuration.EnableMapper;
import com.eldorado.commons.interception.EnableAuthorization;
import com.eldorado.commons.interception.EnableEldoradoHandler;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableRabbit
@EnableAuthorization
@EnableEldoradoHandler
@EnableMapper
public class MsUsuarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsUsuarioApplication.class, args);
    }

}
