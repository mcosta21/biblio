package com.mcosta.util;

import com.mcosta.model.User;
import com.mcosta.model.UserTypeEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AccessProviderTest {

    @Test
    void TestarMetodoQueBuscaPaginasParaUsuarioAtendente(){
        // Given
        User user = new User("teste", "12345", "Teste", UserTypeEnum.ATTENDANT);

        // When
        List<AccessProvider.Page> pages = AccessProvider.getPagesByUserType(user.getUserType());

        // Then
        assertEquals(3, pages.size());
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("reader")));
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("loan-book")));
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("return-book")));
        assertFalse(pages.stream().anyMatch(x -> x.getUri().equals("user")));
        assertFalse(pages.stream().anyMatch(x -> x.getUri().equals("author")));
    }

    @Test
    void TestarMetodoQueBuscaPaginasParaUsuarioBibliotecario(){
        // Given
        User user = new User("teste", "12345", "Teste", UserTypeEnum.LIBRARIAN);

        // When
        List<AccessProvider.Page> pages = AccessProvider.getPagesByUserType(user.getUserType());

        // Then
        assertEquals(5, pages.size());
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("user")));
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("author")));
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("publisher")));
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("book")));
        assertTrue(pages.stream().anyMatch(x -> x.getUri().equals("list-overdue-readers")));
        assertFalse(pages.stream().anyMatch(x -> x.getUri().equals("reader")));
        assertFalse(pages.stream().anyMatch(x -> x.getUri().equals("return-book")));
    }
}
