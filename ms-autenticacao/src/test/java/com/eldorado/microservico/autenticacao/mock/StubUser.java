package com.eldorado.microservico.autenticacao.mock;

import com.eldorado.commons.dto.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StubUser {

    @SneakyThrows
    public void stubUser(PasswordEncoder passwordEncoder) {
        WireMockServer wireMockServer = new WireMockServer();
        configureFor("localhost", 8089);
        var objectMapper = new ObjectMapper();
        var userlogin = UserLoginDto.builder().userName("hose.silva").password(passwordEncoder.encode("123456")).build();
        stubFor(post("/auth").withHeader("Content-Type", containing("json")).willReturn(ok().withHeader("Content-Type", "text/xml").withBody(objectMapper.writeValueAsString(userlogin))));
        wireMockServer.start();
    }

}
