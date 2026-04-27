package com.mariama.easylibrary.service.impl;

import com.mariama.easylibrary.entity.Book;
import com.mariama.easylibrary.exception.ResourceNotFoundException;
import com.mariama.easylibrary.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mariama.easylibrary.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}
