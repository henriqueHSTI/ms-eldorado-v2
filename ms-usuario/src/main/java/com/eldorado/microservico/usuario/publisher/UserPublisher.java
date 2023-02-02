package com.eldorado.microservico.usuario.publisher;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue queue;

    public void sendToQueue(String message) {
        rabbitTemplate.convertAndSend("create", "create-routing", message);
    }

}
