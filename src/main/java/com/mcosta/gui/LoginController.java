package com.mcosta.gui;

import com.mcosta.dao.UserDao;
import com.mcosta.model.User;
import com.mcosta.validator.UserValidator;
import com.mcosta.util.AccessProvider;
import com.mcosta.util.ManagerWindow;
import com.mcosta.util.MessageAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private UserValidator userValidator = new UserValidator();
    private UserDao userDao = new UserDao();

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPassword;

    @FXML
    void onClickLogin(ActionEvent event) throws Exception {
        try {
            String username = inputLogin.getText();
            String password = inputPassword.getText();

            UserValidator.isValidToLogin(username, password);

            User user = userDao.findUserByUsernameAndPassword(username, password);
            if(user == null) {
                throw new Exception("Usuário ou senha incorreto.");
            }

            AccessProvider.setUser(user);
            ManagerWindow.openWindow("main", "Biblio");
            ManagerWindow.closeWindow(event);
        }
        catch(Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
