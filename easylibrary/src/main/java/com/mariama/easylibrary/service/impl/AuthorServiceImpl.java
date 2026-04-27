package com.mariama.easylibrary.service.impl;

import com.mariama.easylibrary.entity.Author;
import com.mariama.easylibrary.exception.ResourceNotFoundException;
import com.mariama.easylibrary.repository.AuthorRepository;
import com.mariama.easylibrary.service.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements IAuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Override
    public Author saveAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }



}
