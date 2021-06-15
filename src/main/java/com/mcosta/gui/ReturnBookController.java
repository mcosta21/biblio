package com.mcosta.gui;

import com.mcosta.dao.LoanBookDao;
import com.mcosta.dao.Persistence;
import com.mcosta.model.LoanBook;
import com.mcosta.util.MessageAlert;
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
import java.util.ResourceBundle;

public class ReturnBookController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private Text titleForm;

    @FXML
    private TextField inputReader;

    @FXML
    private TextField inputBook;

    @FXML
    private TextField inputCopyBook;

    @FXML
    private TextField inputLoan;

    @FXML
    private DatePicker inputReturnDate;

    private LoanBook loanBook;

    private Persistence<LoanBook> loanDao = new LoanBookDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        try {
            this.populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickReturnBook(ActionEvent event) {
        try {

            if(loanBook == null){
                throw new Exception("Empréstimo não selecionado");
            }

            LocalDate dateReturn = inputReturnDate.getValue();

            if(dateReturn == null) {
                throw new Exception("Data de devolução não informada");
            }

            loanBook.setDateReturn(dateReturn);
            loanDao.update(loanBook);
            clear();
            new MessageAlert("Sucesso", "Devolução realizada com sucesso.", Alert.AlertType.INFORMATION).sendMessageAlert();
            updateTable();
        } catch (Exception e) {
            new MessageAlert("Erro", e.getMessage()).sendMessageAlert();
        }
    }

    @FXML
    private void onClickCancel(ActionEvent event){
        clear();
    }

    @FXML
    public void handleRowSelect(MouseEvent event) {
        if(event.getClickCount() == 2){
            loanBook = (LoanBook) tableView.getSelectionModel().getSelectedItem();
            inputReader.setText(loanBook.getReader().toString());
            inputBook.setText(loanBook.getCopyBook().getBook().toString());
            inputCopyBook.setText(loanBook.getCopyBook().toString());
            inputLoan.setText(loanBook.getDateLoan().toString());
            titleForm.setText(loanBook.getId().toString());
        }
    }

    private void clear(){
        loanBook = null;
        inputReader.setText("");
        inputBook.setText("");
        inputCopyBook.setText("");
        inputLoan.setText("");
        inputReturnDate.getEditor().setText("");
        titleForm.setText("");
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

        TableColumn columnStatus = new TableColumn("STATUS");
        columnStatus.setCellValueFactory(new PropertyValueFactory<LoanBook, String>("status"));

        TableColumn columnBook = new TableColumn("LIVRO");
        columnBook.setCellValueFactory(new PropertyValueFactory<LoanBook, String>("bookName"));

        tableView.getColumns().addAll(columnId, columnCpf, columnName, columnBook, columnLoan, columnStatus);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateTable();
    }

    private void updateTable() throws Exception {
        ObservableList<LoanBook> obs = FXCollections.observableArrayList(new LoanBookDao().findAllPending());
        tableView.setItems(obs);
        tableView.refresh();
    }
}
