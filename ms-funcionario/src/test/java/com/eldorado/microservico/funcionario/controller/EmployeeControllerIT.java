package com.eldorado.microservico.funcionario.controller;

import com.eldorado.microservico.funcionario.domain.EmployeeEntity;
import com.eldorado.microservico.funcionario.dto.CustomEmployeeDto;
import com.eldorado.microservico.funcionario.dto.EmployeeDto;
import com.eldorado.microservico.funcionario.mocks.EmployeeMock;
import com.eldorado.microservico.funcionario.repository.EmployeeRepository;
import com.eldorado.microservico.funcionario.testcontainer.MongoDbContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@ContextConfiguration(initializers = EmployeeControllerIT.MongoDbInitializer.class)
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public ModelMapper modelMapper;

    private static MongoDbContainer mongoDbContainer;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    public static void startContainerMongo() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    @Test
    @SneakyThrows
    void createEmployeeAndPersistResult() {
        var employee = EmployeeDto.builder().name("Matheus Wiener").email("matheusowo1@gmail.com").build();

        log.info(objectMapper.writeValueAsString(employee));

        var response = mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().is(200));

        Assertions.assertEquals(1, employeeRepository.findAll().size());
        Assertions.assertEquals(200, response.andReturn().getResponse().getStatus());
    }

    @Test
    @SneakyThrows
    void getAllEmployeesWithSuccess() {

        var entity = EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .name("Matheus Wiener")
                .email("matheuswiener9@gmail.com")
                .createAt(LocalDateTime.now())
                .password("123456")
                .createBy("Matheus")
                .build();

        employeeRepository.save(entity);

        var response = mockMvc.perform(get("/employee/all")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(200));

        String responseBody = response.andReturn().getResponse().getContentAsString();

        log.info(responseBody);

        var employeeResponse = objectMapper.readValue(responseBody, CustomEmployeeDto.class);

        Assertions.assertEquals("matheuswiener9@gmail.com", employeeResponse.get(0).getEmail());
        Assertions.assertEquals(200, response.andReturn().getResponse().getStatus());
    }

    @Test
    @SneakyThrows
    void getEmployeeByIdWithSuccess() {

        UUID employeeId = UUID.randomUUID();

        var entity = EmployeeEntity.builder()
                .id(employeeId)
                .name("Matheus Wiener")
                .email("matheuswiener9@gmail.com")
                .createAt(LocalDateTime.now())
                .password("123456")
                .createBy("Matheus")
                .build();

        employeeRepository.save(entity);

        var user = EmployeeMock.createValidUserDtoMatheus();

        var response = mockMvc.perform(get(String.format("/employee/%s", employeeId))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(200));

        String responseBody = response.andReturn().getResponse().getContentAsString();

        log.info(responseBody);

        var employeeResponse = objectMapper.readValue(responseBody, EmployeeDto.class);

        Assertions.assertEquals("matheuswiener9@gmail.com", employeeResponse.getEmail());
        Assertions.assertEquals(200, response.andReturn().getResponse().getStatus());
    }

    @AfterEach
    public void clearMongoDb() {
        mongoTemplate.dropCollection("Employee");
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
