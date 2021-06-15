package com.mcosta.gui;

import com.mcosta.dao.BookDao;
import com.mcosta.dao.LoanBookDao;
import com.mcosta.dao.Persistence;
import com.mcosta.dao.ReaderDao;
import com.mcosta.model.Book;
import com.mcosta.model.CopyBook;
import com.mcosta.model.LoanBook;
import com.mcosta.model.Reader;
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

public class OverdueReadersController extends AccessProviderController implements Initializable {

    @FXML
    private TableView tableViewOverdueReaders;

    @FXML
    private Text titleForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCredentials();
        try {
            this.populateTableView();
        } catch (Exception e) {
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

        TableColumn columnStatus = new TableColumn("STATUS");
        columnStatus.setCellValueFactory(new PropertyValueFactory<LoanBook, LocalDate>("status"));

        TableColumn columnDueDays = new TableColumn("DIAS VENCIDO");
        columnDueDays.setCellValueFactory(new PropertyValueFactory<LoanBook, Integer>("dueDays"));

        TableColumn columnBook = new TableColumn("LIVRO");
        columnBook.setCellValueFactory(new PropertyValueFactory<LoanBook, String>("bookName"));

        tableViewOverdueReaders.getColumns().addAll(columnId, columnCpf, columnName, columnBook, columnLoan,
                columnStatus, columnDueDays);
        tableViewOverdueReaders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getOverdueReaders();
    }

    private void getOverdueReaders() {
        try {
            ObservableList<LoanBook> obs  = FXCollections.observableArrayList(new LoanBookDao().findAllOverdues());
            tableViewOverdueReaders.setItems(obs);
            tableViewOverdueReaders.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
