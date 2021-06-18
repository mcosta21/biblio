package com.mcosta.validator;

import com.mcosta.model.Publisher;
import com.mcosta.model.Reader;
import com.mcosta.model.Student;
import com.mcosta.model.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderValidatorTest {

    @Test
    void TestarMetodoQueValidaLeitorSemCpf(){
        // Given
        Teacher reader = new Teacher(null, "Professor Teste", "Rua 1", "Estrutura de dados");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new ReaderValidator().isValid(reader);
        });

        // Then
        assertEquals("CPF não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaLeitorComCpfInvalido(){
        // Given
        Student reader = new Student("685.157.500-01", "Aluno Teste", "Rua 1", "123456");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new ReaderValidator().isValid(reader);
        });

        // Then
        assertEquals("CPF inválido.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaLeitorSemNome(){
        // Given
        Teacher reader = new Teacher("241.018.560-60", "", "Rua 1", "Estrutura de dados");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new ReaderValidator().isValid(reader);
        });

        // Then
        assertEquals("Nome não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaProfessorSemDisciplina(){
        // Given
        Teacher reader = new Teacher("241.018.560-60", "Professor Teste", "Rua 1", null);

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new ReaderValidator().isValid(reader);
        });

        // Then
        assertEquals("Disciplina não informada.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaAlunoSemMatricula(){
        // Given
        Student reader = new Student("831.971.950-00", "Aluno Teste", "Rua 1", "");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            new ReaderValidator().isValid(reader);
        });

        // Then
        assertEquals("Matrícula não informada.", exception.getMessage());
    }

}
