package com.mcosta.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class LoanBookTest {

    @Test
    void TestarMetodoQueBuscarDiasVendidosDoEmprestimoDeProfessor(){
        // Given
        final Teacher teacher = new Teacher("511.973.520-74", "Professor Teste", "Rua 1", "Estrutura de dados");
        final Book book = new Book("12345", "Livro 1", 2021, null);
        final CopyBook copyBook = new CopyBook(book, LocalDate.now());
        book.addCopy(copyBook);

        final LoanBook loanBook = new LoanBook();
        loanBook.setCopyBook(copyBook);
        loanBook.setReader(teacher);

        final LocalDate today = LocalDate.now();
        loanBook.setDateLoan(today.minusDays(Teacher.RETURN_LIMIT + 1));

        // When
        long dueDays = loanBook.getDueDays();

        // Then
        assertEquals(31, dueDays);
    }

    @Test
    void TestarMetodoQueBuscarDiasVendidosDoEmprestimoDeAluno(){
        // Given
        Student student = new Student("739.099.880-67", "Aluno Teste", "Rua 1", "123456");
        Book book = new Book("12345", "Livro 2", 2021, null);
        CopyBook copyBook = new CopyBook(book, LocalDate.now());
        book.addCopy(copyBook);

        LoanBook loanBook = new LoanBook();
        loanBook.setCopyBook(copyBook);
        loanBook.setReader(student);

        LocalDate today = LocalDate.now();
        loanBook.setDateLoan(today.minusDays(Student.RETURN_LIMIT + 1));

        // When
        long dueDays = loanBook.getDueDays();

        // Then
        assertEquals(16, dueDays);
    }

    @Test
    void TestarMetodoQueBuscarStatusEmAtrasoDoEmprestimo(){
        // Given
        Student student = new Student("739.099.880-67", "Aluno Teste", "Rua 1", "123456");
        Book book = new Book("12345", "Livro 2", 2021, null);
        CopyBook copyBook = new CopyBook(book, LocalDate.now());
        book.addCopy(copyBook);

        LoanBook loanBook = new LoanBook();
        loanBook.setCopyBook(copyBook);
        loanBook.setReader(student);

        LocalDate today = LocalDate.now();
        loanBook.setDateLoan(today.minusDays(Student.RETURN_LIMIT + 1));

        // When
        String status = loanBook.getStatus();

        // Then
        assertEquals("Em atraso", status);
    }

    @Test
    void TestarMetodoQueBuscarStatusEmDiaDoEmprestimo(){
        // Given
        Teacher teacher = new Teacher("511.973.520-74", "Professor Teste", "Rua 1", "Estrutura de dados");
        Book book = new Book("12345", "Livro 1", 2021, null);
        CopyBook copyBook = new CopyBook(book, LocalDate.now());
        book.addCopy(copyBook);

        LoanBook loanBook = new LoanBook();
        loanBook.setCopyBook(copyBook);
        loanBook.setReader(teacher);

        LocalDate today = LocalDate.now();
        loanBook.setDateLoan(today);

        // When
        String status = loanBook.getStatus();

        // Then
        assertEquals("Em dia", status);
    }

    @Test
    void TestarMetodoQueVerificarSeEmprestimoEstaEmAtraso(){
        // Given
        Teacher teacher = new Teacher("511.973.520-74", "Professor Teste", "Rua 1", "Estrutura de dados");
        Book book = new Book("12345", "Livro 1", 2021, null);
        CopyBook copyBook = new CopyBook(book, LocalDate.now());
        book.addCopy(copyBook);

        LoanBook loanBook = new LoanBook();
        loanBook.setCopyBook(copyBook);
        loanBook.setReader(teacher);

        LocalDate today = LocalDate.now();
        loanBook.setDateLoan(today.minusDays(Teacher.RETURN_LIMIT + 1));

        // When
        boolean isOverdueLoan = loanBook.isOverdueLoan();

        // Then
        assertTrue(isOverdueLoan);
    }
}
