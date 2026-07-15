package com.library.libraryapi.web;

import com.library.libraryapi.domain.Loan;
import com.library.libraryapi.domain.LoanStatus;
import com.library.libraryapi.service.LoanService;
import com.library.libraryapi.web.dto.CreateLoanRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Emprestimos", description = "Controle de emprestimos e devolucoes")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    @Operation(summary = "Lista emprestimos", description = "Permite filtrar por status ACTIVE ou RETURNED.")
    @ApiResponse(responseCode = "200", description = "Emprestimos retornados com sucesso")
    public List<Loan> findAll(@RequestParam(required = false) LoanStatus status) {
        return loanService.findAll(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um emprestimo pelo ID")
    @ApiResponse(responseCode = "200", description = "Emprestimo encontrado")
    @ApiResponse(responseCode = "404", description = "Emprestimo nao encontrado")
    public Loan findById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria um emprestimo", description = "Diminui a quantidade disponivel do livro emprestado.")
    @ApiResponse(responseCode = "201", description = "Emprestimo criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados invalidos ou livro indisponivel")
    @ApiResponse(responseCode = "404", description = "Livro ou usuario nao encontrado")
    public ResponseEntity<Loan> create(@Valid @RequestBody CreateLoanRequest request) {
        Loan created = loanService.create(request.bookId(), request.userId(), request.dueDate());
        return ResponseEntity.created(URI.create("/api/loans/" + created.getId())).body(created);
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Registra a devolucao de um emprestimo", description = "Marca o emprestimo como RETURNED e devolve uma unidade ao estoque do livro.")
    @ApiResponse(responseCode = "200", description = "Emprestimo devolvido com sucesso")
    @ApiResponse(responseCode = "400", description = "Emprestimo ja devolvido")
    @ApiResponse(responseCode = "404", description = "Emprestimo nao encontrado")
    public Loan returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um emprestimo")
    @ApiResponse(responseCode = "204", description = "Emprestimo removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Emprestimo nao encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
