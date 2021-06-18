package com.mcosta.validator;

import com.mcosta.model.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorValidatorTest {

    @Test
    void TestarMetodoQueValidaAutorSemNome(){
        // Given
        Author author = new Author();
        author.setNationality("Brasileiro");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            AuthorValidator.isValid(author);
        });

        // Then
        assertEquals("Nome não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaAutorSemNacionalidade(){
        // Given
        Author author = new Author();
        author.setName("Teste");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            AuthorValidator.isValid(author);
        });

        // Then
        assertEquals("Nacionalidade não informada.", exception.getMessage());
    }
}
