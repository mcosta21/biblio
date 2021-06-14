package com.mcosta.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mcosta.util.AccessProvider;
import com.mcosta.util.ManagerWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AccessProviderController {

    @FXML
    protected Label lblUsername;

    @FXML
    protected Label lblUserType;

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        ManagerWindow.openWindow("main", "Biblioteca");
        ManagerWindow.closeWindow(event);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        AccessProvider.setUser(null);
        ManagerWindow.openWindow("login", "Login");
        ManagerWindow.closeWindow(event);
    }

    protected void getCredentials() {
        lblUsername.setText(AccessProvider.getUser().getName());
        lblUserType.setText(AccessProvider.getUser().getUserType().getValue());
    }
}
