package com.mcosta.gui;

import com.mcosta.dao.*;
import com.mcosta.model.*;
import com.mcosta.util.MessageAlert;
import com.mcosta.validator.ReaderValidator;
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

public class ReaderController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private Text titleForm;

    @FXML
    private TextField inputCpf;

    @FXML
    private Button buttonChangeMode;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputAddress;

    @FXML
    private TextField inputDisciplineOrRegistration;

    @FXML
    private Label labelDisciplineOrRegistration;

    @FXML
    private Button buttonDelete;

    private Boolean isCreating = true;

    private Boolean isTeacherMode = true;

    private Reader reader;

    private Persistence<Reader> readerDao = new ReaderDao();

    private ReaderValidator readerValidator = new ReaderValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        inputCpf.requestFocus();
        buttonDelete.setDisable(true);
        try {
            this.populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        String cpf = inputCpf.getText();
        String name = inputName.getText();
        String disciplineOrRegistration = inputDisciplineOrRegistration.getText();
        String address = inputAddress.getText();

        try {
            if(isCreating) {
                if(isTeacherMode){
                    Teacher teacher = new Teacher(cpf, name, address, disciplineOrRegistration);
                    readerValidator.isValid(teacher);
                    new TeacherDao().save(teacher);
                    new MessageAlert("Sucesso", "Professor cadastrado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                }
                else {
                    Student student = new Student(cpf, name, address, disciplineOrRegistration);
                    readerValidator.isValid(student);
                    new StudentDao().save(student);
                    new MessageAlert("Sucesso", "Aluno cadastrado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                }
                clear();
            }
            else {
                if(isTeacherMode){
                    Teacher teacher = (Teacher) reader;
                    teacher.setDiscipline(disciplineOrRegistration);
                    teacher.setName(name);
                    teacher.setAddress(address);
                    readerValidator.isValid(teacher, false);
                    new TeacherDao().update(teacher);
                    new MessageAlert("Sucesso", "Professor atualizado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                }
                else {
                    Student student = (Student) reader;
                    student.setRegistration(disciplineOrRegistration);
                    student.setName(name);
                    student.setAddress(address);
                    readerValidator.isValid(student, false);
                    new StudentDao().update(student);
                    new MessageAlert("Sucesso", "Aluno atualizado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                }
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
    public void onClickChangeMode(ActionEvent event) {
        isTeacherMode = !isTeacherMode;
        if(isTeacherMode){
            labelDisciplineOrRegistration.setText("Disciplina");
            buttonChangeMode.setText("Cadastro de Aluno");
            titleForm.setText("Professor / Novo");
        }
        else {
            labelDisciplineOrRegistration.setText("Matrícula");
            buttonChangeMode.setText("Cadastro de Professor");
            titleForm.setText("Aluno / Novo");
        }
    }

    @FXML
    public void onClickDelete(ActionEvent event) {
        try{
            if(reader instanceof Teacher){
                new TeacherDao().delete((Teacher) reader);
            }
            else {
                new StudentDao().delete((Student) reader);
            }
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
            reader = (Reader) tableView.getSelectionModel().getSelectedItem();
            inputCpf.setDisable(true);
            inputCpf.setText(reader.getCpf());
            inputName.setText(reader.getName());
            inputAddress.setText(reader.getAddress());
            if(reader instanceof Teacher){
                isTeacherMode = true;
                labelDisciplineOrRegistration.setText("Disciplina");
                buttonChangeMode.setText("Cadastro de Aluno");
                inputDisciplineOrRegistration.setText(((Teacher) reader).getDiscipline());
                titleForm.setText("Professor / " + reader.getCpf());
            }
            else {
                isTeacherMode = false;
                labelDisciplineOrRegistration.setText("Matrícula");
                buttonChangeMode.setText("Cadastro de Professor");
                inputDisciplineOrRegistration.setText(((Student) reader).getRegistration());
                titleForm.setText("Aluno / " + reader.getCpf());
            }
            buttonDelete.setDisable(false);
            isCreating = false;
            inputName.requestFocus();
        }

    }

    private void clear(){
        isCreating = true;
        reader = null;
        buttonDelete.setDisable(true);
        inputCpf.setDisable(false);
        inputCpf.setText("");
        inputAddress.setText("");
        inputName.setText("");
        inputDisciplineOrRegistration.setText("");
        if(isTeacherMode){
            titleForm.setText("Professor / Novo");
        }
        else {
            titleForm.setText("Aluno / Novo");
        }
    }

    private void populateTableView() throws Exception {

        TableColumn columnCpf = new TableColumn("CPF");
        columnCpf.setCellValueFactory(new PropertyValueFactory<Reader, String>("cpf"));

        TableColumn columnName = new TableColumn("NOME");
        columnName.setCellValueFactory(new PropertyValueFactory<Reader, String>("name"));

        TableColumn columnAddress = new TableColumn("ENDEREÇO");
        columnAddress.setCellValueFactory(new PropertyValueFactory<Reader, String>("address"));

        TableColumn columnRegistration = new TableColumn("MATRÍCULA");
        columnRegistration.setCellValueFactory(new PropertyValueFactory<Reader, String>("registration"));

        TableColumn columnDiscipline = new TableColumn("DISCIPLINA");
        columnDiscipline.setCellValueFactory(new PropertyValueFactory<Reader, String>("discipline"));

        TableColumn columnType = new TableColumn("TIPO");
        columnType.setCellValueFactory(new PropertyValueFactory<Reader, String>("type"));

        tableView.getColumns().addAll(columnType, columnCpf, columnName, columnAddress, columnRegistration,
                columnDiscipline);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void updateTable() throws Exception {
        ObservableList<Reader> obs = FXCollections.observableArrayList(readerDao.findAll());
        tableView.setItems(obs);
        tableView.refresh();
    }
}
