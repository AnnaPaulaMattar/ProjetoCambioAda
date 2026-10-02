# ADR 001 - Adoção de Monolito Modular

- **Status:** Aceito
- **Decisão:** Organizar a API de Câmbio como um monolito modular
- **Responsáveis:** Equipe do projeto API de Câmbio
- **Escopo:** Cliente, Câmbio, Compra, Segurança e componentes compartilhados

## 1. Contexto

O projeto consiste em uma API REST desenvolvida com Java e Spring Boot para:

- cadastrar e consultar clientes;
- consultar cotações de dólar e euro;
- registrar ordens de compra de moeda estrangeira;
- consultar compras por identificador e por CPF;
- proteger os endpoints da aplicação;
- padronizar validações, exceções, testes e documentação.

O sistema possui diferentes áreas de responsabilidade, com regras e componentes próprios:

- **Cliente:** cadastro, consulta por CPF e validações cadastrais;
- **Câmbio:** consulta de cotação e integração com a AwesomeAPI;
- **Compra:** registro, cálculo, persistência e consulta de ordens de compra;
- **Segurança:** autenticação e proteção dos endpoints;
- **Compartilhado:** tratamento de erros e recursos utilizados por mais de um módulo.

A equipe precisava escolher uma arquitetura que preservasse a separação entre esses domínios, mas que também fosse simples de desenvolver, testar, executar e apresentar dentro do escopo acadêmico.

## 2. Problema

Uma aplicação sem separação modular poderia concentrar muitas responsabilidades nos mesmos packages e classes, aumentando o acoplamento e dificultando:

- a divisão do trabalho entre os integrantes;
- a localização das regras de cada domínio;
- a execução de testes isolados;
- a revisão de código;
- a evolução futura da aplicação.

Por outro lado, a adoção de microsserviços acrescentaria complexidade operacional que não é necessária para o escopo atual, como múltiplas aplicações, comunicação distribuída, implantação independente e maior esforço de observabilidade.

## 3. Alternativas consideradas

### 3.1 Monolito sem divisão modular

Uma única aplicação com organização predominantemente por tipo técnico, por exemplo, todos os Controllers em um package e todos os Services em outro.

**Vantagens:**

- estrutura inicial simples;
- pequena quantidade de packages.

**Desvantagens:**

- menor visibilidade dos limites de cada domínio;
- risco de mistura entre regras de Cliente, Câmbio e Compra;
- maior dificuldade para dividir responsabilidades entre os integrantes;
- tendência de aumento do acoplamento conforme o projeto cresce.

### 3.2 Microsserviços

Separação de Cliente, Câmbio, Compra e Segurança em aplicações independentes.

**Vantagens:**

- implantação e evolução independentes;
- isolamento técnico entre os serviços;
- possibilidade de escalabilidade específica por domínio.

**Desvantagens:**

- complexidade desproporcional ao escopo do projeto;
- necessidade de comunicação entre serviços;
- maior esforço de configuração, execução, testes e demonstração;
- mais pontos de falha e maior complexidade operacional.

### 3.3 Monolito modular

Uma única aplicação Spring Boot, organizada em módulos internos de negócio.

**Vantagens:**

- execução e implantação simples;
- separação explícita entre os domínios;
- menor complexidade operacional;
- facilidade para testes e desenvolvimento local;
- divisão clara do trabalho entre os integrantes;
- possibilidade de evolução futura dos módulos.

**Desvantagens:**

- todos os módulos continuam sendo implantados em conjunto;
- uma falha grave pode afetar toda a aplicação;
- a equipe precisa controlar dependências entre módulos para evitar acoplamento indevido.

## 4. Decisão

A equipe decidiu adotar uma arquitetura de **monolito modular**.

A solução será executada como uma única aplicação Spring Boot, mas o código será organizado por módulos de domínio e responsabilidade.

Estrutura lógica prevista:

```text
src/main/java/com/example/cambio
├── cliente
│   ├── api
│   ├── application
│   ├── domain
│   ├── dto
│   ├── exception
│   └── infrastructure
├── cambio
│   ├── api
│   ├── application
│   ├── domain
│   └── infrastructure
├── compra
│   ├── api
│   ├── application
│   ├── domain
│   └── infrastructure
├── security
├── shared
└── enums
```

