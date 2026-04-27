package com.mariama.easylibrary.service;

import com.mariama.easylibrary.entity.Author;

import java.util.List;

public interface IAuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(Long id);

    Author saveAuthor(Author author);
}
