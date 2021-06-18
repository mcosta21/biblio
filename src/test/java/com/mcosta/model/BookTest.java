package com.mcosta.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void TestarMetodoDeAdicionarAutor(){
        // Given
        Book book = new Book("12345", "Livro 1", 2021, null);

        Author author1 = new Author("Autor 1", "Brasileiro");
        Author author2 = new Author("Autor 2", "Brasileiro");

        // When
        book.addAuthor(author1);
        book.addAuthor(author2);

        // Then
        assertEquals(2, book.getAuthors().size());
    }

    @Test
    void TestarMetodoDeRemoverAutor(){
        // Given
        Book book = new Book("12345", "Livro 1", 2021, null);

        Author author1 = new Author("Autor 1", "Brasileiro");
        Author author2 = new Author("Autor 2", "Brasileiro");

        // When
        book.addAuthor(author1);
        book.addAuthor(author2);
        book.removeAuthor(author1);

        // Then
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void TestarMetodoDeAdicionarExemplar(){
        // Given
        Book book = new Book("12345", "Livro 1", 2021, null);

        CopyBook copyBook1 = new CopyBook(book, LocalDate.now());
        CopyBook copyBook2 = new CopyBook(book, LocalDate.now().plusDays(1));

        // When
        book.addCopy(copyBook1);
        book.addCopy(copyBook2);

        // Then
        assertEquals(2, book.getCopies().size());
    }
}
