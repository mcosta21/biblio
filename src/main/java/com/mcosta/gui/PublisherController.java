package com.mcosta.gui;

import com.mcosta.dao.AuthorDao;
import com.mcosta.dao.Persistence;
import com.mcosta.dao.PublisherDao;
import com.mcosta.model.Author;
import com.mcosta.model.Publisher;
import com.mcosta.util.MessageAlert;
import com.mcosta.validator.AuthorValidator;
import com.mcosta.validator.PublisherValidator;
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

public class PublisherController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private Text titleForm;

    @FXML
    private TextField inputName;

    @FXML
    private Button buttonDelete;

    private Boolean isCreating = true;

    private Publisher publisher;

    private Persistence<Publisher> publisherDao = new PublisherDao();

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

        try {
            if(isCreating) {
                publisher = new Publisher();
                publisher.setName(name);
                PublisherValidator.isValid(publisher);
                publisherDao.save(publisher);
                new MessageAlert("Sucesso", "Editora cadastrada com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                clear();
            }
            else {
                publisher.setName(name);
                PublisherValidator.isValid(publisher, false);
                publisherDao.update(publisher);
                new MessageAlert("Sucesso", "Editora atualizada com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
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
            publisherDao.delete(publisher);
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
            publisher = (Publisher) tableView.getSelectionModel().getSelectedItem();
            inputName.setText(publisher.getName());
            isCreating = false;
            inputName.requestFocus();
            buttonDelete.setDisable(false);
        }
    }

    private void clear(){
        buttonDelete.setDisable(true);
        isCreating = true;
        publisher = null;
        inputName.setText("");
    }

    private void populateTableView() throws Exception {

        TableColumn columnId = new TableColumn("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<Author, String>("id"));

        TableColumn columnName = new TableColumn("NOME");
        columnName.setCellValueFactory(new PropertyValueFactory<Author, String>("name"));

        tableView.getColumns().addAll(columnId, columnName);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void updateTable() throws Exception {
        ObservableList<Publisher> obs = FXCollections.observableArrayList(publisherDao.findAll());
        tableView.setItems(obs);
        tableView.refresh();
    }
}
