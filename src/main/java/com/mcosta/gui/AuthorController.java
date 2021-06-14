package com.mcosta.gui;

import com.mcosta.dao.*;
import com.mcosta.model.*;
import com.mcosta.util.MessageAlert;
import com.mcosta.validator.AuthorValidator;
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

public class AuthorController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private Text titleForm;

    @FXML
    private TextField inputNationality;

    @FXML
    private TextField inputName;

    @FXML
    private Button buttonDelete;

    private Boolean isCreating = true;

    private Author author;

    private Persistence<Author> authorDao = new AuthorDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        inputName.requestFocus();
        buttonDelete.setDisable(true);
        try {
            this.populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        String name = inputName.getText();
        String nationality = inputNationality.getText();

        try {
            if(isCreating) {
                author = new Author(name, nationality);
                AuthorValidator.isValid(author);
                authorDao.save(author);
                new MessageAlert("Sucesso", "Autor cadastrado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                clear();
            }
            else {
                author.setName(name);
                author.setNationality(nationality);
                AuthorValidator.isValid(author);
                authorDao.update(author);
                new MessageAlert("Sucesso", "Autor atualizado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
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
            authorDao.delete(author);
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
            author = (Author) tableView.getSelectionModel().getSelectedItem();
            inputName.setText(author.getName());
            inputNationality.setText(author.getNationality());
            isCreating = false;
            inputName.requestFocus();
            buttonDelete.setDisable(false);
        }
    }

    private void clear(){
        buttonDelete.setDisable(true);
        isCreating = true;
        author = null;
        inputName.setText("");
        inputNationality.setText("");
    }

    private void populateTableView() throws Exception {

        TableColumn columnId = new TableColumn("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<Author, String>("id"));

        TableColumn columnName = new TableColumn("NOME");
        columnName.setCellValueFactory(new PropertyValueFactory<Author, String>("name"));

        TableColumn columnNationality = new TableColumn("NACIONALIDADE");
        columnNationality.setCellValueFactory(new PropertyValueFactory<Author, String>("nationality"));

        tableView.getColumns().addAll(columnId, columnName, columnNationality);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void updateTable() throws Exception {
        ObservableList<Author> obs = FXCollections.observableArrayList(authorDao.findAll());
        tableView.setItems(obs);
        tableView.refresh();
    }
}
