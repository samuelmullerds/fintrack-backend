# FinTrack

API REST para controle de gastos pessoais desenvolvida com Java e Spring Boot.

## Tecnologias

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- Swagger

## Funcionalidades

- Cadastro e autenticação de usuários com JWT
- Registro de transações financeiras (receitas e despesas)
- Dashboard com saldo atual, total de receitas e despesas
- Resumo de gastos agrupado por categoria
- Redefinição de senha por e-mail
- Listagem de transações paginada

## Como executar

1. Clone o repositório
2. Configure o banco de dados MySQL e as credenciais no `application.properties`
3. Execute com `mvn spring-boot:run`
4. Acesse a documentação em `http://localhost:8080/swagger-ui.html`
