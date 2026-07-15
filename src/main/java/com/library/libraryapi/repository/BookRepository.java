package com.library.libraryapi.repository;

import com.library.libraryapi.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
