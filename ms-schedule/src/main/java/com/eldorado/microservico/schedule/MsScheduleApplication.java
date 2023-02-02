package com.eldorado.microservico.schedule;

import com.eldorado.commons.configuration.EnableMapper;
import com.eldorado.commons.interception.EnableAuthorization;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableMapper
//@EnableAuthorization
@EnableRabbit
@EnableFeignClients
public class MsScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsScheduleApplication.class, args);
    }

}
