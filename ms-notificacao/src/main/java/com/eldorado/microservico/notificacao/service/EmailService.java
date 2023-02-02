package com.eldorado.microservico.notificacao.service;

import com.eldorado.microservico.notificacao.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final ObjectMapper objectMapper;

    private final ModelMapper modelMapper;

    private final JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendEmail(String message) {

        var messageDto = objectMapper.readValue(message, MessageDto.class);

        var simpleMailMessage = modelMapper.map(messageDto, SimpleMailMessage.class);

        simpleMailMessage.setText(messageDto.getMessage());
        simpleMailMessage.setFrom("noreply@hsti.eng.br");
        javaMailSender.send(simpleMailMessage);

    }
}
