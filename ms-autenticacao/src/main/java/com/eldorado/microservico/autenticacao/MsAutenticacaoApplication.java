package com.eldorado.microservico.autenticacao;

import com.eldorado.commons.configuration.EnableMapper;
import com.eldorado.commons.interception.EnableEldoradoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEldoradoHandler
@EnableMapper
public class MsAutenticacaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsAutenticacaoApplication.class, args);
    }

}
