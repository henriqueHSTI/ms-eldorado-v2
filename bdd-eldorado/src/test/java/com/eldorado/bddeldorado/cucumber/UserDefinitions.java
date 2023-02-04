//package com.eldorado.bddeldorado.cucumber;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//
//@Slf4j
//@RunWith(Cucumber.class)
//@CucumberOptions(
//        tags = "not @ignore",
//        features = {"src/test/resources/features"},
//        snippets = CucumberOptions.SnippetType.CAMELCASE,
//        glue = {
//                "classpath:cucumber"})
//public class UserDefinitions {
//
//
//    @Given("Vou criar um novo usuário")
//    public void vou_criar_um_novo_usuario() {
//        Assertions.assertTrue(true);
//    }
//
//    @When("Eu faco uma requisição")
//    public void eu_faco_uma_requisicao() {
//        Assertions.assertTrue(true);
//    }
//
//    @Then("Eu recebo um payload de usuario")
//    public void eu_recebo_um_payload_de_usuario() {
//        Assertions.assertTrue(true);
//    }
//
//
//    @And("meu statusCode é {int}")
//    public void meuStatusCodeÉ(int arg0) {
//        Assertions.assertTrue(true);
//    }
//}
