package com.library.libraryapi.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateLoanRequest(
        @NotNull Long bookId,
        @NotNull Long userId,
        @FutureOrPresent LocalDate dueDate
) {
}
