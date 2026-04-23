package com.mariama.easylibrary.controller;

import com.mariama.easylibrary.entity.Book;
import com.mariama.easylibrary.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService iBookService;

    @GetMapping
    public List<Book> getBooks(){
        return iBookService.getBooks();
    }
}
