package com.mcosta.validator;

import com.mcosta.model.Author;

public class AuthorValidator {

    public static void isValid(Author author) throws Exception {

        if (author.getName() == null || author.getName().isEmpty()) {
            throw new Exception("Nome não informado.");
        }

        if (author.getNationality() == null || author.getNationality().isEmpty()) {
            throw new Exception("Nacionalidade não informada.");
        }

    }

}
