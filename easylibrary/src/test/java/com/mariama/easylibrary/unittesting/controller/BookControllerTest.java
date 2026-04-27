package com.mariama.easylibrary.unittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariama.easylibrary.controller.BookController;
import com.mariama.easylibrary.entity.Author;
import com.mariama.easylibrary.entity.Book;
import com.mariama.easylibrary.exception.ResourceNotFoundException;
import com.mariama.easylibrary.service.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

        @Mock
        private IBookService iBookService;

        @InjectMocks
        private BookController bookController;

        private MockMvc mockMvc;
        private ObjectMapper objectMapper;

        private Author rowling;
        private Book book1;
        private Book book2;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
                objectMapper = new ObjectMapper();

                rowling = new Author(1L, "J.K. Rowling", "British", "rowling.jpg");
                Author riordan = new Author(2L, "Rick Riordan", "American", "riordan.jpg");

                book1 = new Book(1L, "Harry Potter and the Sorcerer's Stone",
                                "978-0439708180", 1997, "harry_potter_1.jpg", rowling);
                book2 = new Book(2L, "Percy Jackson and the Lightning Thief",
                                "978-0786838653", 2005, "percy_jackson_1.jpg", riordan);
        }

        // -------------- Test 1 --------------
        @Test
        void getBooks_shouldReturn200_withListOfBooks() throws Exception {
                // Arrange
                when(iBookService.getBooks()).thenReturn(List.of(book1, book2));

                // Act & Assert
                mockMvc.perform(get("/api/v1/books"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].title").value("Harry Potter and the Sorcerer's Stone"))
                                .andExpect(jsonPath("$[1].title").value("Percy Jackson and the Lightning Thief"));

                verify(iBookService, times(1)).getBooks();
        }

        // -------------- Test 2 --------------
        @Test
        void getBooks_shouldReturn200_withEmptyList() throws Exception {
                // Arrange
                when(iBookService.getBooks()).thenReturn(List.of());

                // Act & Assert
                mockMvc.perform(get("/api/v1/books"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(0));

                verify(iBookService, times(1)).getBooks();
        }

        // -------------- Test 3 --------------
        @Test
        void getBookById_shouldReturn200_whenBookExists() throws Exception {
                // Arrange
                when(iBookService.getBookById(1L)).thenReturn(book1);

                // Act & Assert
                mockMvc.perform(get("/api/v1/books/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Harry Potter and the Sorcerer's Stone"))
                                .andExpect(jsonPath("$.isbn").value("978-0439708180"));

                verify(iBookService, times(1)).getBookById(1L);
        }

        // -------------- Test 4 --------------
        @Test
        void getBookById_shouldReturn404_whenBookNotFound() throws Exception {
                // Arrange
                when(iBookService.getBookById(99L))
                                .thenThrow(new ResourceNotFoundException("Book not found with id: 99"));
                // Act & Assert
                mockMvc.perform(get("/api/v1/books/99"))
                                .andExpect(status().isNotFound());

                verify(iBookService, times(1)).getBookById(99L);
        }

        // -------------- Test 5 --------------
        @Test
        void createBook_shouldReturn200_withSavedBook() throws Exception {
                // Arrange
                Book newBook = new Book(null, "Harry Potter and the Sorcerer's Stone",
                                "978-0439708180", 1997, "harry_potter_1.jpg", rowling);
                when(iBookService.saveBook(any(Book.class))).thenReturn(book1);

                // Act & Assert
                mockMvc.perform(post("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newBook)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Harry Potter and the Sorcerer's Stone"));

                verify(iBookService, times(1)).saveBook(any(Book.class));
        }

        // -------------- Test 6 --------------
        @Test
        void deleteBook_shouldReturn204_whenBookExists() throws Exception {
                // Arrange
                doNothing().when(iBookService).deleteBook(1L);

                // Act & Assert
                mockMvc.perform(delete("/api/v1/books/1"))
                                .andExpect(status().isNoContent());

                verify(iBookService, times(1)).deleteBook(1L);
        }

        // -------------- Test 7 --------------
        @Test
        void deleteBook_shouldReturn404_whenBookNotFound() throws Exception {
                // Arrange
                doThrow(new ResourceNotFoundException("Book not found with id: 99"))
                                .when(iBookService).deleteBook(99L);

                // Act & Assert
                mockMvc.perform(delete("/api/v1/books/99"))
                                .andExpect(status().is4xxClientError());

                verify(iBookService, times(1)).deleteBook(99L);
        }

}