Cada módulo deve manter suas responsabilidades internas:

- **API/Controller:** receber requisições HTTP e devolver respostas;
- **Application/Service:** executar casos de uso e regras de negócio;
- **Domain:** representar conceitos e regras do domínio;
- **DTO:** definir contratos de entrada e saída;
- **Infrastructure/Repository ou Adapter:** acessar banco de dados e serviços externos;
- **Exception:** representar e tratar condições de erro.

## 5. Regras arquiteturais resultantes

A decisão implica as seguintes regras:

1. Uma entidade JPA não deve ser exposta diretamente pelos Controllers.
2. Os Controllers devem delegar regras de negócio aos Services.
3. Os Services não devem instanciar diretamente seus Repositories ou Adapters.
4. As dependências obrigatórias devem ser fornecidas preferencialmente por construtor.
5. A integração com a API externa deve depender da abstração `CotacaoProvider`, e não diretamente da implementação `AwesomeApiCotacaoAdapter`.
6. Exceções de negócio devem ser convertidas em respostas HTTP padronizadas.
7. Os módulos devem evitar dependências circulares.
8. Segredos, chaves e arquivos de log não devem ser versionados.
9. Cada integrante deve desenvolver em sua branch de feature e integrar as alterações por Pull Request.
10. Decisões arquiteturais relevantes devem ser registradas em novos ADRs.

## 6. Consequências positivas

- A aplicação pode ser iniciada e demonstrada como uma única unidade.
- Os domínios permanecem identificáveis na estrutura do código.
- A equipe consegue dividir o desenvolvimento por feature.
- A injeção de dependência favorece testes com mocks.
- A separação entre Controller, Service e Repository reforça a responsabilidade única.
- DTOs protegem o contrato HTTP contra exposição direta das entidades.
- A abstração `CotacaoProvider` reduz o acoplamento do domínio com a AwesomeAPI.
- A estrutura facilita revisão por Pull Request e rastreabilidade no Kanban.

## 7. Consequências negativas e riscos

- Todos os módulos compartilham o mesmo processo de execução.
- A implantação continua sendo conjunta.
- Alterações em componentes compartilhados podem afetar vários módulos.
- Sem revisão contínua, a separação modular pode se degradar.
- O banco e a configuração da aplicação continuam centralizados.

## 8. Medidas de controle

Para preservar a arquitetura escolhida, a equipe deve:

- revisar dependências entre os módulos nos Pull Requests;
- manter os casos de uso dentro dos Services correspondentes;
- evitar acesso direto de Controller ao Repository;
- executar os testes antes de integrar uma feature à `develop`;
- atualizar a documentação quando a estrutura mudar;
- criar novo ADR quando uma decisão arquitetural relevante substituir esta decisão.

## 9. Relação com SOLID

A decisão apoia principalmente:

### Princípio da Responsabilidade Única

Controller, Service, Repository, DTO e Exception Handler possuem responsabilidades distintas.

### Princípio da Inversão de Dependência

O módulo de Câmbio deve depender da abstração `CotacaoProvider`. A implementação concreta da integração externa é fornecida pela infraestrutura.

### Testabilidade

A injeção de dependência por construtor permite substituir dependências reais por mocks durante os testes unitários.

## 10. Critérios de verificação

A decisão será considerada aplicada quando:

- os módulos Cliente, Câmbio, Compra e Segurança estiverem identificáveis na estrutura;
- os Controllers utilizarem DTOs;
- as regras de negócio estiverem concentradas nos Services;
- os Repositories e Adapters forem injetados;
- a integração externa estiver protegida por uma abstração;
- as exceções forem tratadas de forma padronizada;
- os testes puderem isolar os Services com mocks.

## 11. Revisão futura

Esta decisão deverá ser reavaliada caso ocorram mudanças significativas, como:

- necessidade de implantação independente de um domínio;
- requisitos de escalabilidade muito diferentes entre os módulos;
- crescimento da equipe e necessidade de autonomia operacional;
- exigência de isolamento de falhas ou dados;
- substituição da arquitetura por serviços independentes.

Se a decisão for substituída, um novo ADR deverá ser criado com referência a este documento e status **Superseded/Substituído**.
