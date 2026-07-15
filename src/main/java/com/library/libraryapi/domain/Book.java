package com.library.libraryapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String author;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer publishedYear;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    protected Book() {
    }

    public Book(String title, String author, Integer publishedYear, Integer quantity) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean hasAvailableCopy() {
        return quantity != null && quantity > 0;
    }

    public void borrowCopy() {
        if (!hasAvailableCopy()) {
            throw new IllegalStateException("Livro indisponivel para emprestimo");
        }
        quantity--;
    }

    public void returnCopy() {
        quantity++;
    }
}
