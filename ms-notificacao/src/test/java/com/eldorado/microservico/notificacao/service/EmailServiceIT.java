package com.eldorado.microservico.notificacao.service;

import com.eldorado.microservico.notificacao.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceIT {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void sendEmailTest(){
        MessageDto email = MessageDto.builder().to("test").subject("teste").message("Message test").build();

        emailService.sendEmail(email.getMessage());

        verify(javaMailSender, times(1));
    }
}
