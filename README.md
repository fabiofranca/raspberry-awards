# Golden Raspberry Awards

Aplicação spring-boot web para possibilitar a leitura da lista de indicados e vencedores da
categoria Pior Filme do Golden Raspberry Awards.

## Softwares / runtimes necessários
* JDK 21
* Git

Clone a aplicação do git

Se estiver usando linux/mac será necessário conceder permissão de execução ao maven antes de executar qualquer ação 
na aplicação:

`chmod +X mvnw`

#### Executar os testes

Na raiz do projeto executar o comando :

`./mvnw test`

#### Executar a aplicação web
Para executar a aplicação rode o seguinte comando

<./mvnw spring-boot:start>

A aplicação estará acessível na url base
<localhost:8080/movies>

Para parar a aplicação execute

`./mvnw spring-boot:stop`

A aplicação executa o banco de dados H2 em memória, ou seja, os dados serão perdidos ao parar a aplicação

Para acessar o console do banco use a url <http://localhost:8080/h2-console/login.do>. Usuário/senha sa/sa

## Acessando os endpoints

As definições das requisições estão disponíveis no swagger do projeto em 
<http://localhost:8080/swagger-ui/index.html>
