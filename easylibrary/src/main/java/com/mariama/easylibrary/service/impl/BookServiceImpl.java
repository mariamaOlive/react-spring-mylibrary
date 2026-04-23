package com.mariama.easylibrary.service.impl;

import com.mariama.easylibrary.entity.Book;
import com.mariama.easylibrary.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mariama.easylibrary.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
}
