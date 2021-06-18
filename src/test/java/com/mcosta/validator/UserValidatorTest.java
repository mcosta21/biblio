package com.mcosta.validator;

import com.mcosta.model.Publisher;
import com.mcosta.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidatorTest {

    @Test
    void TestarMetodoQueValidaUsuarioSemLogin(){
        // Given
        User user = new User();

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            UserValidator.isValid(user);
        });

        // Then
        assertEquals("Login não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaUsuarioSemNome(){
        // Given
        User user = new User();
        user.setUsername("teste");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            UserValidator.isValid(user);
        });

        // Then
        assertEquals("Nome não informado.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaUsuarioSemSenha(){
        // Given
        User user = new User();
        user.setUsername("teste");
        user.setName("Teste");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            UserValidator.isValid(user);
        });

        // Then
        assertEquals("Senha não informada.", exception.getMessage());
    }

    @Test
    void TestarMetodoQueValidaUsuarioSemTipo(){
        // Given
        User user = new User();
        user.setUsername("teste");
        user.setName("Teste");
        user.setPassword("123456");

        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            UserValidator.isValid(user);
        });

        // Then
        assertEquals("Tipo de Usuário não informado.", exception.getMessage());
    }

}
