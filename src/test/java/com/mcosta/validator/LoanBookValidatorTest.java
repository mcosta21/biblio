package com.mcosta.validator;

import com.mcosta.model.Book;
import com.mcosta.model.LoanBook;
import com.mcosta.model.Reader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanBookValidatorTest {

    @Test
    void TestarMetodoQueValidaEmprestimoSemLeitor(){
        // Given
        LoanBook loanBook = new LoanBook();

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            LoanBookValidator.isValid(loanBook, null);
        });

        // Then
        assertEquals("Leitor não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaEmprestimoSemLivro(){
        // Given
        LoanBook loanBook = new LoanBook();
        loanBook.setReader(new Reader());

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            LoanBookValidator.isValid(loanBook, null);
        });

        // Then
        assertEquals("Livro não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaEmprestimoSemExemplar(){
        // Given
        LoanBook loanBook = new LoanBook();
        loanBook.setReader(new Reader());

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            LoanBookValidator.isValid(loanBook, new Book());
        });

        // Then
        assertEquals("Exemplar não informado.", exception.getMessage());
    }

}
