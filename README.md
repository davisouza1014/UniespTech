# Projeto Uniesp Tech: Sistema de Gestão Acadêmica Escalonável

## Situação-Problema
A **Uniesp Tech** herdou um sistema de uma startup que faliu. O código atual é funcional, porém extremamente **frágil**:
* **Dados Voláteis:** Armazena dados apenas em memória (perde tudo ao reiniciar).
* **Sem Qualidade:** Não possui nenhum teste unitário ou de integração.
* **Deploy Artesanal:** O processo é manual (copiar o `.jar` via FTP).
* **Blindness (Cegueira):** Ninguém sabe se o sistema está online ou offline até que um usuário reclame.

---

## O Objetivo
Em **3 semanas**, vocês devem reconstruir a base deste sistema, aplicar persistência real, containerizar a aplicação e criar um fluxo de CI/CD profissional que impeça bugs de chegarem em produção.

---

## Cronograma de Desenvolvimento

### Semana 1: Refatoração, Qualidade e Governança (Plan, Code, Test)
Nesta fase, o foco é "organizar a casa" e garantir que o código seja testável e modular.

* **Desafio Java:**
    * Refatorar o código "macarrônico" original separando-o em camadas: `Controller`, `Service`, `Model` e `Repository`.
    * Implementar validações rigorosas (ex: CPF com 11 dígitos, campos obrigatórios não vazios).
* **Desafio DevOps:**
    * **Maven:** Configurar o `pom.xml` para gerenciar dependências e o ciclo de vida do projeto.
    * **Testes:** Criar os primeiros Testes Unitários com **JUnit 5** para as regras de negócio.
    * **Governança:** Criar um quadro Kanban (GitHub Projects). Proibido o Push direto na `main`; o código só entra via **Pull Request** com Code Review de um colega.

** Entrega:** Repositório organizado, código limpo e suite de testes rodando localmente.

---

### Semana 2: Persistência e Containerização (Build, Release)
Agora o sistema precisa sobreviver ao reinício e ser portável para qualquer ambiente.

* **Desafio Java:**
    * Substituir o armazenamento em `ArrayList` por persistência real.
    * Implementar **H2 Database** (em arquivo) ou **PostgreSQL** via JDBC ou Spring Data JPA.
* **Desafio DevOps:**
    * **Docker:** Criar um `Dockerfile` otimizado para a aplicação (Multi-stage build).
    * **CI (Continuous Integration):** Configurar **GitHub Actions** para que, a cada Push/PR, o sistema execute o Build e os Testes automaticamente.
    * **Artifacts:** O build bem-sucedido deve gerar uma imagem Docker ou um artefato `.jar` versionado no GitHub.

** Entrega:** Pipeline de CI configurado (build verde) e aplicação rodando dentro de um container Docker.

---

### Semana 3: Cloud, Deploy e Monitoramento (Deploy, Operate, Monitor)
Hora de colocar o sistema no mundo real e garantir que ele continue de pé e saudável.

* **Desafio Java:**
    * Implementar um endpoint de **Health Check** (ex: `/health`) que verifica a saúde do app e da conexão com o banco.
    * Implementar logs estruturados (Log4j ou SLF4J) para monitorar tentativas de cadastro ou erros críticos.
* **Desafio DevOps:**
    * **CD (Continuous Deployment):** Configurar o deploy automático para uma nuvem (Render, Railway ou Fly.io).
    * **Operação:** Realizar um "Chaos Test" (simular queda do banco e observar como a aplicação loga o erro).
    * **Documentação:** Finalizar o README com instruções de uso e evidências do monitoramento.

** Entrega:** Link da aplicação rodando em produção com fluxo de entrega contínua ativo.

---

## Semana 4: Prova Prática
Aplicação de uma *Hotfix de Emergência* em tempo real para avaliar o domínio sobre o fluxo DevOps construído.


# UniespTech — Sistema de Gestão Acadêmica

Sistema acadêmico com persistência real em PostgreSQL, containerização Docker, pipeline CI/CD e monitoramento em produção.

---

## 🚀 Link em Produção

