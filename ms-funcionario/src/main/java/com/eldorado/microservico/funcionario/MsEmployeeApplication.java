package com.eldorado.microservico.funcionario;

import com.eldorado.commons.configuration.EnableMapper;
import com.eldorado.commons.interception.EnableEldoradoHandler;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableEldoradoHandler
@EnableRabbit
@EnableMapper
public class MsEmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsEmployeeApplication.class, args);
    }

}
