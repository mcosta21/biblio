package com.mcosta.gui;

import com.mcosta.dao.AuthorDao;
import com.mcosta.dao.BookDao;
import com.mcosta.dao.Persistence;
import com.mcosta.dao.PublisherDao;
import com.mcosta.model.*;
import com.mcosta.util.MessageAlert;
import com.mcosta.validator.BookValidator;
import com.mcosta.util.DateConverter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private Text titleForm;

    @FXML
    private TextField inputIsbn;

    @FXML
    private TextField inputYear;

    @FXML
    private TextField inputName;

    @FXML
    private ComboBox<Author> inputAuthors;

    @FXML
    private ComboBox<Publisher> inputPublisher;

    @FXML
    private ListView listViewAuthors;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonRemoveAuthor;

    @FXML
    private DatePicker inputDateAcquisition;

    @FXML
    private ListView listViewCopiesBook;

    private Boolean isCreating = true;

    private Book book;

    private Author author;

    private Persistence<Book> bookDao = new BookDao();

    private BookValidator bookValidator = new BookValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        inputIsbn.requestFocus();
        this.getAuthors();
        this.getPublishers();
        buttonDelete.setDisable(true);
        buttonRemoveAuthor.setDisable(true);
        try {
            this.populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        String isbn = inputIsbn.getText();
        String year = inputYear.getText();
        String name = inputName.getText();
        Publisher publisher = inputPublisher.getSelectionModel().getSelectedItem();

        try {
            if(isCreating) {
                if(book == null){
                    book = new Book();
                }
                book.setIsbn(isbn);
                book.setName(name);
                book.setPublisher(publisher);
                bookValidator.isValid(book, year);
                book.setYear(Integer.valueOf(year));
                bookDao.save(book);
                new MessageAlert("Sucesso", "Livro cadastrado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
                clear();
            }
            else {
                book.setName(name);
                book.setPublisher(publisher);
                bookValidator.isValid(book, year, false);
                book.setYear(Integer.valueOf(year));
                bookDao.update(book);
                new MessageAlert("Sucesso", "Livro atualizado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
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
            bookDao.delete(book);
            clear();
            updateTable();
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickAddAuthor(ActionEvent event) {
        author = inputAuthors.getSelectionModel().getSelectedItem();
        if(author == null) {
            new MessageAlert("Erro", "Nenhum autor selecionado").sendMessageAlert();
            return;
        }
        if(book == null){
            book = new Book();
        }
        book.addAuthor(author);
        author = null;
        getAuthors();
        inputAuthors.getSelectionModel().clearSelection();
        populateListViewAuthors();
        buttonRemoveAuthor.setDisable(true);
    }

    @FXML
    public void onClickRemoveAuthor(ActionEvent event) {
        if(author == null) {
            new MessageAlert("Erro", "Nenhum autor selecionado").sendMessageAlert();
            return;
        }
        book.removeAuthor(author);
        author = null;
        getAuthors();
        populateListViewAuthors();
        buttonRemoveAuthor.setDisable(true);
    }
    @FXML
    public void onClickAddCopyBook(ActionEvent event) {
        String dateAcquisition = inputDateAcquisition.getValue() == null ? null :
                inputDateAcquisition.getValue().toString();

        if(dateAcquisition == null) {
            new MessageAlert("Erro", "Data de aquisição não informada").sendMessageAlert();
            return;
        }

        if(!DateConverter.isLocalDateValid(dateAcquisition)) {
            new MessageAlert("Erro", "Data de aquisição inválida").sendMessageAlert();
            return;
        }

        if(book == null){
            book = new Book();
        }

        book.addCopy(new CopyBook(book, DateConverter.StringToLocalDate(dateAcquisition)));
        inputDateAcquisition.setValue(null);
        populateListViewCopiesBook();
    }

    @FXML
    public void handleRowSelect(MouseEvent event) {
        if(event.getClickCount() == 2){
            book = (Book) tableView.getSelectionModel().getSelectedItem();
            inputIsbn.setText(book.getIsbn());
            inputIsbn.setDisable(true);
            inputYear.setText(String.valueOf(book.getYear()));
            inputName.setText(book.getName());
            inputPublisher.getSelectionModel().select(book.getPublisher());
            isCreating = false;
            inputIsbn.requestFocus();
            titleForm.setText(book.getIsbn());
            buttonDelete.setDisable(false);
            getAuthors();
            populateListViewAuthors();
            inputDateAcquisition.setValue(null);
            populateListViewCopiesBook();
        }
    }

    @FXML
    public void handleRowSelectAuthor(MouseEvent event){
        author = (Author) listViewAuthors.getSelectionModel().getSelectedItem();
        buttonRemoveAuthor.setDisable(false);
    }

    private void clear(){
        isCreating = true;
        book = null;
        buttonDelete.setDisable(true);
        inputIsbn.setText("");
        inputIsbn.setDisable(false);
        inputName.setText("");
        inputYear.setText("");
        inputAuthors.getSelectionModel().clearSelection();
        inputPublisher.getSelectionModel().clearSelection();
        titleForm.setText("Novo");
        getAuthors();
        populateListViewAuthors();
        populateListViewCopiesBook();
    }

    private void getAuthors() {
        try {
            List<Author> authors = new ArrayList<>();
            for(Author a : new AuthorDao().findAll()){
                if(book != null && book.getAuthors().contains(a)){
                    continue;
                }
                authors.add(a);
            }
            ObservableList<Author> obs = FXCollections.observableArrayList(authors);
            inputAuthors.setItems(obs);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    private void getPublishers() {
        try {
            ObservableList<Publisher> obs = FXCollections.observableArrayList(new PublisherDao().findAll());
            inputPublisher.setItems(obs);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    private void populateTableView() throws Exception {

        TableColumn columnIsbn = new TableColumn("ISBN");
        columnIsbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));

        TableColumn columnName = new TableColumn("NOME");
        columnName.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

        TableColumn columnYear = new TableColumn("ANO");
        columnYear.setCellValueFactory(new PropertyValueFactory<Book, String>("year"));

        TableColumn columnPublisher = new TableColumn("EDITORA");
        columnPublisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisherName"));

        tableView.getColumns().addAll(columnIsbn, columnName, columnYear, columnPublisher);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void populateListViewAuthors(){
        try {
            List<Author> authors = new ArrayList<>();
            if(book != null){
                authors.addAll(book.getAuthors());
            }
            ObservableList<Author> obs = FXCollections.observableArrayList(authors);
            listViewAuthors.setItems(obs);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    private void populateListViewCopiesBook(){
        try {
            List<CopyBook> copies = new ArrayList<>();
            if(book != null){
                copies.addAll(book.getCopies());
            }
            ObservableList<CopyBook> obs = FXCollections.observableArrayList(copies);
            listViewCopiesBook.setItems(obs);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    private void updateTable() throws Exception {
        ObservableList<Book> obs = FXCollections.observableArrayList(bookDao.findAll());
        tableView.setItems(obs);
        tableView.refresh();
    }
}
