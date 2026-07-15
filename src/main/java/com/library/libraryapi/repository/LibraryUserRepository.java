package com.library.libraryapi.repository;

import com.library.libraryapi.domain.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
}
