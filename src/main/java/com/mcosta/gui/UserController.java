package com.mcosta.gui;

import com.mcosta.dao.*;
import com.mcosta.model.*;
import com.mcosta.util.MessageAlert;
import com.mcosta.validator.UserValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private Text titleForm;

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputName;

    @FXML
    private ComboBox<UserTypeEnum> inputUserType;

    @FXML
    private Button buttonDelete;

    private Boolean isCreating = true;

    private User user;

    private Persistence<User> userDao = new UserDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        inputUsername.requestFocus();
        this.getUserTypes();
        buttonDelete.setDisable(true);
        try {
            this.populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String name = inputName.getText();
        UserTypeEnum userType = inputUserType.getSelectionModel().getSelectedItem();

        try {
            if(isCreating) {
                user = new User(username, password, name, userType);
                UserValidator.isValid(user);
                userDao.save(user);
                new MessageAlert("Sucesso", "Usuário cadastrado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                clear();
            }
            else {
                user.setUsername(username);
                user.setPassword(password);
                user.setName(name);
                user.setUserType(userType);
                UserValidator.isValid(user);
                userDao.update(user);
                new MessageAlert("Sucesso", "Usuário atualizado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
            }
            updateTable();
        } catch (Exception e) {
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    @FXML
    public void onClickCancel(ActionEvent event) {
        clear();
    }

    @FXML
    public void onClickDelete(ActionEvent event) {
        try{
            userDao.delete(user);
            clear();
            updateTable();
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    @FXML
    public void handleRowSelect(MouseEvent event) {
        if(event.getClickCount() == 2){
            user = (User) tableView.getSelectionModel().getSelectedItem();
            inputUsername.setText(user.getUsername());
            inputPassword.setText(user.getPassword());
            inputName.setText(user.getName());
            inputUserType.getSelectionModel().select(user.getUserType());
            isCreating = false;
            inputUsername.requestFocus();
            titleForm.setText(user.getId().toString());
            buttonDelete.setDisable(false);
        }

    }

    private void clear(){
        isCreating = true;
        user = null;
        buttonDelete.setDisable(true);
        inputUsername.setText("");
        inputPassword.setText("");
        inputName.setText("");
        inputUserType.getSelectionModel().clearSelection();
        titleForm.setText("Novo");
    }

    private void getUserTypes(){
        ObservableList<UserTypeEnum> obs = FXCollections.observableArrayList(UserTypeEnum.values());
        inputUserType.setItems(obs);
    }

    private void populateTableView() throws Exception {

        TableColumn columnId = new TableColumn("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<User, String>("id"));

        TableColumn columnUsername = new TableColumn("USUÁRIO");
        columnUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));

        TableColumn columnName = new TableColumn("NOME");
        columnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        TableColumn columnUserType = new TableColumn("TIPO");
        columnUserType.setCellValueFactory(new PropertyValueFactory<User, String>("userType"));

        tableView.getColumns().addAll(columnId, columnUsername, columnName, columnUserType);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void updateTable() throws Exception {
        ObservableList<User> obs = FXCollections.observableArrayList(userDao.findAll());
        tableView.setItems(obs);
        tableView.refresh();
    }
}
