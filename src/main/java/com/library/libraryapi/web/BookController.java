package com.library.libraryapi.web;

import com.library.libraryapi.domain.Book;
import com.library.libraryapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Livros", description = "CRUD de livros cadastrados na biblioteca")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os livros")
    @ApiResponse(responseCode = "200", description = "Livros retornados com sucesso")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um livro pelo ID")
    @ApiResponse(responseCode = "200", description = "Livro encontrado")
    @ApiResponse(responseCode = "404", description = "Livro nao encontrado")
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        Book created = bookService.create(book);
        return ResponseEntity.created(URI.create("/api/books/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um livro existente")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "404", description = "Livro nao encontrado")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um livro")
    @ApiResponse(responseCode = "204", description = "Livro removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Livro nao encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
