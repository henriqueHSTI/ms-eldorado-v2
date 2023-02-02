package com.eldorado.microservico.notificacao;

import com.eldorado.commons.configuration.EnableMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMapper
public class MsNotificacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsNotificacaoApplication.class, args);
    }

}
