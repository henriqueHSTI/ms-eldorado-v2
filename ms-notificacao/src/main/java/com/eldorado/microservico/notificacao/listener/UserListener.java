package com.eldorado.microservico.notificacao.listener;

import com.eldorado.microservico.notificacao.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserListener {

    private final EmailService emailService;

    @RabbitListener(queues = "${user.queue.name-create}")
    public void receive(String message) {
        emailService.sendEmail(message);
        log.info("Consumer: {}", message);
    }
}
