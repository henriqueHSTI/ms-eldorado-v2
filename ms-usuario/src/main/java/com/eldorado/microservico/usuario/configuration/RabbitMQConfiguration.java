package com.eldorado.microservico.usuario.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private static final String QUEUE_NAME = "create-user";

    private static final String EXCHANGE = "create";

    private static final String ROUTING_KEY = "create-routing";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(EXCHANGE);
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        return rabbitTemplate;
    }
}
