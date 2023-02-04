package com.eldorado.bddeldorado.cucumber;

import com.eldorado.commons.dto.UserDto;
import com.eldorado.commons.dto.UserLoginDto;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore",
        features = {"src/test/resources/features"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = {
                "classpath:cucumber"})
@Slf4j
public class UserStepsDefinitions {

    private static final String USER_CREATE_ENDPOINT = "http://localhost:8065/user/create";
    private static final String USER_LOGIN_ENDPOINT = "http://localhost:8065/user/login";


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private UserDto userDto;

    ResponseEntity<UserLoginDto> userLoginResponse;

    private ResponseEntity<UserDto> userDtoResponse;
    private int statusError;
    private String errorMessage;

    @Given("Eu vou criar um payload de usuario")
    public void eu_vou_criar_um_payload_de_usuario() {

        Assertions.assertNull(userDto);

        userDto = UserDto.builder().name("Everton Ribeiro").document("131313").userName("everton.ribeiro@flamengo.com.br")
                .birthDate(LocalDate.parse("1985-08-29")).build();

        Assertions.assertNotNull(userDto);
    }

    @But("O email eh invalido")
    public void o_email_eh_invalido() {
        userDto.setUserName("palmeiras não tem mundial");
    }

    @When("Eu envio o payload para o sistema")
    public void eu_envio_o_payload_para_o_sistema() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        userDtoResponse = restTemplate.postForEntity(USER_CREATE_ENDPOINT, request, UserDto.class);
        Assertions.assertNotNull(userDtoResponse);
    }

    @When("Eu envio o payload para o sistema com erro")
    public void eu_envio_o_payload_para_o_sistema_com_erro() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        try {
            restTemplate.postForEntity(USER_CREATE_ENDPOINT, request, UserDto.class);
        } catch (HttpStatusCodeException e) {
            statusError = e.getStatusCode().value();
            errorMessage = e.getMessage();
        }

        Assertions.assertNotNull(statusError);
        Assertions.assertNotNull(errorMessage);
    }

    @Then("Eu recebo um payload de criacao")
    public void eu_recebo_um_payload_de_criacao() {
        var payload = userDtoResponse.getBody();
        Assertions.assertEquals(userDto.getUserName(), payload.getUserName());
        Assertions.assertEquals(userDto.getDocument(), payload.getDocument());
        Assertions.assertEquals(userDto.getBirthDate(), payload.getBirthDate());
    }

    @Then("meu statusCode eh {int}")
    public void meu_status_code_eh(Integer statusCode) {
        Assertions.assertEquals(statusCode, userDtoResponse.getStatusCode().value());
    }

    @Then("meu statusCode error eh {int}")
    public void meu_status_code_error_eh(Integer status) {
        Assertions.assertEquals(status, statusError);
    }


    @Then("A messagem de erro contem {string}")
    public void a_messagem_de_erro_contem(String message) {
        Assertions.assertTrue(errorMessage.contains(message));
    }






    @Given("O username eh {string}")
    public void o_username_eh(String username) {
        userDto.setUserName(username);
        Assertions.assertEquals(userDto.getUserName(), username);
    }

    @When("Eu vou fazer uma requisição com username {string}")
    public void eu_vou_fazer_uma_requisicao_com_username(String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserLoginDto> request = new HttpEntity<>(UserLoginDto.builder().userName(username).build());
        userLoginResponse = restTemplate.postForEntity(USER_LOGIN_ENDPOINT, request, UserLoginDto.class);

        Assertions.assertNotNull(userLoginResponse);
        Assertions.assertEquals(HttpStatus.OK, userLoginResponse.getStatusCode());

    }

    @When("Eu vou fazer uma requisição com username {string} invalido")
    public void eu_vou_fazer_uma_requisicao_com_username_com_erro(String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserLoginDto> request = new HttpEntity<>(UserLoginDto.builder().userName(username).build());

        try {
            userLoginResponse = restTemplate.postForEntity(USER_LOGIN_ENDPOINT, request, UserLoginDto.class);
        } catch (HttpStatusCodeException e) {
            statusError = e.getStatusCode().value();
            errorMessage = e.getMessage();
        }
        Assertions.assertNotNull(statusError);
        Assertions.assertNotNull(errorMessage);


    }

    @Then("Eu recebo um payload de login")
    public void eu_recebo_um_payload_de_login() {
        var payload = userLoginResponse.getBody();
        Assertions.assertNotNull(payload);
    }

    @Then("o payload contem o campo password e username nao eh nulo")
    public void o_payload_contem_o_campo_password_e_username_nao_eh_nulo() {
        var payload = userLoginResponse.getBody();
        Assertions.assertNotNull(payload.getUserName());
        Assertions.assertNotNull(payload.getPassword());
    }
}
