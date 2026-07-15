package com.library.libraryapi.repository;

import com.library.libraryapi.domain.Loan;
import com.library.libraryapi.domain.LoanStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus status);
}
