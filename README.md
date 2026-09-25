# API de Câmbio USD/EUR

API REST desenvolvida em Java com Spring Boot como projeto final do módulo **Arquitetura, Qualidade e Práticas Ágeis**.

O sistema permite cadastrar e consultar clientes, consultar cotações de dólar e euro em tempo real e registrar ordens de compra de moeda estrangeira para retirada em uma agência física.

> **Status do projeto:** em desenvolvimento. A funcionalidade de Cliente, correspondente aos casos de uso UC1 e UC2, já está implementada e testada. As seções de Câmbio, Compra e Segurança devem ser atualizadas após a integração das features da equipe.

## Sumário

1. [Objetivo](#objetivo)
2. [Arquitetura](#arquitetura)
3. [Funcionalidades](#funcionalidades)
4. [Casos de uso](#casos-de-uso)
5. [Tecnologias](#tecnologias)
6. [Estrutura do projeto](#estrutura-do-projeto)
7. [Pré-requisitos](#pré-requisitos)
8. [Como executar](#como-executar)
9. [Autenticação](#autenticação)
10. [Endpoints](#endpoints)
11. [Exemplos da API de Clientes](#exemplos-da-api-de-clientes)
12. [Tratamento de erros](#tratamento-de-erros)
13. [Testes automatizados](#testes-automatizados)
14. [SOLID e Clean Code](#solid-e-clean-code)
15. [Design Pattern](#design-pattern)
16. [Integração com a AwesomeAPI](#integração-com-a-awesomeapi)
17. [Metodologia ágil](#metodologia-ágil)
18. [Equipe](#equipe)

## Objetivo

Construir a API responsável pelo fluxo de compra de moeda estrangeira, contemplando:

- cadastro de clientes;
- consulta de clientes por CPF;
- consulta das cotações USD/BRL e EUR/BRL;
- registro de ordens de compra;
- cálculo do valor total da operação;
- retirada da moeda em agência indicada pelo cliente;
- autenticação obrigatória em todos os endpoints;
- tratamento explícito das exceções de negócio.

## Arquitetura

O grupo adotou a arquitetura de **monolito modular**.

A solução permanece em uma única aplicação Spring Boot, mas as responsabilidades são organizadas em módulos de negócio independentes:

- Cliente;
- Câmbio;
- Compra;
- Segurança.

### Justificativa

O monolito modular foi escolhido porque:

- o projeto possui escopo delimitado e prazo acadêmico reduzido;
- reduz a complexidade de configuração e execução;
- facilita os testes e a demonstração local;
- mantém os domínios separados por packages;
- permite uma futura extração de módulos para microsserviços, caso necessário.

A alternativa de microsserviços com Eureka e OpenFeign foi considerada, mas adicionaria maior complexidade de infraestrutura e integração para o prazo e o tamanho do projeto.

## Funcionalidades

### Cliente

- cadastro de cliente;
- consulta de cliente por CPF;
- geração automática do identificador;
- validação dos campos obrigatórios;
- validação de CPF com 11 dígitos e dígitos verificadores;
- impedimento de CPF duplicado;
- tratamento de cliente não encontrado.

### Câmbio

- consulta de cotação de USD;
- consulta de cotação de EUR;
- integração com a AwesomeAPI;
- tratamento de moeda não suportada;
- tratamento de falha ou timeout da API externa.

> Atualizar esta seção após a integração da feature Câmbio.

### Compra

- validação do cliente;
- obtenção da cotação da moeda;
- cálculo do valor total da operação;
- persistência da ordem de compra;
- validação da agência de retirada;
- consulta da ordem de compra.

> Atualizar esta seção após a integração da feature Compra.

## Casos de uso

### UC1: Cadastrar cliente

O cliente informa nome, CPF, data de nascimento, estado civil e sexo. O sistema valida os dados, impede CPF duplicado, persiste o cliente e retorna `201 Created`.

### UC2: Consultar cliente por CPF

O sistema busca o cliente pelo CPF. Quando encontrado, retorna `200 OK`. Quando não encontrado, retorna `404 Not Found`.

### UC3: Consultar cotação

O sistema consulta a cotação atual de USD ou EUR na AwesomeAPI. Moedas não suportadas devem resultar em `422 Unprocessable Entity`.

### UC4: Registrar ordem de compra

O sistema valida o cliente, obtém a cotação, calcula o valor total, persiste a ordem e retorna `201 Created`. Este caso de uso é obrigatório no projeto.

### UC5: Consultar histórico de compras

Caso de uso opcional para listar as ordens de compra associadas ao CPF do cliente.

## Tecnologias

- Java 17;
- Spring Boot 4.1.1;
- Spring Web MVC;
- Spring Data JPA;
- Spring Validation;
- Spring Security;
- H2 Database;
- Maven;
- JUnit;
- Mockito;
- Postman;
- Lombok, disponível no projeto, mas não obrigatório nas classes já implementadas.

## Estrutura do projeto

```text
src/main/java/com/example/cambio
├── CambioApplication.java
├── cliente
│   ├── api
│   │   └── ClienteController.java
│   ├── application
│   │   └── ClienteService.java
│   ├── domain
│   │   ├── Cliente.java
│   │   ├── EstadoCivil.java
│   │   └── Sexo.java
│   ├── dto
│   │   ├── CadastrarClienteRequest.java
│   │   └── ClienteResponse.java
│   ├── exception
│   │   ├── ApiErrorResponse.java
│   │   ├── ClienteNaoEncontradoException.java
│   │   ├── CpfJaCadastradoException.java
│   │   └── GlobalExceptionHandler.java
│   └── infrastructure
│       └── ClienteRepository.java
└── security
    └── SecurityConfig.java
```

Após a integração, adicionar à árvore os módulos `cambio` e `compra` conforme a implementação real da equipe.

## Pré-requisitos

- Java 17 instalado;
- Maven 3.6.3 ou superior, ou Maven Wrapper incluído no projeto;
- IntelliJ IDEA ou outra IDE compatível;
- Postman, Insomnia ou cURL para testar os endpoints.

## Como executar

### Pela IDE

1. Abra o projeto na IDE.
2. Aguarde o Maven carregar as dependências.
3. Execute a classe:

```text
com.example.cambio.CambioApplication
```

4. A aplicação será disponibilizada, por padrão, em:

```text
http://localhost:8080
```

### Pelo Maven Wrapper

No Windows:

```bash
mvnw.cmd clean test
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

### Banco H2

Configuração utilizada durante o desenvolvimento:

```properties
spring.datasource.url=jdbc:h2:mem:cambio
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

O banco está em memória. Portanto, os dados são removidos quando a aplicação é encerrada.

## Autenticação

Todos os endpoints devem exigir autenticação.

Durante o desenvolvimento da feature Cliente foi utilizado HTTP Basic Auth do Spring Security:

```text
Username: user
Password: senha gerada no console durante a inicialização
```

A configuração definitiva de segurança deve ser consolidada na integração da equipe. As opções aceitas pelo projeto são:

- HTTP Basic Auth;
- API Key fixa no cabeçalho `X-API-KEY`;
- JWT como implementação bônus.

Credenciais e chaves não devem ser gravadas diretamente em repositório público. Devem ser fornecidas por configuração local ou variável de ambiente.

## Endpoints

### Cliente

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| POST | `/api/clientes` | Cadastra cliente | 201, 400, 409 |
| GET | `/api/clientes/{cpf}` | Consulta cliente por CPF | 200, 404 |

### Câmbio

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| GET | `/api/cambio/cotacao/{moeda}` | Consulta cotação atual | 200, 422, 503 |

### Compra

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| POST | `/api/compras` | Registra ordem de compra | 201, 400, 404, 422 |
| GET | `/api/compras/{id}` | Consulta compra por ID | 200, 404 |
| GET | `/api/compras/cliente/{cpf}` | Consulta histórico do cliente | 200 |

> Os endpoints de Câmbio e Compra deverão ser confirmados após o merge das features.

## Exemplos da API de Clientes

### Cadastrar cliente

```http
POST /api/clientes
Authorization: Basic <credenciais>
Content-Type: application/json
```

```json
{
  "nome": "Cliente Teste",
  "cpf": "52998224725",
  "dataNascimento": "1990-05-20",
  "estadoCivil": "SOLTEIRO",
  "sexo": "FEMININO"
}
```

O CPF deve ser informado com **11 dígitos, sem pontos ou hífen**.

Resposta de sucesso:

```http
HTTP/1.1 201 Created
```

```json
{
  "id": 1,
  "nome": "Cliente Teste",
  "cpf": "52998224725",
  "dataNascimento": "1990-05-20",
  "estadoCivil": "SOLTEIRO",
  "sexo": "FEMININO"
}
```

### Consultar cliente por CPF

```http
GET /api/clientes/52998224725
Authorization: Basic <credenciais>
```

Resposta de sucesso:

```http
HTTP/1.1 200 OK
```

### CPF duplicado

```http
HTTP/1.1 409 Conflict
```

```json
{
  "timestamp": "2026-09-25T11:55:00",
  "status": 409,
  "mensagem": "CPF já cadastrado."
}
```

### Cliente não encontrado

```http
HTTP/1.1 404 Not Found
```

```json
{
  "timestamp": "2026-09-25T11:55:00",
  "status": 404,
  "mensagem": "Cliente com CPF 99999999999 não encontrado."
}
```

## Tratamento de erros

As exceções de negócio são convertidas em respostas HTTP por um `GlobalExceptionHandler`.

Formato utilizado:

```json
{
  "timestamp": "2026-09-25T11:55:00",
  "status": 400,
  "mensagem": "Descrição do erro."
}
```

| Situação | Status | Mensagem |
|---|---:|---|
| Dados cadastrais inválidos | 400 | Mensagem da validação |
| Cliente não encontrado | 404 | `Cliente com CPF {cpf} não encontrado.` |
| CPF já cadastrado | 409 | `CPF já cadastrado.` |
| Moeda não suportada | 422 | `Moeda 'XYZ' não é suportada. Utilize USD ou EUR.` |
| Falha na API externa | 503 | `Não foi possível obter a cotação no momento. Tente novamente.` |
| Credencial ausente ou inválida | 401 | `Não autenticado.` |

Antes da entrega, a equipe deve garantir que o ambiente não exponha stack traces nas respostas HTTP.

## Testes automatizados

A feature Cliente utiliza JUnit e Mockito para testar o `ClienteService` isoladamente.

Testes implementados:

```text
deveCadastrarClienteComSucesso
deveLancarExcecaoQuandoCpfJaCadastrado
```

O teste de CPF duplicado também verifica que o Repository não executa a persistência:

```java
verify(clienteRepository, never())
        .save(any(Cliente.class));
```

### Executar testes

No Windows:

```bash
mvnw.cmd test
```

No Linux ou macOS:

```bash
./mvnw test
```

## SOLID e Clean Code

### Single Responsibility Principle, SRP

As responsabilidades foram separadas entre as camadas:

- `ClienteController`: comunicação HTTP;
- `ClienteService`: regras de negócio;
- `ClienteRepository`: persistência;
- DTOs: entrada e saída da API;
- `Cliente`: entidade persistida;
- `GlobalExceptionHandler`: conversão das exceções em respostas HTTP.

### Injeção de dependência por construtor

```java
private final ClienteRepository clienteRepository;

public ClienteService(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
}
```

A injeção por construtor reduz o acoplamento e permite substituir o Repository por um mock nos testes unitários.

### Clean Code

Foram adotadas as seguintes práticas:

- nomes de classes e métodos que expressam intenção;
- métodos curtos e coesos;
- ausência de código morto ou comentado;
- Controller sem regras de negócio;
- conversão entre entidade e DTO centralizada no método `toResponse`;
- exceções específicas para regras de negócio.

## Design Pattern

O padrão recomendado para a integração externa é o **Adapter**.

O Adapter encapsula a comunicação com a AwesomeAPI e converte sua resposta externa para o modelo interno de cotação. Dessa forma, alterações no formato da API externa ficam isoladas da regra de negócio.

> Atualizar esta seção com os nomes reais das interfaces e classes após a integração da feature Câmbio.

O serviço de Compra também pode atuar como uma **Facade**, oferecendo uma operação simples para coordenar cliente, cotação, cálculo e persistência. A equipe deverá citar apenas os patterns efetivamente implementados.

## Integração com a AwesomeAPI

A consulta de cotação deve utilizar os pares:

```text
USD-BRL
EUR-BRL
```

A aplicação deve aceitar somente as moedas:

```text
USD
EUR
```

Moedas diferentes devem resultar em `422 Unprocessable Entity`. Falhas e timeouts da integração devem ser tratados sem expor erro genérico ao consumidor.

> Atualizar esta seção com a URL configurada, campo de cotação adotado e estratégia de timeout após o merge.

## Metodologia ágil

O projeto é organizado em duas sprints e acompanhado por board Kanban com as colunas:

```text
To Do
Doing
Done
```

### Sprint 1

Objetivo:

- concluir UC1, UC2 e UC3;
- definir e documentar a arquitetura;
- configurar a estrutura inicial da aplicação.

### Sprint 2

Objetivo:

- concluir o UC4;
- integrar as features;
- consolidar autenticação;
- aplicar e justificar o Design Pattern;
- revisar SOLID e Clean Code;
- executar testes;
- concluir documentação e apresentação.

O board deve registrar evidências reais de movimentação dos cards durante as sprints.

## Equipe

| Integrante | Responsabilidade | Papel Scrum |
|---|---|---|
| Anna Paula Mattar | Feature Cliente, UC1 e UC2 | Preencher |
| Integrante 2 | Feature Câmbio, UC3 | Preencher |
| Integrante 3 | Feature Compra, UC4 e integração | Preencher |

Preencher os nomes completos e os papéis de Product Owner, Scrum Master e Dev Team antes da entrega.

## Evidências concluídas da feature Cliente

- `POST /api/clientes` com retorno `201 Created`;
- tentativa de cadastrar CPF duplicado com retorno `409 Conflict`;
- requisição inválida com retorno `400 Bad Request`;
- `GET /api/clientes/{cpf}` com retorno `200 OK`;
- consulta de CPF inexistente com retorno `404 Not Found`;
- acesso sem credencial com retorno `401 Unauthorized`;
- dois testes unitários executados com sucesso.

## Pendências antes da entrega

- integrar os módulos Câmbio e Compra;
- confirmar a implementação definitiva de autenticação;
- garantir uma única `SecurityConfig` após o merge;
- mover os componentes globais de erro para package compartilhado, caso sejam reutilizados pelos demais módulos;
- atualizar a documentação do Adapter com os nomes reais das classes;
- confirmar endpoints e exemplos de Câmbio e Compra;
- adicionar nomes e papéis dos integrantes;
- adicionar link ou imagem do board Kanban;
- executar `mvnw.cmd clean test` no projeto integrado;
- testar o fluxo completo do UC4 no Postman;
- gerar o arquivo ZIP final para upload individual no LMS.
