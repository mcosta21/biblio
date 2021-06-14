package com.mcosta.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    void nomeAlunoTeste() {
        final Student aluno = new Student();
        //aluno.setNome("Pedro");
        String esperado = "Pedro";
        //assertEquals(esperado, aluno.getNome());
    }
    
}
