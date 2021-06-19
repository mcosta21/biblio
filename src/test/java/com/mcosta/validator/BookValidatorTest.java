package com.mcosta.validator;

import com.mcosta.dao.BookDao;
import com.mcosta.model.Author;
import com.mcosta.model.Book;
import com.mcosta.model.CopyBook;
import com.mcosta.model.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookValidatorTest {

    @Test
    void TestarMetodoQueValidaLivroSemIsbn(){
        // Given
        Book book = new Book();
        book.setName("Livro 1");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new BookValidator().isValid(book, "2021");
        });

        // Then
        assertEquals("ISBN não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaLivroSemNome(){
        // Given
        Book book = new Book();
        book.setIsbn("12414");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new BookValidator().isValid(book, "2021");
        });

        // Then
        assertEquals("Nome não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaLivroSemAno(){
        // Given
        Book book = new Book();
        book.setIsbn("12414");
        book.setName("Livro 1");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new BookValidator().isValid(book, null);
        });

        // Then
        assertEquals("Ano de lançamento não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaLivroComAnoInvalido(){
        // Given
        Book book = new Book();
        book.setIsbn("12414");
        book.setName("Livro 1");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new BookValidator().isValid(book, "20a1");
        });

        // Then
        assertEquals("Ano de lançamento inválido", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaLivroSemEditora(){
        // Given
        Book book = new Book();
        book.setIsbn("12414");
        book.setName("Livro 1");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new BookValidator().isValid(book, "2021");
        });

        // Then
        assertEquals("Editora não informada.", exception.getMessage());
    }

}
