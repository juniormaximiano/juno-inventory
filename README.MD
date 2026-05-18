<div align="center">

# 📦 Juno Inventory

**API REST para controle de estoque baseado em lotes, com rastreabilidade automática de movimentações.**

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square\&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=flat-square\&logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square\&logo=docker)
![License](https://img.shields.io/badge/Licença-MIT-yellow?style=flat-square)

</div>

---

## 📖 Sobre

O **Juno Inventory** é um sistema backend de controle de estoque desenvolvido como MVP com foco em rastreabilidade de movimentações por lote.

O sistema permite:

* 📦 Cadastro de produtos
* 🗂️ Criação de lotes vinculados a produtos
* 🔄 Movimentações de entrada, saída e ajuste
* 📋 Histórico completo de estoque
* 🔍 Filtros de movimentação por tipo e período

> Toda movimentação é registrada automaticamente, permitindo auditoria e rastreabilidade do estoque sem intervenção manual.

---

## ✅ Funcionalidades

| Status | Funcionalidade                          |
| ------ | --------------------------------------- |
| ✅      | Cadastro de produtos                    |
| ✅      | Criação de lotes vinculados a produtos  |
| ✅      | Controle de quantidade inicial e atual  |
| ✅      | Entrada automática ao criar lote        |
| ✅      | Baixa de estoque                        |
| ✅      | Ajuste manual de estoque                |
| ✅      | Histórico de movimentações              |
| ✅      | Filtro por tipo de movimentação         |
| ✅      | Filtro por período                      |
| ✅      | Combinação de filtros                   |
| ✅      | Validação de produto existente          |
| ✅      | Prevenção de lote duplicado por produto |
| ✅      | Prevenção de estoque negativo           |
| ✅      | DTOs de resposta para movimentações     |
| ✅      | Documentação Swagger/OpenAPI            |
| ✅      | Ambiente MySQL via Docker Compose       |
| ✅      | Seed automática para ambiente local     |
| ✅      | Testes unitários de regras de negócio   |

---

## 🛠️ Tecnologias

| Tecnologia                  | Versão |
| --------------------------- | ------ |
| Java                        | 17     |
| Spring Boot                 | 3.x    |
| Spring Data JPA             | —      |
| Hibernate                   | —      |
| MySQL                       | 8.x    |
| Docker / Docker Compose     | —      |
| Swagger / SpringDoc OpenAPI | —      |
| JUnit 5                     | —      |
| Mockito                     | —      |

---

## 🚀 Como rodar

### Pré-requisitos

* Java 17
* Maven
* Docker
* Docker Compose

---

### 1. Clonar o projeto

```bash
git clone https://github.com/juniormaximiano/juno-inventory.git
cd juno-inventory
```

---

### 2. Configurar variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto baseado no `.env.example`.

Exemplo:

```env
MYSQL_ROOT_PASSWORD=your_password

SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/inventoryDB?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password
```

---

### 3. Subir o MySQL com Docker

```bash
docker compose up -d
```

O banco será disponibilizado na porta `3307`.

---

### 4. Rodar a aplicação Spring Boot

Via Maven:

```bash
./mvnw spring-boot:run
```

Ou execute a aplicação diretamente pela IDE.

---

### 5. Seed automática

O projeto possui um `data.sql` responsável por popular automaticamente o banco com dados de exemplo durante a inicialização da aplicação.

Isso inclui:

* produtos
* lotes
* movimentações de estoque

---

## 🌐 Acesso

| Serviço    | URL                                         |
| ---------- | ------------------------------------------- |
| API        | http://localhost:8080                       |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |

---

## 📡 Endpoints

### Produtos

| Método | Endpoint    | Descrição            |
| ------ | ----------- | -------------------- |
| `POST` | `/produtos` | Cria um novo produto |

### Lotes

| Método | Endpoint        | Descrição                        |
| ------ | --------------- | -------------------------------- |
| `POST` | `/lotes`        | Cria um novo lote                |
| `POST` | `/lotes/saida`  | Registra saída de estoque        |
| `POST` | `/lotes/ajuste` | Realiza ajuste manual de estoque |
| `GET`  | `/lotes`        | Lista todos os lotes             |
| `GET`  | `/lotes/{id}`   | Busca lote por ID                |

### Movimentações

| Método | Endpoint                              | Descrição                                       |
| ------ | ------------------------------------- | ----------------------------------------------- |
| `GET`  | `/lotes/{id}/movimentacoesPorTipo`    | Lista movimentações por tipo                    |
| `GET`  | `/lotes/{id}/movimentacoesPorPeriodo` | Lista movimentações por período e tipo opcional |

---

## 🧪 Testes

O projeto possui testes unitários focados nas principais regras de negócio utilizando:

* JUnit 5
* Mockito

Cobertura atual:

* prevenção de estoque negativo
* saída válida de estoque
* ajuste de estoque
* produto inexistente
* prevenção de lote duplicado

---

## 📌 Regras de negócio

* Lotes só podem ser criados para **produtos existentes**
* **Estoque negativo não é permitido**
* A criação de lote gera automaticamente uma **movimentação de entrada**
* Ajustes de estoque geram movimentações do tipo `AJUSTE`
* O histórico suporta **filtros por tipo e período**
* **Código de lote duplicado** para o mesmo produto não é permitido

---

## 🗂️ Estrutura do projeto

```text
src/main/java/com/juno/inventory/
├── controller/    # Endpoints REST
├── dto/           # DTOs de request/response
├── model/         # Entidades JPA
├── repository/    # Acesso ao banco de dados
└── service/       # Regras de negócio
```

---

## 📄 Licença

Distribuído sob a licença [MIT](LICENSE).

---

<div align="center">
  Feito com ☕ por <a href="https://github.com/juniormaximiano">Junior Maximiano</a>
</div>
