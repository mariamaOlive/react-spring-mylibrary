package com.mariama.easylibrary.controller;

import com.mariama.easylibrary.entity.Book;
import com.mariama.easylibrary.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService iBookService;

    @GetMapping
    public List<Book> getBooks() {
        return iBookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return iBookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = iBookService.saveBook(book);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
