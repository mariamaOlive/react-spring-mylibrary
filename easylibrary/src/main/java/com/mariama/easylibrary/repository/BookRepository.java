package com.mariama.easylibrary.repository;

import com.mariama.easylibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
}
