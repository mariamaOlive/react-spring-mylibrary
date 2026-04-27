package com.mariama.easylibrary.repository;

import com.mariama.easylibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
}
