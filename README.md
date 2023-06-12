# Desafio Maida Health 2023

### Cafeteria API Restful

API Restful de uma aplicação de gerenciamento de pedidos de uma lanchonete.

### Descrição do desafio

Implementar uma API Restful de uma aplicação de gerenciamento de pedidos de uma lanchonete. Nela, haverá um gestor que
terá como atribuições realizar o cadastro e gerenciamento dos produtos com seus respectivos valores e descrição, além de
gerenciar os pedidos que são realizados pelos clientes, aprovando ou negando. Os clientes só podem realizar os pedidos
após o cadastro na lanchonete, e este poderá acompanhar o andamento dos seus pedidos.

### Tecnologias utilizadas

* [Java 17](https://www.java.com/pt-BR/)
* [Spring Boot 3.1.0](https://spring.io/projects/spring-boot)
* [Postgres](https://www.postgresql.org/)
* [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/)

### Guia de instalação

#### Clone o projeto

`git clone https://github.com/pedroleandrogomesilva/cafeteria.git`

#### Use o IntelliJ IDEA para abrir o projeto

Ao importar o projeto com IntelliJ IDEA, ele irá automaticamente baixar as depedências do arquivo pom.xml utilizando o
maven

#### Crie um banco de dados do projeto

Crie um banco de dados no postgres, pode ser nomeado de cafeteria-db

#### Crie o arquivo application.properties

`crie um arquivo application.properties na pasta ../cafeteria/src/main/resources`

#### Adicione o arquivo application.properties

`spring.datasource.url=jdbc:postgresql://localhost:5432/cafeteria-db`
`spring.datasource.username=postgres`
`spring.datasource.password=SUA_SENHA`
`spring.jpa.hibernate.ddl-auto=update`

`spring.jpa.properties.hibernate.jdbc.lob.non_contectual_creation=true`

#### Importe o arquivo Cafeteria API.postman_collection no Postman

Nesse arquivo irá conter todas as rotas para realizar os testes dos endpoints do desafio

#### Execute o projeto

