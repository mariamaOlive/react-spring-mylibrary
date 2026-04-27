package com.mariama.easylibrary.unittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariama.easylibrary.controller.AuthorController;
import com.mariama.easylibrary.entity.Author;
import com.mariama.easylibrary.exception.ResourceNotFoundException;
import com.mariama.easylibrary.service.IAuthorService;
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
public class AuthorControllerTest {

    @Mock
    private IAuthorService iAuthorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Author rowling;
    private Author riordan;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();

        rowling = new Author(1L, "J.K. Rowling", "British author known for Harry Potter", "rowling.jpg");
        riordan = new Author(2L, "Rick Riordan", "American author known for Percy Jackson", "riordan.jpg");
    }

    // -------------- Test 1 --------------
    @Test
    void getAuthors_shouldReturn200_withListOfAuthors() throws Exception {
        // Arrange
        when(iAuthorService.getAllAuthors()).thenReturn(List.of(rowling, riordan));

        // Act & Assert
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("J.K. Rowling"))
                .andExpect(jsonPath("$[1].name").value("Rick Riordan"));

        verify(iAuthorService, times(1)).getAllAuthors();
    }

    // -------------- Test 2 --------------
    @Test
    void getAuthors_shouldReturn200_withEmptyList() throws Exception {
        // Arrange
        when(iAuthorService.getAllAuthors()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(iAuthorService, times(1)).getAllAuthors();
    }

    // -------------- Test 3 --------------
    @Test
    void getAuthorById_shouldReturn200_whenAuthorExists() throws Exception {
        // Arrange
        when(iAuthorService.getAuthorById(1L)).thenReturn(rowling);

        // Act & Assert
        mockMvc.perform(get("/api/v1/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("J.K. Rowling"))
                .andExpect(jsonPath("$.bio").value("British author known for Harry Potter"));

        verify(iAuthorService, times(1)).getAuthorById(1L);
    }

    // -------------- Test 4 --------------
    @Test
    void getAuthorById_shouldReturn404_whenAuthorNotFound() throws Exception {
        // Arrange
        when(iAuthorService.getAuthorById(99L))
                .thenThrow(new ResourceNotFoundException("Author not found with id: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/authors/99"))
                .andExpect(status().isNotFound());

        verify(iAuthorService, times(1)).getAuthorById(99L);
    }

    // -------------- Test 5 --------------
    @Test
    void createAuthor_shouldReturn200_withSavedAuthor() throws Exception {
        // Arrange
        Author newAuthor = new Author(null, "J.K. Rowling", "British author known for Harry Potter", "rowling.jpg");
        when(iAuthorService.saveAuthor(any(Author.class))).thenReturn(rowling);

        // Act & Assert
        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("J.K. Rowling"));

        verify(iAuthorService, times(1)).saveAuthor(any(Author.class));
    }

    // -------------- Test 6 --------------
    @Test
    void deleteAuthor_shouldReturn204_whenAuthorExists() throws Exception {
        // Arrange
        doNothing().when(iAuthorService).deleteAuthor(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isNoContent());

        verify(iAuthorService, times(1)).deleteAuthor(1L);
    }

    // -------------- Test 7 --------------
    @Test
    void deleteAuthor_shouldReturn404_whenAuthorNotFound() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Author not found with id: 99"))
                .when(iAuthorService).deleteAuthor(99L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/authors/99"))
                .andExpect(status().is4xxClientError());

        verify(iAuthorService, times(1)).deleteAuthor(99L);
    }

}
