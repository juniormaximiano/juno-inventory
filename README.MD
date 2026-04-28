# 📦 Juno Inventory

API backend para controle de estoque baseada em lotes, com foco em rastreabilidade de movimentações.

---
##             ⚠️ Status  ⚠️️

🚧 Em desenvolvimento (MVP em evolução) 🚧

---

## 🚀 Sobre o projeto

O **Juno Inventory** é um MVP de um sistema WMS (Warehouse Management System), desenvolvido com foco em lógica de negócio e organização de backend.

O sistema permite controlar o estoque através de **lotes**, registrando automaticamente todas as movimentações realizadas.

---

## ✅ Funcionalidades implementadas

* Cadastro de produtos
* Registro de entrada de lote
* Controle de estoque por lote
* Definição automática de quantidade inicial e atual
* Validação de produto existente
* Prevenção de duplicidade de lote por produto
* Geração automática de movimentação de entrada

---

## 🛠️ Tecnologias utilizadas

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* Docker

---

## ⚙️ Como rodar o projeto

### 1. Build do projeto

```bash
./mvnw clean package
```

### 2. Subir o ambiente com Docker

```bash
docker compose up -d --build
```

### 3. Acessar a aplicação

http://localhost:8080

Swagger:
http://localhost:8080/swagger-ui/index.html

---

## 📌 Próximas implementações

* Baixa de estoque (saída de lote)
* Regras de consumo (FIFO / FEFO)
* Consulta de histórico de movimentações
* Melhorias de validação e tratamento de erro
* Estruturação de autenticação

---

## 🧠 Objetivo

Este projeto está sendo desenvolvido como um **MVP em evolução**, com foco em aprendizado prático de backend.

A proposta é construir gradualmente um sistema de controle de estoque baseado em lotes, aproximando-se de cenários reais de mercado.

O projeto ainda está **incompleto**, e novas funcionalidades estão sendo adicionadas conforme a evolução do desenvolvimento.

O foco principal é:

* consolidar lógica de negócio
* melhorar modelagem de dados
* aplicar boas práticas de backend

---
