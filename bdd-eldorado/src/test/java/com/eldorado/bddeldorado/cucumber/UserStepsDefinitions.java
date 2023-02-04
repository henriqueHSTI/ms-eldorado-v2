package com.eldorado.bddeldorado.cucumber;

import com.eldorado.commons.dto.UserDto;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private UserDto userDto;


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
        String url = "http://localhost:8065/user/create";
        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        userDtoResponse = restTemplate.postForEntity(url, request, UserDto.class);
        Assertions.assertNotNull(userDtoResponse);
    }

    @When("Eu envio o payload para o sistema com erro")
    public void eu_envio_o_payload_para_o_sistema_com_erro() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8065/user/create";
        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        try {
            restTemplate.postForEntity(url, request, UserDto.class);
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
}
