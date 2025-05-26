# 📊 API REST - Consulta de CNPJ

Uma API REST desenvolvida com Spring Boot para consulta de informações de empresas brasileiras a partir do número de CNPJ.

## 🚀 Funcionalidades

* 🔍 Buscar dados de empresas pelo número de CNPJ
* 📍 Informações de endereço, município e UF
* 🏢 Razão social, nome fantasia e natureza jurídica
* 📅 Situação cadastral da empresa

## 🛠️ Tecnologias Utilizadas

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* Banco de dados: PostgreSQL
* https://dados.gov.br/dados/conjuntos-dados/cadastro-nacional-da-pessoa-juridica---cnpj

## 📦 Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/api-cnpj.git
cd api-cnpj
```

### 2. Configure o `application.properties`

Exemplo com `.properties`:

```properties
server.port=8080

# Configurações do banco de dados
spring.datasource.url=jdbc:h2:mem:cnpjdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

Ou rode a classe principal:

```bash
mvn clean package
java -jar target/api-cnpj-0.0.1-SNAPSHOT.jar
```

## ✅ Testes

Execute os testes automatizados com:

```bash
./mvnw test
```

## 📘 Exemplo de Requisição

### [https://cnpjbrasil.pauloduarte.tec.br/swagger-ui/index.html](https://cnpjbrasil.pauloduarte.tec.br/swagger-ui/index.html)

## 📄 Licença

Distribuído sob a licença [WTFPL ](LICENSE).

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para enviar issues ou pull requests.
