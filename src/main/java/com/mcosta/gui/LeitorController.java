package com.mcosta.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mcosta.dao.StudentDao;
import com.mcosta.dao.ReaderDao;
import com.mcosta.dao.TeacherDao;
import com.mcosta.model.Student;
import com.mcosta.model.Reader;
import com.mcosta.model.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class LeitorController implements Initializable {

    @FXML
    private ListView<Reader> LstLeitores;
    @FXML 
    private ComboBox<String> CboTipo; 

    @FXML 
    private HBox FrameMatricula;
    @FXML 
    private HBox FrameDisciplina;

    @FXML 
    private Button BtnNovo;
    @FXML 
    private Button BtnAlterar;
    @FXML 
    private Button BtnExcluir;
    @FXML 
    private Button BtnGravar;
    @FXML 
    private Button BtnCancelar;

    @FXML 
    private TextField TxtCpf;
    @FXML 
    private TextField TxtNome;
    @FXML 
    private TextField TxtSubClasse;

    @FXML
    private Label LblSubClasse;

    //Controla se é uma inclusão ou alteração
    private Boolean alteracao;
    private ReaderDao leitorDao = new ReaderDao();
    private Reader reader;

    //O autor que está sendo trabalhado


    //Códigos da tela
    private void habilitarEdicao(boolean editar) {
        TxtCpf.setEditable(editar);
        TxtNome.setEditable(editar);
        TxtSubClasse.setEditable(editar);
        CboTipo.setDisable(!editar);

        BtnGravar.setDisable(!editar);
        BtnCancelar.setDisable(!editar);

        BtnNovo.setDisable(editar);
        BtnAlterar.setDisable(editar);
        BtnExcluir.setDisable(editar);

        LstLeitores.setDisable(editar);

    }

    private void preencherLista() {
        try {
            ObservableList<Reader> leitores = FXCollections.observableArrayList(leitorDao.findAll());
            LstLeitores.setItems(leitores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void preencherCombo() {
        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Aluno");
        opcoes.add("Professor");
        ObservableList<String> tipos = FXCollections.observableArrayList(opcoes);
        CboTipo.setItems(tipos);
        CboTipo.getSelectionModel().select("Aluno");
    }

    private void mostrarSubClasse() {

        if (CboTipo.getSelectionModel().getSelectedItem()==null) return;
        if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {   
            LblSubClasse.setText("Matrícula");
        } else {
            LblSubClasse.setText("Disciplina");
        }        
    }

    private void exibirDados() {
        reader = LstLeitores.getSelectionModel().getSelectedItem();

        if (reader instanceof Student) {
            Student aluno = (Student) reader;
            TxtCpf.setText(aluno.getCpf());
            TxtNome.setText(aluno.getName());
            CboTipo.getSelectionModel().select("Aluno");
            LblSubClasse.setText("Matrícula");
            TxtSubClasse.setText(aluno.getRegistration());
        } else {
            Teacher teacher = (Teacher) reader;
            TxtCpf.setText(teacher.getCpf());
            TxtNome.setText(teacher.getName());
            CboTipo.getSelectionModel().select("Professor");
            LblSubClasse.setText("Disciplina");
            TxtSubClasse.setText(teacher.getDiscipline());
        }

    }

    //Eventos de Tela
    @FXML
    private void LstLeitores_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    private void LstLeitores_MouseClicked(MouseEvent event) {
        exibirDados();
    }
    @FXML
    private void CboTipo_KeyPressed(KeyEvent event) {
        mostrarSubClasse();
    }

    @FXML
    private void CboTipo_MouseClicked(MouseEvent event) {
        mostrarSubClasse();
    }


    @FXML
    private void BtnNovo_Action(ActionEvent event) {
        alteracao = false;
        habilitarEdicao(true);
    }

    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        alteracao = true;
        reader = LstLeitores.getSelectionModel().getSelectedItem();
        habilitarEdicao(true);
        CboTipo.setDisable(true);
    }

    @FXML
    private void BtnExcluir_Action(ActionEvent event) {
        Reader reader = LstLeitores.getSelectionModel().getSelectedItem();
        try {
            if (reader instanceof Teacher) {
                new TeacherDao().delete((Teacher) reader);
            } else {
                new StudentDao().delete((Student) reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        preencherLista();
        
    }
    @FXML
    private void BtnGravar_Action(ActionEvent event) {
        try {
                if (alteracao) {
                    if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                        Student aluno = (Student) reader;
                        aluno.setRegistration(TxtSubClasse.getText());
                        aluno.setName(TxtNome.getText());

                        new StudentDao().update(aluno);
                    } else {
                        Teacher teacher = (Teacher) reader;
                        teacher.setDiscipline(TxtSubClasse.getText());
                        teacher.setName(TxtNome.getText());

                        new TeacherDao().update(teacher);
                    }
                } else {
                    if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                        Student aluno = new Student();
                        aluno.setCpf(TxtCpf.getText());
                        aluno.setRegistration(TxtSubClasse.getText());
                        aluno.setName(TxtNome.getText());
                        new StudentDao().save(aluno);
                    } else {
                        Teacher teacher = new Teacher();
                        teacher.setCpf(TxtCpf.getText());
                        teacher.setDiscipline(TxtSubClasse.getText());
                        teacher.setName(TxtNome.getText());
                        new TeacherDao().save(teacher);
                    }
                }
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        preencherLista();
        habilitarEdicao(false);
    }

    @FXML
    private void BtnCancelar_Action(ActionEvent event) {
        habilitarEdicao(false);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
        preencherCombo();
    }    
}
