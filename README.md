# 📌 Kanban Backend – Desafio Técnico

API desenvolvida em **Java 17 / Spring Boot 3** para gerenciamento de **Projetos, Responsáveis e quadro Kanban**, com regras de negócio, indicadores e documentação **OpenAPI/Swagger**.

---

## 📖 Sumário
- [Visão Geral](#-visão-geral)
- [Como Executar](#-como-executar)
    - [Executar com Docker](#executar-com-docker)
    - [Executar Localmente (sem Docker)](#executar-localmente-sem-docker)
    - [Gerar Targets via Docker](#gerar-targets-via-docker)
- [Principais Endpoints](#-principais-endpoints)
- [Regras de Negócio](#-regras-de-negócio)
- [Testes](#-testes)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Próximos Passos](#-próximos-passos)
- [Observações](#-observações)
- [Coleção Postman/Insomnia](#-coleção-postmaninsomnia)

---

## 📝 Visão Geral
Este projeto foi desenvolvido como parte de um **desafio técnico backend** com os seguintes objetivos:
- Criar uma **API REST** para  gerenciar Projetos, Responsáveis e Resumos através de um quadro Kanban e indicadores.
- Implementar **quadro Kanban** com regras de transição entre status.
- Expor indicadores e documentação **OpenAPI**.
- Garantir boas práticas de desenvolvimento, incluindo **Clean Code, logs padronizados, paginação e tratamento de exceções**.

---

## ▶️ Como Executar

### Executar com Docker
```bash
# Build + subir banco e aplicação
docker compose up --build
```

### 🚀 Documentação / Contratos
- Swagger UI: `http://localhost:8080/swagger-ui`
- API Docs: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

### Executar Localmente (sem Docker)
Requisitos: **Java 17**, **Maven**, **H2** (configurar credenciais no `application.yml`).
```bash
mvn clean install
mvn spring-boot:run
```

### Gerar Targets via Docker
Caso você possua apenas o Docker instalado:

**Linux/Mac**
```bash
docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```

**Windows (PowerShell)**
```bash
docker run --rm -v "${PWD}:/app" -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```

**Windows (CMD)**
```bash
docker run --rm -v "%cd%:/app" -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```

---

## 📄 Principais Endpoints
- `GET /api/projetos` – Lista projetos
- `POST /api/projetos` – Cria um projeto
- `PUT /api/projetos/{id}` – Atualiza um projeto
- `DELETE /api/projetos/{id}` – Remove um projeto
- `GET /api/projetos/status/{status}` – Lista por status (Kanban)
- `POST /api/projetos/{id}/transicoes` – Solicita transição de status
- `GET /api/responsaveis` – CRUD de responsáveis
- `GET /api/indicadores/atraso-medio-por-status`
- `GET /api/indicadores/quantidade-por-status`

---

## 🔄 Regras de Negócio
- **Status calculado automaticamente**:
    - A_INICIAR, EM_ANDAMENTO, ATRASADO, CONCLUIDO.
- **Transições** seguem regras pré-definidas. Erros retornam `422` com sugestão.
- **Cálculos disponíveis**:
    - Percentual de tempo restante.
    - Dias de atraso.

---

## ✅ Testes
Executar testes unitários:
```bash
mvn test
```
Relatório de cobertura disponível em:  
`target/site/jacoco/index.html`

---

## 📂 Estrutura do Projeto

```
src/main/java/com/facilit/kanban_backend/
├── controller/     # Controllers REST e Handdlers.
├── config/         # Configurações do projeto e de acesso web.
├── domain/         # Entidades e enums.
    ├── entity/     # Entidades da base de dados.
    ├── enums/      # Constantes predefinidas.
├── exception/      # Controle de exceções de negócio.
├── mapper/         # Mapeamento dos DTOs para os Representations.
├── repository/     # Repositórios JPA.
├── service/        # Regras de negócio, transições e indicadores.
├── utils/          # Utilitários.
├── dto/            # Objetos de transferencias de dados.
├── security/       # Configurações de segurança.

src/test/java/
├── integration/     # Testes de integração
├── unit/           # Testes unitários
└── fixtures/       # Dados de teste
```

## 🛠️ Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Swagger/OpenAPI
- Docker
- Maven
- Git
- JUnit (com JaCoCo para cobertura)

---

## 🚀 Próximos Passos
- Suporte a **GraphQL** (Projetos/Responsáveis)
- Interface **Kanban (React com drag-and-drop)**
- Observabilidade com **Actuator + Prometheus/Grafana**
- Testes de integração com **Testcontainers**
- CI/CD utilizando **GitHub Actions**

---

## 📌 Observações
Este projeto foi desenvolvido como **desafio técnico backend** para avaliação de habilidades em desenvolvimento Java.  
Foca em **boas práticas de arquitetura, organização e documentação**.

---

## 📦 Coleção Postman/Insomnia
Os endpoints podem ser importados diretamente do Swagger:  
`/api-docs`
