package com.mcosta.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mcosta.dao.AuthorDao;
import com.mcosta.dao.PublisherDao;
import com.mcosta.dao.BookDao;
import com.mcosta.dao.Persistence;
import com.mcosta.model.Author;
import com.mcosta.model.Publisher;
import com.mcosta.model.CopyBook;
import com.mcosta.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LivroController implements Initializable {
    // Objetos do programa
    private Book book;

    private Persistence<Book> livroDao = new BookDao();
    private Persistence<Publisher> editoraDao = new PublisherDao();
    private Persistence<Author> authorDao = new AuthorDao();

    private Boolean alterando;

    // Objetos de tela
    @FXML
    private ListView<Book> LstLivros;
    @FXML
    private ListView<Author> LstAutores;
    @FXML
    private ListView<CopyBook> LstExemplares;

    @FXML
    private Button BtnNovo;
    @FXML
    private Button BtnAlterar;
    @FXML
    private Button BtnExcluir;
    @FXML
    private Button BtnAdicionarAutor;
    @FXML
    private Button BtnRemoverAutor;
    @FXML
    private Button BtnAdicionarExemplar;
    @FXML
    private Button BtnGravar;
    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtIsbn;
    @FXML
    private TextField TxtNome;

    @FXML
    private ComboBox<Publisher> CboEditora;
    @FXML
    private ComboBox<Author> CboAutor;

    // Funcionalidades
    private void habilitarEdicao(boolean entrar) {
        BtnNovo.setDisable(entrar);
        BtnAlterar.setDisable(entrar);
        BtnExcluir.setDisable(entrar);
        TxtIsbn.setEditable(entrar);
        TxtNome.setEditable(entrar);
        CboEditora.setDisable(!entrar);
        CboAutor.setDisable(!entrar);
        BtnAdicionarAutor.setDisable(!entrar);
        BtnRemoverAutor.setDisable(!entrar);
        LstAutores.setDisable(!entrar);
        BtnAdicionarExemplar.setDisable(!entrar);
        LstExemplares.setDisable(!entrar);
        BtnGravar.setDisable(!entrar);
        BtnCancelar.setDisable(!entrar);
    }

    private void preencherLivros() {
        try {
            List<Book> books = livroDao.findAll();
            ObservableList<Book> dados = FXCollections.observableArrayList(books);
            LstLivros.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherAutores() {
        try {
            List<Author> autores = authorDao.findAll();
            ObservableList<Author> dados = FXCollections.observableArrayList(autores);
            CboAutor.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherEditoras() {
        try {
            List<Publisher> publishers = editoraDao.findAll();
            ObservableList<Publisher> dados = FXCollections.observableArrayList(publishers);
            CboEditora.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherExemplaresLivro() {
        if (book == null)
            return;
        try {
            ObservableList<CopyBook> dados = FXCollections.observableArrayList(book.getCopies());
            LstExemplares.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherAutoresLivro() {
        if (book == null)
            return;
        try {
            ObservableList<Author> dados = FXCollections.observableArrayList(book.getAuthors());
            LstAutores.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void exibirLivro() {
        if (book == null)
            return;

        TxtIsbn.setText(book.getIsbn());
        TxtNome.setText(book.getName());
        CboEditora.getSelectionModel().select(book.getPublisher());
        preencherAutoresLivro();
        preencherExemplaresLivro();
    }

    // Eventos de Tela
    @FXML
    private void BtnNovo_Action(ActionEvent event) {
        book = new Book();
        alterando = false;
        habilitarEdicao(true);
        TxtIsbn.setText("");
        TxtNome.setText("");
        CboEditora.getSelectionModel().clearSelection();
        CboAutor.getSelectionModel().clearSelection();
        TxtIsbn.requestFocus();
    }

    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        book = LstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();
        alterando = true;
        habilitarEdicao(true);
        TxtIsbn.setEditable(false);
        TxtNome.requestFocus();
    }

    @FXML
    private void BtnExcluir_Action(ActionEvent event) {

    }

    @FXML
    private void BtnAdicionarAutor_Action(ActionEvent event) {
        Author author = CboAutor.getSelectionModel().getSelectedItem();
        book.addAuthor(author);
        preencherAutoresLivro();

    }

    @FXML
    private void BtnRemoverAutor_Action(ActionEvent event) {
        Author author = LstAutores.getSelectionModel().getSelectedItem();
        book.removeAuthor(author);
        preencherAutoresLivro();

    }

    @FXML
    private void BtnAdicionarExemplar_Action(ActionEvent event) {
        book.addCopy(new CopyBook());
        preencherExemplaresLivro();
    }

    @FXML
    private void BtnGravar_Action(ActionEvent event) {
        try {
            book.setPublisher(CboEditora.getSelectionModel().getSelectedItem());
            book.setName(TxtNome.getText());
            if (alterando) {
                livroDao.update(book);
            } else {
                book.setIsbn(TxtIsbn.getText());
                livroDao.save(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        preencherLivros();
        habilitarEdicao(false);
    }

    @FXML
    private void BtnCancelar_Action(ActionEvent event) {
        habilitarEdicao(false);
    }

    @FXML
    private void LstLivros_KeyPressed(KeyEvent event) {
        book = LstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();

    }

    @FXML
    private void LstLivros_MouseClicked(MouseEvent event) {
        book = LstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherLivros();
        preencherAutores();
        preencherEditoras();
    }

}
