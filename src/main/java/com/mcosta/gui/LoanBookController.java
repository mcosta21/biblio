package com.mcosta.gui;

import com.mcosta.dao.*;
import com.mcosta.model.*;
import com.mcosta.util.MessageAlert;
import com.mcosta.validator.LoanBookValidator;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoanBookController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private TableView tableViewOverdueReaders;

    @FXML
    private Text titleForm;

    @FXML
    private ComboBox<Reader> inputReader;

    @FXML
    private ComboBox<Book> inputBook;

    @FXML
    private ComboBox<CopyBook> inputCopyBook;

    @FXML
    private Button buttonDelete;

    private Boolean isCreating = true;

    private LoanBook loanBook;

    private Persistence<LoanBook> loanDao = new LoanBookDao();
    private BookDao bookDao = new BookDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        this.getReaders();
        this.getBooks();
        buttonDelete.setDisable(true);
        try {
            this.populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        try {
            if(loanBook != null && loanBook.getDateReturn() != null){
                throw new Exception("Empréstimo já devolvido, portanto não pode ser alterado");
            }

            Reader reader = inputReader.getSelectionModel().getSelectedItem();
            Book book = inputBook.getSelectionModel().getSelectedItem();
            CopyBook copyBook = inputCopyBook.getSelectionModel().getSelectedItem();

            if(isCreating) {
                loanBook = new LoanBook(reader, copyBook);
                LoanBookValidator.isValid(loanBook, book);
                copyBook.setBook(book);
                loanDao.save(loanBook);
                copyBook.setStatus(Status.BORROWED);
                bookDao.updateCopyBookStatus(copyBook);

                new MessageAlert("Sucesso", "Empréstimo realizado com sucesso.",
                        Alert.AlertType.INFORMATION).sendMessageAlert();
                clear();
            }
            else {
                CopyBook oldCopyBook = loanBook.getCopyBook();
                loanBook.setReader(reader);
                loanBook.setCopyBook(copyBook);
                LoanBookValidator.isValid(loanBook, book);
                loanDao.update(loanBook);

                if(!copyBook.getId().equals(oldCopyBook.getId())){
                    oldCopyBook.setStatus(Status.AVAILABLE);
                    copyBook.setStatus(Status.BORROWED);
                    bookDao.updateCopyBookStatus(oldCopyBook);
                    bookDao.updateCopyBookStatus(copyBook);
                }

                new MessageAlert("Sucesso", "Empréstimo atualizado com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
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

            if(loanBook != null && loanBook.getDateReturn() != null){
                throw new Exception("Empréstimo já devolvido, portanto não pode ser alterado");
            }

            loanDao.delete(loanBook);
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
            loanBook = (LoanBook) tableView.getSelectionModel().getSelectedItem();
            inputReader.getSelectionModel().select(loanBook.getReader());
            inputBook.getSelectionModel().select(loanBook.getCopyBook().getBook());
            inputCopyBook.getSelectionModel().select(loanBook.getCopyBook());
            isCreating = false;
            titleForm.setText(loanBook.getId().toString());
            buttonDelete.setDisable(false);
        }
    }

    @FXML
    public void onSelectBook(ActionEvent event) {
        Book book = inputBook.getSelectionModel().getSelectedItem();
        getCopiesBook(book != null ? book.getIsbn() : null);
    }

    private void clear(){
        isCreating = true;
        loanBook = null;
        buttonDelete.setDisable(true);
        inputReader.getSelectionModel().clearSelection();
        inputBook.getSelectionModel().clearSelection();
        inputCopyBook.getSelectionModel().select(null);
        titleForm.setText("Novo");
    }

    private void getReaders(){
        try{
            ObservableList<Reader> obs = FXCollections.observableArrayList(new ReaderDao().findAll());
            inputReader.setItems(obs);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
            e.printStackTrace();
        }
    }

    private void getBooks(){
        try{
            ObservableList<Book> obs = FXCollections.observableArrayList(new BookDao().findAll());
            inputBook.setItems(obs);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
            e.printStackTrace();
        }
    }

    private void getCopiesBook(String isbn){
        try{
            List<CopyBook> copies = new ArrayList<CopyBook>();
            if(isbn != null){
                copies.addAll(new BookDao().findCopiesBookAvailableByIsbn(isbn));
            }
            ObservableList<CopyBook> obs =
                    FXCollections.observableArrayList(copies);
            inputCopyBook.setItems(obs);
            inputCopyBook.getSelectionModel().select(null);
        }
        catch (Exception e){
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
            e.printStackTrace();
        }
    }

    private void populateTableView() throws Exception {

        TableColumn columnId = new TableColumn("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<LoanBook, Long>("id"));

        TableColumn columnCpf = new TableColumn("CPF");
        columnCpf.setCellValueFactory(new PropertyValueFactory<LoanBook, String>("cpfReader"));

        TableColumn columnName = new TableColumn("LEITOR");
        columnName.setCellValueFactory(new PropertyValueFactory<LoanBook, String>("nameReader"));

        TableColumn columnLoan = new TableColumn("EMPRÉSTIMO");
        columnLoan.setCellValueFactory(new PropertyValueFactory<LoanBook, LocalDate>("dateLoan"));

        TableColumn columnReturn = new TableColumn("DEVOLUÇÃO");
        columnReturn.setCellValueFactory(new PropertyValueFactory<LoanBook, LocalDate>("dateReturn"));

        TableColumn columnBook = new TableColumn("LIVRO");
        columnBook.setCellValueFactory(new PropertyValueFactory<LoanBook, String>("bookName"));

        tableView.getColumns().addAll(columnId, columnCpf, columnName, columnBook, columnLoan, columnReturn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void updateTable() throws Exception {
        ObservableList<LoanBook> obs = FXCollections.observableArrayList(loanDao.findAll());
        tableView.setItems(obs);
        tableView.refresh();
    }

}
