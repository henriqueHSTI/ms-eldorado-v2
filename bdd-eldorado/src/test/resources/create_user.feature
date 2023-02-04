Feature: Criação de Usuario

  Scenario: Envio de Payload para criação de usuário com sucesso
    Given Eu vou criar um payload de usuario
    When Eu envio o payload para o sistema
    Then Eu recebo um payload de criacao
    And meu statusCode eh 200

  Scenario: Envio de Payload para criação de usuário com falha
    Given Eu vou criar um payload de usuario
    But O email eh invalido
    When Eu envio o payload para o sistema com erro
    Then A messagem de erro contem 'Invalid email'
    And meu statusCode error eh 400

  Scenario: Busca de Usuario no sistema com sucesso
    Given Eu vou criar um payload de usuario
    And O username eh 'matyyeh@gmail.com'
    When Eu envio o payload para o sistema
    And Eu vou fazer uma requisição com username 'matyyeh@gmail.com'
    Then Eu recebo um payload de login
    And meu statusCode eh 200
    And o payload contem o campo password e username nao eh nulo


  Scenario: Busca de Usuario no sistema com falha
    Given Eu vou criar um payload de usuario
    When Eu envio o payload para o sistema
    And Eu vou fazer uma requisição com username 'palmeirasnaotemmundial@gmail.com' invalido
    And meu statusCode error eh 404
