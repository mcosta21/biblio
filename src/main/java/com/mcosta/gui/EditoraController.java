package com.mcosta.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mcosta.dao.PublisherDao;
import com.mcosta.dao.Persistence;
import com.mcosta.model.Publisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class EditoraController implements Initializable {
    
    @FXML
    private ListView<Publisher> LstEditoras;
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
    private TextField TxtId;
    @FXML 
    private TextField TxtNome;

    //Controla se é uma inclusão ou alteração
    private Boolean alteracao;

    //O autor que está sendo trabalhado
    private Publisher publisher;

    //Objeto de manipulação de dados
    private Persistence<Publisher> editoraDao = new PublisherDao();


    //Códigos da tela
    private void habilitarEdicao(boolean editar) {
        TxtId.setEditable(editar);
        TxtNome.setEditable(editar);

        BtnGravar.setDisable(!editar);
        BtnCancelar.setDisable(!editar);

        BtnNovo.setDisable(editar);
        BtnAlterar.setDisable(editar);
        BtnExcluir.setDisable(editar);

        LstEditoras.setDisable(editar);

        if (editar) {
            TxtNome.requestFocus();
        }
    }

    private void preencherLista() {
        List<Publisher> publishers = new ArrayList<Publisher>();
        try {
            publishers = editoraDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Publisher> dados = FXCollections.observableArrayList(publishers);

        
        LstEditoras.setItems(dados);
    }

    private void getEditoraSelecionado() {
        publisher = LstEditoras.getSelectionModel().getSelectedItem();
        TxtId.setText(publisher.getId().toString());
        TxtNome.setText(publisher.getName());
    }

    //Eventos de Tela
    @FXML
    private void LstAutores_KeyPressed(KeyEvent event) {
        getEditoraSelecionado();
    }

    @FXML
    private void LstAutores_MouseClicked(MouseEvent event) {
        getEditoraSelecionado();
    }

    @FXML
    private void BtnNovo_Action(ActionEvent event) {
        publisher = new Publisher();
        TxtId.setText("");
        TxtNome.setText("");
        alteracao = false;
        habilitarEdicao(true);
    }
    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        alteracao = true;
        habilitarEdicao(true);

    }
    @FXML
    private void BtnExcluir_Action(ActionEvent event) {
        try {
            editoraDao.delete(publisher);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        preencherLista();
        
    }
    @FXML
    private void BtnGravar_Action(ActionEvent event) {
        publisher.setName(TxtNome.getText());

        try {
            if (alteracao) {
                editoraDao.update(publisher);
            } else {
                editoraDao.save(publisher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        habilitarEdicao(false);
        preencherLista();

    }
    @FXML
    private void BtnCancelar_Action(ActionEvent event) {
        habilitarEdicao(false);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
    }    
}
