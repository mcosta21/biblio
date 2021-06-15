package com.mcosta.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.mcosta.model.LoanBook;

public class LoanBookDao extends Dao implements Persistence<LoanBook> {

    @Override
    public void save(LoanBook object) throws Exception {
        String sql = "insert into loan_book (date_loan, reader_cpf, copybook_id) values (?,?,?)";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setDate(1, Date.valueOf(object.getDateLoan()));
        ps.setString(2, object.getReader().getCpf());
        ps.setLong(3, object.getCopyBook().getId());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        
        if (rs.next()) {
            object.setId(rs.getLong("id"));
        }
        
    }

    @Override
    public List<LoanBook> findAll() throws Exception {
        String sql = "select id, date_loan, date_return, reader_cpf, copybook_id from loan_book order by date_loan";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        List<LoanBook> loanBooks = new ArrayList<LoanBook>();

        while (rs.next()) {
            LoanBook loanBook = new LoanBook();
            loanBook.setId(rs.getLong("id"));

            Date dateReturn = rs.getDate("date_return");
            if(dateReturn != null) {
                loanBook.setDateReturn(dateReturn.toLocalDate());
            }

            loanBook.setDateLoan(rs.getDate("date_loan").toLocalDate());
            loanBook.setReader(new ReaderDao().findByCpf(rs.getString("reader_cpf")));
            loanBook.setCopyBook(new BookDao().findCopyBookById(rs.getLong("copybook_id")));

            loanBooks.add(loanBook);
        }

        return loanBooks;
        
    }

    @Override
    public void update(LoanBook object) throws Exception {
        String sql = "update loan_book set date_loan = ?, date_return = ?, reader_cpf = ?, copybook_id = ? where id =" +
                " ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setDate(1, Date.valueOf(object.getDateLoan()));

        Date dateReturn = null;
        if(object.getDateReturn() != null){
            dateReturn = Date.valueOf(object.getDateReturn());
        }

        ps.setDate(2, dateReturn);
        ps.setString(3, object.getCpfReader());
        ps.setLong(4, object.getCopyBook().getId());
        ps.setLong(5, object.getId());

        ps.executeUpdate();
    }

    @Override
    public void delete(LoanBook object) throws Exception {
        String sql = "delete from loan_book where id = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setLong(1, object.getId());
        ps.executeUpdate();
    }

    @Override
    public LoanBook findById(Long id) throws Exception {
        return null;
    }


    public List<LoanBook> findAllPending() throws Exception {
        String sql = "select id, date_loan, date_return, reader_cpf, copybook_id from loan_book where " +
                "date_return is null order by date_loan";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        List<LoanBook> loanBooks = new ArrayList<LoanBook>();

        while (rs.next()) {
            LoanBook loanBook = new LoanBook();
            loanBook.setId(rs.getLong("id"));

            Date dateReturn = rs.getDate("date_return");
            if(dateReturn != null) {
                loanBook.setDateReturn(dateReturn.toLocalDate());
            }

            loanBook.setDateLoan(rs.getDate("date_loan").toLocalDate());
            loanBook.setReader(new ReaderDao().findByCpf(rs.getString("reader_cpf")));
            loanBook.setCopyBook(new BookDao().findCopyBookById(rs.getLong("copybook_id")));

            loanBooks.add(loanBook);
        }

        return loanBooks;
    }

    public List<LoanBook> findAllOverdues() throws Exception {
        List<LoanBook> loanBooks = new ArrayList<LoanBook>();
        for(LoanBook loanBook : findAllPending()){
            if(loanBook.isOverdueLoan()){
                loanBooks.add(loanBook);
            }
        }
        return loanBooks;
    }
}
