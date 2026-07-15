package com.library.libraryapi.service;

import com.library.libraryapi.domain.Book;
import com.library.libraryapi.domain.LibraryUser;
import com.library.libraryapi.domain.Loan;
import com.library.libraryapi.domain.LoanStatus;
import com.library.libraryapi.repository.LoanRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    private static final int DEFAULT_LOAN_DAYS = 14;

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final LibraryUserService userService;

    public LoanService(LoanRepository loanRepository, BookService bookService, LibraryUserService userService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public List<Loan> findAll(LoanStatus status) {
        if (status == null) {
            return loanRepository.findAll();
        }
        return loanRepository.findByStatus(status);
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Emprestimo nao encontrado"));
    }

    @Transactional
    public Loan create(Long bookId, Long userId, LocalDate dueDate) {
        Book book = bookService.findById(bookId);
        LibraryUser user = userService.findById(userId);
        book.borrowCopy();

        LocalDate today = LocalDate.now();
        LocalDate finalDueDate = dueDate == null ? today.plusDays(DEFAULT_LOAN_DAYS) : dueDate;
        return loanRepository.save(new Loan(book, user, today, finalDueDate));
    }

    @Transactional
    public Loan returnLoan(Long id) {
        Loan loan = findById(id);
        loan.close(LocalDate.now());
        loan.getBook().returnCopy();
        return loan;
    }

    public void delete(Long id) {
        Loan loan = findById(id);
        if (loan.isActive()) {
            loan.getBook().returnCopy();
        }
        loanRepository.delete(loan);
    }
}
