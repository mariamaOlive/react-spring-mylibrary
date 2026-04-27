package com.mariama.easylibrary.unittesting.service;

import com.mariama.easylibrary.entity.Author;
import com.mariama.easylibrary.exception.ResourceNotFoundException;
import com.mariama.easylibrary.repository.AuthorRepository;
import com.mariama.easylibrary.service.impl.AuthorServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;

    // -------------- Test 1 --------------
    @Test
    void getAllAuthors_ShouldReturnListOfAuthors() {
        //Arrange
        Author author1 = new Author(1L, "J.K. Rowling", "British", "rowling.jpg");
        Author author2 = new Author(2L, "Rick Riordan", "American", "riordan.jpg");
        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        //Act
        List<Author> authors = authorServiceImpl.getAllAuthors();

        //Assert
        assertEquals(2, authors.size());
        assertEquals("J.K. Rowling", authors.get(0).getName());
        assertEquals("Rick Riordan", authors.get(1).getName());
        assertThat(authors).containsExactly(author1, author2);
        verify(authorRepository, times(1)).findAll();
    }

    // -------------- Test 2 --------------
    @Test
    void getAllAuthors_ShouldReturnEmptyList_WhenNoAuthorsExist() {
        when(authorRepository.findAll()).thenReturn(List.of());

        List<Author> authors = authorServiceImpl.getAllAuthors();

        assertThat(authors).isEmpty();
    }

    // -------------- Test 3 --------------
    @Test
    void getAuthorById_ShouldReturnAuthor_WhenAuthorExists() {
        //Arrange
        Author author = new Author(1L, "J.K. Rowling", "British", "rowling.jpg");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        //Act
        Author foundAuthor = authorServiceImpl.getAuthorById(1L);

        //Assert
        assertEquals("J.K. Rowling", foundAuthor.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    // -------------- Test 4 --------------
    @Test
    void getAuthorById_ShouldThrowException_WhenAuthorNotFound() {
        //Arrange
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> authorServiceImpl.getAuthorById(99L));
    }

    // -------------- Test 5 --------------
    @Test
    void addAuthor_ShouldSaveAuthor_WhenAuthorIsValid() {
        //Arrange
        Author author = new Author(null, "J.K. Rowling", "British", "rowling.jpg");
        when(authorRepository.save(author)).thenReturn(author);

        //Act
        Author savedAuthor = authorServiceImpl.saveAuthor(author);

        //Assert
        assertEquals("J.K. Rowling", savedAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    // -------------- Test 6 --------------
    @Test
    void addAuthor_ShouldThrowException_WhenAuthorIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> authorServiceImpl.saveAuthor(null));
    }

    // -------------- Test 7 --------------
    @Test
    void deleteAuthor_ShouldCallRepositoryDeleteById(){
        //Arrange
        doNothing().when(authorRepository).deleteById(1L);

        //Act
        authorServiceImpl.deleteAuthor(1L);

        //Assert
        verify(authorRepository, times(1)).deleteById(1L);
    }

}
