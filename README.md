# Library API

API REST em Java com Spring Boot e PostgreSQL para gerenciar livros, usuarios e emprestimos de uma biblioteca.

## Requisitos

- Java 21
- PostgreSQL 12+

## Banco de dados

Com Docker, suba o PostgreSQL com:

```bash
docker compose up -d
```

Sem Docker, crie um banco PostgreSQL:

```sql
CREATE DATABASE "libraryAPI";
```

Por padrao a aplicacao usa:

```text
DB_URL=jdbc:postgresql://localhost:5883/libraryAPI
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

Essas variaveis podem ser alteradas no ambiente antes de iniciar a API.

## Executando

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

# Entidades

### Livros

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`

Exemplo:

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "publishedYear": 2008,
  "quantity": 3
}
```

### Usuarios

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Exemplo:

```json
{
  "name": "Maria Silva",
  "email": "maria@email.com",
  "phone": "85999990000"
}
```

### Emprestimos

- `GET /api/loans`
- `GET /api/loans?status=ACTIVE`
- `GET /api/loans/{id}`
- `POST /api/loans`
- `POST /api/loans/{id}/return`
- `DELETE /api/loans/{id}`

Exemplo:

```json
{
  "bookId": 1,
  "userId": 1,
  "dueDate": "2026-07-22"
}
```

Ao criar um emprestimo, a quantidade disponivel do livro diminui. Ao devolver, ela aumenta novamente.