```
https://uniesptech-production-038c.up.railway.app/health
```

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** — Linguagem principal
- **Maven** — Gerenciamento de dependências e build
- **PostgreSQL** — Banco de dados relacional
- **JDBC** — Conexão com o banco de dados
- **SLF4J + Logback** — Logs estruturados
- **JUnit 5** — Testes unitários
- **Docker** — Containerização
- **GitHub Actions** — CI/CD Pipeline
- **Railway** — Deploy em nuvem

---

## 📁 Estrutura do Projeto

```
src/main/java/
├── SistemaUniesp.java          # Ponto de entrada
├── controller/
│   └── AlunoController.java    # Camada de controle
├── service/
│   └── AlunoService.java       # Regras de negócio + logs
├── repository/
│   ├── AlunoRepository.java        # Interface do repositório
│   ├── AlunoRepositoryMemoria.java # Implementação em memória
│   └── AlunoRepositoryPostgres.java # Implementação PostgreSQL
├── model/
│   └── Aluno.java              # Modelo de dados
└── infra/
    ├── DatabaseConnection.java  # Conexão JDBC
    └── HealthCheckServer.java   # Servidor HTTP /health
```

---

## ⚙️ Como Rodar Localmente

### Pré-requisitos
- Java 21
- Maven
- PostgreSQL instalado e rodando

### 1. Crie o banco de dados
```sql
CREATE DATABASE uniesp_db;
```

### 2. Configure as variáveis de ambiente
```bash
# Windows PowerShell
$env:DB_URL="jdbc:postgresql://localhost:5432/uniesp_db"
$env:DB_USER="postgres"
$env:DB_PASSWORD="sua_senha"
```

### 3. Compile e rode
```bash
mvn clean package -DskipTests
java -jar target/uniesp-tech.jar
```

### 4. Verifique o Health Check
```
http://localhost:8080/health
```

---

## 🐳 Como Rodar com Docker

```bash
docker-compose up --build
```

O `docker-compose.yml` sobe o PostgreSQL e a aplicação automaticamente.

---

## 🔍 Health Check

O endpoint `/health` verifica a saúde da aplicação e da conexão com o banco:

**Banco online:**
```json
{
  "status": "UP",
  "database": "UP"
}
```

**Banco offline:**
```json
{
  "status": "DOWN",
  "database": "DOWN"
}
```

---

## 📋 Logs Estruturados

Os logs são gerados com SLF4J + Logback e salvos em `logs/uniesp.log`:

```
2026-04-10 19:00:00 [INFO]  service.AlunoService - Tentativa de cadastro - nome: João, cpf: 12345678901
2026-04-10 19:00:00 [INFO]  service.AlunoService - Aluno cadastrado com sucesso - id: 1, nome: João
2026-04-10 19:00:00 [WARN]  service.AlunoService - Cadastro rejeitado - CPF já cadastrado: 12345678901
2026-04-10 19:00:00 [ERROR] infra.HealthCheckServer - Health check FALHOU - banco indisponivel
```

---

## 🧪 Testes

Para rodar os testes unitários:

```bash
mvn test
```

Os testes cobrem:
- Validações de nome (vazio, nulo, caracteres inválidos)
- Validações de CPF (vazio, nulo, letras, tamanho)
- Cadastro com sucesso
- Listagem e deleção

---

## 💥 Chaos Test

Para simular queda do banco e verificar o comportamento do sistema:

1. Altere a variável `DB_PASSWORD` para um valor inválido
2. Acesse `/health` — retornará `{"status": "DOWN", "database": "DOWN"}`
3. Restaure a senha correta
4. Acesse `/health` novamente — retornará `{"status": "UP", "database": "UP"}`

O sistema **não crasha** — continua rodando e monitorando a saúde do banco.

---

## 🔄 CI/CD Pipeline

A cada Push ou Pull Request na branch `main` ou `feat/*`:

1. ✅ GitHub Actions executa o build
2. ✅ Testes unitários rodam automaticamente
3. ✅ Artefato `.jar` é gerado e publicado
4. ✅ Railway faz o deploy automático

---

## 🌍 Variáveis de Ambiente

| Variável | Descrição | Padrão |
|---|---|---|
| `DB_URL` | URL de conexão JDBC | `jdbc:postgresql://localhost:5432/uniesp_db` |
| `DB_USER` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `postgres` |