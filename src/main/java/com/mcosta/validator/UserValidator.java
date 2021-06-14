package com.mcosta.validator;

import com.mcosta.model.User;

public class UserValidator {

    public static void isValid(User user) throws Exception {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new Exception("Login não informado.");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new Exception("Nome não informado.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new Exception("Senha não informada.");
        }

        if (user.getUserType() == null) {
            throw new Exception("Tipo de Usuário não informado.");
        }
    }

}
