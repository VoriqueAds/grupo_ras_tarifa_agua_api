# API — Tabela Tarifária de Água (GrupoRAS)

API REST em **Java + Spring Boot + PostgreSQL** para **cadastrar tabelas tarifárias parametrizáveis** (faixas/valores no banco) e **calcular tarifa por faixas progressivas**, retornando total e detalhamento por faixa.

## Stack

* Java 17
* Spring Boot 4.0.2
* PostgreSQL
* Flyway
* Swagger (springdoc-openapi)

## Como rodar (sem Docker)

### 1\) PostgreSQL

Crie o banco:
sql
CREATE DATABASE tarifa\_agua;

Configure em src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/tarifa\_agua
spring.datasource.username=postgres
spring.datasource.password=postgres



O Flyway aplica as migrations automaticamente na primeira execução (src/main/resources/db/migration).

2. Rodar a API
   mvn spring-boot:run



Swagger: http://localhost:8080/swagger-ui

Endpoints
Criar tabela tarifária completa

POST /api/tabelas-tarifarias

Exemplo:

{
"nome": "Tabela 2026",
"vigenciaInicio": "2026-01-01",
"vigenciaFim": null,
"categorias": \[
{
"categoria": "INDUSTRIAL",
"faixas": \[
{ "inicio": 0,  "fim": 10,    "valorUnitario": 1.00 },
{ "inicio": 11, "fim": 20,    "valorUnitario": 2.00 },
{ "inicio": 21, "fim": 30,    "valorUnitario": 3.00 },
{ "inicio": 31, "fim": 99999, "valorUnitario": 4.00 }
]
}
]
}



Validações:

faixas contínuas (sem buracos) e sem sobreposição

inicio < fim

primeira faixa inicia em 0

última faixa cobre consumos altos (ex.: 99999)

Listar tabelas

GET /api/tabelas-tarifarias

Excluir tabela

DELETE /api/tabelas-tarifarias/{id}
Implementação: soft delete (active=false).

Calcular tarifa

POST /api/calculos

Request:

{ "categoria": "INDUSTRIAL", "consumo": 18 }



Response:

{
"categoria": "INDUSTRIAL",
"consumoTotal": 18,
"valorTotal": 26.00,
"detalhamento": \[
{ "faixa": { "inicio": 0, "fim": 10 }, "m3Cobrados": 10, "valorUnitario": 1.00, "subtotal": 10.00 },
{ "faixa": { "inicio": 11, "fim": 20 }, "m3Cobrados": 8,  "valorUnitario": 2.00, "subtotal": 16.00 }
]
}

Migrations

V1\_\_init.sql: estrutura do banco

V2\_\_seed.sql: seed opcional (exemplo)

