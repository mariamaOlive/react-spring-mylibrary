package com.mariama.easylibrary.unittesting.service;

import com.mariama.easylibrary.entity.Author;
import com.mariama.easylibrary.entity.Book;
import com.mariama.easylibrary.repository.BookRepository;
import com.mariama.easylibrary.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    void getAllBooks_shouldReturnAllBooks(){
        //Arrange
        Author rowling  = new Author(1L, "J.K. Rowling", "British", "rowling.jpg");
        Author riordan  = new Author(2L, "Rick Riordan",  "American", "riordan.jpg");

        Book book1 = new Book(1L, "Harry Potter and the Sorcerer's Stone",
                "978-0439708180", 1997, "harry_potter_1.jpg", rowling);
        Book book2 = new Book(2L, "Percy Jackson and the Lightning Thief",
                "978-0786838653", 2005, "percy_jackson_1.jpg", riordan);
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        //Act
        List<Book> books = bookServiceImpl.getBooks();

        //Assert
        assertEquals(2, books.size());
        assertEquals("J.K. Rowling", books.get(0).getAuthor().getName());
        assertEquals("Rick Riordan", books.get(1).getAuthor().getName());
        verify(bookRepository, times(1)).findAll();
    }





}
