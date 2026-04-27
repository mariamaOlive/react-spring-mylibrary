package com.mariama.easylibrary.controller;

import com.mariama.easylibrary.entity.Author;
import com.mariama.easylibrary.service.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final IAuthorService iAuthorService;

    @GetMapping
    public List<Author> getAuthors() {
        return iAuthorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return iAuthorService.getAuthorById(id);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        Author saved = iAuthorService.saveAuthor(author);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iAuthorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
