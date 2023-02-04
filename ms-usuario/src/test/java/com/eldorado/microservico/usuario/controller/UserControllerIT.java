package com.eldorado.microservico.usuario.controller;

import com.eldorado.microservico.usuario.domain.repository.UserRepository;
import com.eldorado.microservico.usuario.dto.UserDto;
import com.eldorado.microservico.usuario.testcontainer.MongoDbContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@ContextConfiguration(initializers = UserControllerIT.MongoDbInitializer.class)
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    private static MongoDbContainer mongoDbContainer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    public static void startContainerMongo() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    @AfterEach
    public void clearMongoDb() {
        mongoTemplate.dropCollection("user");
    }

    @Test
    @SneakyThrows
    void createUserAndPersistResult() {
        var user = UserDto.builder().name("Jorge Jesus").document(UUID.randomUUID().toString())
                .userName(String.format("%s@flamengo.com", UUID.randomUUID()))
                .birthDate(LocalDate.now()).build();

        log.info(objectMapper.writeValueAsString(user));

        var response = mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(201));

        Assertions.assertEquals(1, userRepository.findAll().size());
        Assertions.assertEquals(201, response.andReturn().getResponse().getStatus());
    }


    public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    String.format("spring.data.mongodb.host=%s", mongoDbContainer.getHost(),
                            String.format("spring.data.mongodb.port=%s", mongoDbContainer.getPort()))
            );
            values.applyTo(configurableApplicationContext);
        }
    }

}