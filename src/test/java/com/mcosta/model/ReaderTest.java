package com.mcosta.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    void TestarMetodoQueVerificaSeLeitorEhProfessor(){
        // Given
        Reader reader = new Teacher("739.099.880-67", "Professor Teste", "Rua 1", "Estrutura de dados");

        // When
        String type = reader.getType();

        // Then
        assertEquals("Professor", type);
    }

    @Test
    void TestarMetodoQueVerificaSeLeitorEhAluno(){
        // Given
        Reader reader = new Student("897.807.810-99", "Aluno Teste", "Rua 1", "123456");

        // When
        String type = reader.getType();

        // Then
        assertEquals("Aluno", type);
    }

    @Test
    void TestarMetodoQueBuscaDisciplinaDoLeitor(){
        // Given
        Reader reader = new Teacher("739.099.880-67", "Professor Teste", "Rua 1", "Estrutura de dados");

        // When
        String discipline = reader.getDiscipline();

        // Then
        assertEquals("Estrutura de dados", discipline);
    }

    @Test
    void TestarMetodoQueBuscaDisciplinaDeUmLeitorAluno(){
        // Given
        Reader reader = new Student("897.807.810-99", "Aluno Teste", "Rua 1", "123456");

        // When
        String discipline = reader.getDiscipline();

        // Then
        assertEquals("-", discipline);
    }
}
