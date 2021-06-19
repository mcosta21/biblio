package com.mcosta.validator;

import com.mcosta.dao.UserDao;
import com.mcosta.model.User;

public class UserValidator {

    private UserDao userDao = new UserDao();

    public void isValid(User user) throws Exception {
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

        User u = userDao.findByUsername(user.getUsername());
        if(user.getId() == null){
            if(u != null){
                throw new Exception("Login já utilizado.");
            }
        }
        else {
            if(u != null && !user.getId().equals(u.getId())){
                throw new Exception("Login já utilizado.");
            }
        }

    }

    public static void isValidToLogin(String username, String password) throws Exception {

        if (username == null || username.isEmpty()) {
            throw new Exception("Usuário não informado");
        }

        if (password == null || password.isEmpty()) {
            throw new Exception("Senha não informada.");
        }

    }
}
