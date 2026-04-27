package com.mariama.easylibrary.service;

import com.mariama.easylibrary.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getBooks();

    Optional<Book> getBookById(Long id);

    Book saveBook(Book book);

    void deleteBook(Long id);
}