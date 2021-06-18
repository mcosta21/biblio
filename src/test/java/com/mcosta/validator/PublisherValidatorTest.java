package com.mcosta.validator;

import com.mcosta.model.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PublisherValidatorTest {

    @Test
    void TestarMetodoQueValidaEditoraSemNome(){
        // Given
        Publisher publisher = new Publisher();

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            PublisherValidator.isValid(publisher);
        });

        // Then
        assertEquals("Nome não informado.", exception.getMessage());
    }

}
