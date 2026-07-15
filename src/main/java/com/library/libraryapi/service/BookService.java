package com.library.libraryapi.service;

import com.library.libraryapi.domain.Book;
import com.library.libraryapi.repository.BookRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro nao encontrado"));
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book request) {
        Book book = findById(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishedYear(request.getPublishedYear());
        book.setQuantity(request.getQuantity());
        return book;
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Livro nao encontrado");
        }
        bookRepository.deleteById(id);
    }
}
