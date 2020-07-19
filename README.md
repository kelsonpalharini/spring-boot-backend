<h1 align="center">Spring Boot Ionic Backend</h1>

<p align="center">Aplicação backend criada para atualizar meus conhecimentos sobre Spring Framework.
    <br>
</p>

## Conteúdo

- [Começando](#getting_started)
- [Rodando a aplicação](#running)
- [Construído usando](#built_using)

## Começando <a name = "getting_started"></a>

Essas instruções vão te permitir baixar uma cópia do código fonte da aplicação e depois rodar na sua maquina.

### Pré-requisitos

- A primeira ação é baixar o código fonte da aplicação, para isso clone o repositório com o seguinte comando:.

```
git clone https://github.com/kelsonpalharini/spring-boot-ionic-backend.git
```

- A segunda ação é verificar se você possui a versão do Java esperada para rodar a aplicação(11),
  para isso, acesso o console no diretório da aplicação e execute o comando:

```
java --version
```

A saída deve ser similiar a isso

```
java version "11.0.7" 2020-04-14 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.7+8-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.7+8-LTS, mixed mode)
```

Caso contrário instale a versão 11 para que a aplicação execute de forma correta

### Rodando a aplicação <a name = "running"></a>

A aplicação possui 3 profiles para execução:

- dev

Executa a aplicação em modo de desenvolvimento, usando uma instância local do banco de dados MySQL.
Você pode configurar os dados de conexão no arquivo do profile encontrado em:

```
src/main/resources/application-dev.properties
```

- test

Executa a aplicação em modo de teste, usando uma instância local do banco de dados H2.
Você pode configurar os dados de conexão no arquivo do profile encontrado em:

```
src/main/resources/application-test.properties
```

- prod

Executa a aplicação em modo de teste, usando uma instância externa do banco de dados MySQL.
Você pode configurar os dados de conexão no arquivo do profile encontrado em:

```
src/main/resources/application-prod.properties
```

São 3 váriaveis de ambiente responsáveis pela conexão com o banco:

- JDBC_DATABASE_URL: Para o envio da url de conexão;
- JDBC_DATABASE_USERNAME: Para o envio do usuário;
- JDBC_DATABASE_PASSWORD - Para o envio da senha;

O arquivo de configuração de profiles é o:

src/main/resources/application.properties

## Construído usando <a name = "built_using"></a>

- [Java SE Development Kit 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - Development Environment
- [Spring](https://spring.io/projects/spring-boot) - Framework
- [MySQL](https://www.mysql.com/) - Database
- [Cloud SQL - Google Cloud Platform](https://cloud.google.com/sql) - DBaaS
- [H2](https://www.h2database.com/html/main.html) - Database
- [HEROKU](https://www.heroku.com) - PaaS
