package com.eldorado.microservico.autenticacao.controller;

import com.eldorado.commons.dto.UserLoginDto;
import com.eldorado.microservico.autenticacao.feign.UserInterface;
import com.eldorado.microservico.autenticacao.mock.StubUser;
import com.eldorado.microservico.autenticacao.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticatorControllerIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @SneakyThrows
    void createUserAndPersistResult() {
        //arrange
        var user = UserLoginDto.builder().userName("jose.silva").password("123456");

        new StubUser().stubUser(passwordEncoder);
        log.info(objectMapper.writeValueAsString(user));

        //act
        var response = mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(200));

        //assert
        Assertions.assertEquals(201, response.andReturn().getResponse().getStatus());
    }


}