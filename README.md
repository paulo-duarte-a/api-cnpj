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

### http://localhost:8080/swagger-ui/index.html

# API CNPJ Brasil

## Description

API para consulta de dados de CNPJ

## Contact

**Name:** Paulo Duarte
**URL:** [https://pauloduarte.tec.br](https://pauloduarte.tec.br)
**Email:** contato@pauloduarte.tec.br

## License

**Name:** Apache 2.0
**URL:** [https://www.apache.org/licenses/LICENSE-2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Version

1.0.0

## Servers

### Generated server url
**URL:** http://localhost:8080

## Security

### bearerAuth
Type: HTTP
Scheme: bearer
Bearer format: JWT

## Endpoints

### /api/auth/register
**POST**

**Tags:** `auth-controller`

**Operation ID:** `registerUser`

**Request Body:**
* **Content Type:** `application/json`
    * **Schema:** [$ref](#schemaregisterrequest)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** `object`

### /api/auth/login
**POST**

**Tags:** `auth-controller`

**Operation ID:** `authenticateUser`

**Request Body:**
* **Content Type:** `application/json`
    * **Schema:** [$ref](#schemaloginrequest)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** `object`

### /api/socios
**GET**

**Tags:** `Sócios`

**Operation ID:** `getAllSocios`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"idSocio"`
* **cnpjBasico** (query, optional):
    * Schema: `string`
* **identificadorSocio** (query, optional):
    * Schema: `integer` (int32)
* **nomeSocio** (query, optional):
    * Schema: `string`
* **qualificacaoSocioCodigo** (query, optional):
    * Schema: `string`
* **paisCodigo** (query, optional):
    * Schema: `string`
* **faixaEtaria** (query, optional):
    * Schema: `integer` (int32)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagesocio)

### /api/socios/{id}
**GET**

**Tags:** `Sócios`

**Operation ID:** `getSocioById`

**Parameters:**
* **id** (path, required):
    * Schema: `integer` (int64)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemasocio)

### /api/simples
**GET**

**Tags:** `Simples`

**Operation ID:** `getAllSimples`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"idSimples"`
* **cnpjBasico** (query, optional):
    * Schema: `string`
* **opcaoPeloSimples** (query, optional):
    * Schema: `string`
* **opcaoPeloMei** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagesimples)

### /api/simples/{id}
**GET**

**Tags:** `Simples`

**Operation ID:** `getSimplesById`

**Parameters:**
* **id** (path, required):
    * Schema: `integer` (int64)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemasimples)

### /api/qualificacoes-socios
**GET**

**Tags:** `Qualificação do Sócio`

**Operation ID:** `getAllQualificacoesSocios`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"codigo"`
* **codigo** (query, optional):
    * Schema: `string`
* **descricao** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagequalificacaosocio)

### /api/paises
**GET**

**Tags:** `Países`

**Operation ID:** `getAllPaises`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"codigo"`
* **codigo** (query, optional):
    * Schema: `string`
* **descricao** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagepais)

### /api/naturezas-juridicas
**GET**

**Tags:** `Natureza Jurídica`

**Operation ID:** `getAllNaturezasJuridicas`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"codigo"`
* **codigo** (query, optional):
    * Schema: `string`
* **descricao** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagenaturezajuridica)

### /api/municipios
**GET**

**Tags:** `Municipios`

**Operation ID:** `getAllPaises_1`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"codigo"`
* **codigo** (query, optional):
    * Schema: `string`
* **descricao** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagemunicipio)

### /api/estabelecimentos
**GET**

**Tags:** `Estabelecimentos`

**Operation ID:** `getAllEstabelecimentos`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"idEmpresa"`
* **cnpjBasico** (query, optional):
    * Schema: `string`
* **identificadorMatrizFilial** (query, optional):
    * Schema: `integer` (int32)
* **nomeFantasia** (query, optional):
    * Schema: `string`
* **situacaoCadastral** (query, optional):
    * Schema: `integer` (int32)
* **uf** (query, optional):
    * Schema: `string`
* **municipioCodigo** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapageestabelecimento)

### /api/estabelecimentos/{id}
**GET**

**Tags:** `Estabelecimentos`

**Operation ID:** `getEstabelecimentoById`

**Parameters:**
* **id** (path, required):
    * Schema: `integer` (int64)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemaestabelecimento)

### /api/empresas
**GET**

**Tags:** `Empresas`

**Operation ID:** `getAllEmpresas`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"idEmpresa"`
* **cnpjBasico** (query, optional):
    * Schema: `string`
* **razaoSocial** (query, optional):
    * Schema: `string`
* **naturezaJuridicaCodigo** (query, optional):
    * Schema: `string`
* **qualificacaoResponsavelCodigo** (query, optional):
    * Schema: `string`
* **porteEmpresa** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapageempresa)

### /api/empresas/{id}
**GET**

**Tags:** `Empresas`

**Operation ID:** `getEmpresaById`

**Parameters:**
* **id** (path, required):
    * Schema: `integer` (int64)

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemaempresa)

### /api/cnaes
**GET**

**Tags:** `Atividade Econômica`

**Operation ID:** `getAllPaises_2`

**Parameters:**
* **page** (query, optional):
    * Schema: `integer` (int32), default: `0`
* **size** (query, optional):
    * Schema: `integer` (int32), default: `10`
* **sort** (query, optional):
    * Schema: `string`, default: `"codigo"`
* **codigo** (query, optional):
    * Schema: `string`
* **descricao** (query, optional):
    * Schema: `string`

**Responses:**
* **200 OK**
    * **Content Type:** `*/*`
        * **Schema:** [$ref](#schemapagecnae)

## Schemas

### RegisterRequest
```json
{
  "type": "object",
  "properties": {
    "email": {
      "type": "string"
    },
    "senha": {
      "type": "string"
    }
  }
}
````

## 📄 Licença

Distribuído sob a licença [MIT](LICENSE).

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para enviar issues ou pull requests.
