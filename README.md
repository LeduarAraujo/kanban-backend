# Kanban Backend (Desafio Técnico)

API em Java 17 / Spring Boot 3 para gerenciar Projetos, Responsáveis e um quadro **Kanban** com regras de negócio, indicadores e documentação **OpenAPI**.

## Como rodar (Docker)

```bash
# 1) Build + subir banco e app
docker compose up --build
# App em http://localhost:8080
# Swagger em http://localhost:8080/swagger-ui.html
```

## Como rodar local (sem Docker)
- Requer: Java 17, Maven, PostgreSQL rodando local com DB/credenciais conforme `application.yml`.
```bash
mvn spring-boot:run
```

## Como gerar as targets local
```bash
docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```

## Endpoints principais
- `GET /api/projetos` – lista
- `POST /api/projetos` – cria (usa ProjetoDTO)
- `PUT /api/projetos/{id}` – atualiza
- `DELETE /api/projetos/{id}` – remove
- `GET /api/projetos/status/{status}` – lista por status (kanban)
- `POST /api/projetos/{id}/transicoes` – solicita transição de status
- `GET /api/responsaveis` – CRUD de responsáveis
- `GET /api/indicadores/atraso-medio-por-status`
- `GET /api/indicadores/quantidade-por-status`

## Regras (resumo)
- **Status** calculado automaticamente com base nas datas:
  - A_INICIAR, EM_ANDAMENTO, ATRASADO, CONCLUIDO.
- **Transições** respeitam a tabela do desafio; quando inconsistentes, retornam 422 com dica.
- **Cálculos**
  - Percentual de tempo restante
  - Dias de atraso

## Testes
```bash
mvn test
```
JaCoCo configurado para gerar relatório em `target/site/jacoco/index.html`.

## Estrutura
- `domain/` entidades e enum
- `repository/` JPA
- `service/` regras de negócio, transições, indicadores
- `api/` controllers, DTOs, mappers, exceções

## Próximos passos / Diferenciais
- GraphQL (schema para Projetos/Responsáveis)
- UI Kanban (React com drag-and-drop)
- Observabilidade (Actuator + Prometheus/Grafana)
- Testcontainers para testes de integração
- CI/CD com GitHub Actions
```

## Coleção (Postman/Insomnia)
Você pode importar os endpoints a partir do Swagger em `/api-docs`.
