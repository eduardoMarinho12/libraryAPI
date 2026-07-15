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

## Swagger

Com a aplicacao rodando, acesse:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

No Swagger UI, abra cada grupo de endpoints, clique em `Try it out`, preencha o JSON e clique em `Execute`.
Ele mostra a URL chamada, o status HTTP retornado e o corpo JSON da resposta.

Para demonstrar o CRUD completo no Swagger:

1. Crie um livro em `POST /api/books`.
2. Liste os livros em `GET /api/books`.
3. Atualize o livro em `PUT /api/books/{id}`.
4. Remova o livro em `DELETE /api/books/{id}`.
5. Repita o fluxo para usuarios em `/api/users`.
6. Para emprestimos, crie primeiro um livro e um usuario, depois use `POST /api/loans` e `POST /api/loans/{id}/return`.

Os dados ficam persistidos no PostgreSQL configurado em `application.properties`. Se voce reiniciar a API sem apagar o volume do Docker, os registros continuam no banco.

## Endpoints

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
