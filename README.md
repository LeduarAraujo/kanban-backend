# 📌 Kanban Backend (Desafio Técnico)

API em Java 17 / Spring Boot 3 para gerenciar Projetos, Responsáveis e um quadro **Kanban** com regras de negócio, indicadores e documentação **OpenAPI**.

# 📝 Visão Geral
Este projeto foi desenvolvido como parte de um **desafio técnico backend**. O objetivo é criar uma **API para gerenciamento de projetos, responsáveis e indicadores em um quadro Kanban**, implementando regras de negócio específicas e expondo uma documentação clara.

# ▶️ Como rodar (Docker)

```bash
# 1) Build + subir banco e app
docker compose up --build
# App em http://localhost:8080
# Swagger em http://localhost:8080/swagger-ui.html
```

### Como rodar local (sem Docker)
- Requer: Java 17, Maven, PostgreSQL rodando local com DB/credenciais conforme `application.yml`.
```bash
mvn clean install
mvn spring-boot:run
```

### Como gerar as targets local
- Os comandos abaixo, são usados quando apenas o docker está instalado local. 
    Ele irá rodar em uma instancia maven.

```bash
# 1) No Linux / Mac
docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```
```bash
# 2) No Windows ( PowerShell )
docker run --rm -v "${PWD}:/app" -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```
```bash
# 3) No Windows ( CMD ) 
docker run --rm -v "%cd%:/app" -w /app maven:3.9.6-eclipse-temurin-17 mvn clean install
```

# 📄 Endpoints principais
- `GET /api/projetos` – lista
- `POST /api/projetos` – cria (usa ProjetoDTO)
- `PUT /api/projetos/{id}` – atualiza
- `DELETE /api/projetos/{id}` – remove
- `GET /api/projetos/status/{status}` – lista por status (kanban)
- `POST /api/projetos/{id}/transicoes` – solicita transição de status
- `GET /api/responsaveis` – CRUD de responsáveis
- `GET /api/indicadores/atraso-medio-por-status`
- `GET /api/indicadores/quantidade-por-status`

# 🔄 Regras (resumo)
- **Status** calculado automaticamente com base nas datas:
  - A_INICIAR, EM_ANDAMENTO, ATRASADO, CONCLUIDO.
- **Transições** respeitam a tabela do desafio; quando inconsistentes, retornam 422 com dica.
- **Cálculos**
  - Percentual de tempo restante
  - Dias de atraso

# ✅ Testes
```bash
mvn test
```
JaCoCo configurado para gerar relatório em `target/site/jacoco/index.html`.

# 📂 Estrutura
- `domain/` entidades e enum
- `repository/` JPA
- `service/` regras de negócio, transições, indicadores
- `api/` controllers, DTOs, mappers, exceções

# 🛠️ Tecnologias Utilizadas
- `Java 17` Linguagem adotada para obter mais compatibilidade
- `Spring Boot 3`
- `Swagger/OpenApi`
- `Git`
- `Maven`
- `Docker`
- `JUnit`

# 🚀 Próximos passos / Diferenciais
- GraphQL (schema para Projetos/Responsáveis)
- UI Kanban (React com drag-and-drop)
- Observabilidade (Actuator + Prometheus/Grafana)
- Testcontainers para testes de integração
- CI/CD com GitHub Actions

# 📌 Observações

Este projeto foi desenvolvido como desafio técnico para avaliação de habilidades em desenvolvimento Java Backend.
Segue boas práticas, incluindo Clean Code, tratamento de exceções, paginação em listagens, e logs padronizados.


```
## Coleção (Postman/Insomnia)
Você pode importar os endpoints a partir do Swagger em `/api-docs`.
```
