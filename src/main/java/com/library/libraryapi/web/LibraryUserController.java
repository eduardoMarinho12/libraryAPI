package com.library.libraryapi.web;

import com.library.libraryapi.domain.LibraryUser;
import com.library.libraryapi.service.LibraryUserService;
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
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "CRUD de usuarios da biblioteca")
public class LibraryUserController {

    private final LibraryUserService userService;

    public LibraryUserController(LibraryUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuarios")
    @ApiResponse(responseCode = "200", description = "Usuarios retornados com sucesso")
    public List<LibraryUser> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuario pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    public LibraryUser findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "409", description = "E-mail duplicado")
    public ResponseEntity<LibraryUser> create(@Valid @RequestBody LibraryUser user) {
        LibraryUser created = userService.create(user);
        return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados invalidos")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    public LibraryUser update(@PathVariable Long id, @Valid @RequestBody LibraryUser user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuario")
    @ApiResponse(responseCode = "204", description = "Usuario removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
