# API de Câmbio USD/EUR

API REST desenvolvida em Java com Spring Boot como projeto final do módulo **Arquitetura, Qualidade e Práticas Ágeis**.

O sistema permite cadastrar e consultar clientes, consultar cotações de dólar e euro em tempo real e registrar ordens de compra de moeda estrangeira para retirada em uma agência física.

> **Status:** projeto em desenvolvimento. A feature Cliente está implementada na branch `feat/cliente`. As features Câmbio, Compra e Segurança serão consolidadas na integração da equipe.

## Sumário

1. [Objetivo](#objetivo)
2. [Arquitetura](#arquitetura)
3. [Tecnologias](#tecnologias)
4. [Estrutura do projeto](#estrutura-do-projeto)
5. [Como executar](#como-executar)
6. [Swagger e OpenAPI](#swagger-e-openapi)
7. [Feature Cliente](#feature-cliente)
8. [Endpoints de Cliente](#endpoints-de-cliente)
9. [Validações](#validações)
10. [Tratamento de erros](#tratamento-de-erros)
11. [Testes automatizados](#testes-automatizados)
12. [SOLID e Clean Code](#solid-e-clean-code)
13. [Logging](#logging)
14. [Git e Kanban](#git-e-kanban)
15. [Evidências da Sprint 1](#evidências-da-sprint-1)
16. [Pendências](#pendências)

## Objetivo

Construir a API responsável pelo fluxo de compra de moeda estrangeira, contemplando:

- cadastro de clientes;
- consulta de clientes por CPF;
- consulta das cotações USD/BRL e EUR/BRL;
- registro de ordens de compra;
- cálculo do valor total da operação;
- retirada da moeda em agência indicada pelo cliente;
- autenticação dos endpoints;
- tratamento explícito das exceções de negócio.

## Arquitetura

O grupo adotou a arquitetura de **monolito modular**.

A aplicação permanece em um único projeto Spring Boot, mas as responsabilidades são separadas em módulos:

- Cliente;
- Câmbio;
- Compra;
- Segurança;
- componentes compartilhados.

### Justificativa

A arquitetura de monolito modular foi escolhida porque reduz a complexidade operacional para o escopo acadêmico, facilita a execução local e mantém os domínios separados por packages. A organização modular também permite evolução futura sem concentrar todas as responsabilidades nas mesmas classes.

## Tecnologias

- Java 17;
- Spring Boot 4.1.1;
- Spring Web MVC;
- Spring Data JPA;
- Spring Security;
- Bean Validation;
- Hibernate Validator;
- H2 Database;
- Lombok;
- Maven;
- JUnit 5;
- Mockito;
- AssertJ;
- springdoc-openapi;
- Swagger UI;
- Git e GitHub Projects.

## Estrutura do projeto

Estrutura atual da feature Cliente:

```text
src/main/java/com/example/cambio
├── cliente
│   ├── api
│   │   └── ClienteController.java
│   ├── application
│   │   └── ClienteService.java
│   ├── domain
│   │   └── Cliente.java
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
└── enums
    ├── EstadoCivil.java
    └── Sexo.java
```

Os DTOs `CadastrarClienteRequest` e `ClienteResponse` utilizam `record`. A entidade `Cliente` utiliza Lombok e Builder.

## Como executar

### Pré-requisitos

- Java 17;
- Maven Wrapper incluído no projeto;
- IntelliJ IDEA ou outra IDE Java;
- navegador para acessar o Swagger UI.

### Pela IDE

1. Abra o projeto.
2. Aguarde o Maven carregar as dependências.
3. Execute `CambioApplication`.
4. Aguarde a mensagem de inicialização da aplicação.

### Pelo terminal no Windows

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

A aplicação utiliza, por padrão:

```text
http://localhost:8080
```

### Banco H2

O banco está configurado em memória durante o desenvolvimento. Os dados podem ser reinicializados quando a aplicação é encerrada.

## Swagger e OpenAPI

A documentação interativa utiliza `springdoc-openapi-starter-webmvc-ui`.

Após iniciar a aplicação, acesse:

```text
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/v3/api-docs
```

Dependendo do redirecionamento da biblioteca, a interface também poderá aparecer em:

```text
http://localhost:8080/swagger-ui/index.html
```

## Feature Cliente

A feature Cliente é responsável pelos casos de uso:

- **UC1:** cadastrar cliente;
- **UC2:** consultar cliente por CPF.

### Componentes principais

- `ClienteController`: recebe as requisições HTTP;
- `ClienteService`: aplica as regras de negócio;
- `ClienteRepository`: realiza o acesso aos dados;
- `CadastrarClienteRequest`: contrato de entrada;
- `ClienteResponse`: contrato de saída;
- `Cliente`: entidade JPA;
- `GlobalExceptionHandler`: converte exceções em respostas HTTP.

## Endpoints de Cliente

### Cadastrar cliente

```http
POST /api/clientes
Content-Type: application/json
```

Exemplo de requisição:

```json
{
  "nome": "Anna Teste",
  "cpf": "52998224725",
  "dataNascimento": "1990-05-20",
  "estadoCivil": "SOLTEIRO",
  "sexo": "FEMININO"
}
```

Exemplo de resposta de sucesso:

```json
{
  "id": 1,
  "nome": "Anna Teste",
  "cpf": "52998224725",
  "dataNascimento": "1990-05-20",
  "estadoCivil": "SOLTEIRO",
  "sexo": "FEMININO"
}
```

Respostas previstas:

- `201 Created`: cliente cadastrado;
- `400 Bad Request`: dados inválidos;
- `409 Conflict`: CPF já cadastrado.

### Consultar cliente por CPF

```http
GET /api/clientes/{cpf}
```

Exemplo:

```http
GET /api/clientes/52998224725
```

Respostas previstas:

- `200 OK`: cliente encontrado;
- `404 Not Found`: cliente não encontrado.

## Validações

O cadastro de Cliente valida:

- nome obrigatório;
- CPF obrigatório;
- CPF com exatamente 11 dígitos;
- dígitos verificadores do CPF;
- data de nascimento obrigatória;
- data de nascimento no passado;
- estado civil obrigatório;
- sexo obrigatório.

O CPF deve ser enviado com 11 números, sem pontos ou hífen.

## Tratamento de erros

As exceções são tratadas pelo `GlobalExceptionHandler` e devolvidas em formato padronizado.

Exemplo:

```json
{
  "timestamp": "2026-10-01T14:00:00",
  "status": 409,
  "mensagem": "CPF já cadastrado."
}
```

Erros de Cliente:

| Situação | Status esperado |
|---|---:|
| Dados de cadastro inválidos | 400 |
| Cliente não encontrado | 404 |
| CPF já cadastrado | 409 |

## Testes automatizados

A feature Cliente possui testes unitários com JUnit 5, Mockito e AssertJ.

Cenários implementados:

- cadastro de cliente com sucesso;
- tentativa de cadastro com CPF já cadastrado;
- garantia de que `save()` não é executado no cenário de duplicidade;
- captura e validação da entidade enviada ao Repository com `ArgumentCaptor`.

Para executar:

```powershell
.\mvnw.cmd test
```

## SOLID e Clean Code

### Responsabilidade Única, SRP

- Controller: comunicação HTTP;
- Service: regras de negócio;
- Repository: persistência;
- DTOs: contratos de entrada e saída;
- Entity: representação persistida;
- Exception Handler: tratamento das respostas de erro.

### Injeção de dependência por construtor

O `ClienteService` utiliza uma dependência `final` e `@RequiredArgsConstructor`:

```java
@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
}
```

### Builder

A entidade Cliente é criada de forma legível com Builder:

```java
Cliente cliente = Cliente.builder()
        .nome(request.nome())
        .cpf(request.cpf())
        .dataNascimento(request.dataNascimento())
        .estadoCivil(request.estadoCivil())
        .sexo(request.sexo())
        .build();
```

## Logging

A aplicação gera logs de execução. Arquivos de log não são versionados.

Regras adicionadas ao `.gitignore`:

```gitignore
logs/
*.log
```

## Git e Kanban

### Branch da feature

```text
feat/cliente
```

Fluxo adotado:

```text
feat/cliente → Pull Request → develop → main
```

Comandos principais:

```powershell
git status
git add .
git commit -m "descrição da alteração"
git push origin feat/cliente
```

### Kanban

O projeto utiliza GitHub Projects com os status:

- Backlog;
- Todo;
- In Progress;
- Done.

As tarefas são classificadas pelas features:

- Cliente;
- Câmbio;
- Compra;
- Segurança;
- Geral/Arquitetura.

Cada card deve registrar responsável, Sprint, critérios de aceite e evidência.

## Evidências da Sprint 1

### Swagger

| Cenário | Resultado esperado | Situação |
|---|---:|---|
| Cadastro de cliente válido | 201 Created | ✅ Validado no Swagger |
| CPF duplicado | 409 Conflict | ⏳ Pendente de confirmação |
| Consulta de cliente existente | 200 OK | ⏳ Pendente de confirmação |
| Cliente inexistente | 404 Not Found | ⏳ Pendente de confirmação |
| Dados inválidos | 400 Bad Request | ⏳ Pendente de confirmação |

Evidência confirmada:

```text
POST /api/clientes
Response code: 201
Cliente criado com id 1
```

## Pendências

- confirmar no Swagger o retorno `409` para CPF duplicado;
- confirmar o retorno `200` na consulta por CPF;
- confirmar o retorno `404` para cliente inexistente;
- confirmar o retorno `400` para request inválido;
- integrar a feature Câmbio;
- integrar a feature Compra;
- consolidar a autenticação definitiva;
- complementar o README após a integração das demais features;
- criar o Pull Request de `feat/cliente` para `develop`.
