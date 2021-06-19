package com.mcosta.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mcosta.model.*;

public class BookDao extends Dao implements Persistence<Book> {

    @Override
    public void save(Book object) throws Exception {
       String sql = "insert into book (isbn, name, year, publisher_id) values (?, ?, ?, ?)";
       PreparedStatement ps = getPreparedStatement(false, sql);
       ps.setString(1, object.getIsbn());
       ps.setString(2, object.getName());
       ps.setInt(3, object.getYear());
       ps.setLong(4, object.getPublisher().getId());
       ps.executeUpdate();

       for (Author author: object.getAuthors()) {
           sql = "insert into book_author (book_isbn, author_id) values (?, ?)";
           ps = getPreparedStatement(false, sql);
           ps.setString(1, object.getIsbn());
           ps.setLong((2), author.getId());
           ps.executeUpdate();
       }

       for (CopyBook copyBook: object.getCopies()) {
           sql = "insert into copybook (book_isbn, date_acquisition, status) values (?, ?, ?)";
           ps = getPreparedStatement(true, sql);
           ps.setString(1, object.getIsbn());
           ps.setDate(2, Date.valueOf(copyBook.getDateAcquisition()));
           ps.setString(3, copyBook.getStatus().toString());
           ps.executeUpdate();

           ResultSet rs = ps.getGeneratedKeys();
           if (rs.next()) {
               copyBook.setId(rs.getLong("id"));
           }
       }
        
    }

    @Override
    public List<Book> findAll() throws Exception {
        String sql = "select book.isbn, book.name as book_name, book.year, publisher.id, publisher.name as " +
                "publisher_name from book inner join publisher on book.publisher_id = publisher.id "
                    + " order by book.name";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();
        
        List<Book> books = new ArrayList<Book>();
        
        while (rs.next()) {
            Book book = new Book();
            book.setIsbn(rs.getString("isbn"));
            book.setName(rs.getString("book_name"));
            book.setYear(rs.getInt("year"));

            Publisher publisher = new Publisher();
            publisher.setId(rs.getLong("id"));
            publisher.setName(rs.getString("publisher_name"));
            book.setPublisher(publisher);

            sql = "select author.* from book_author inner join author on book_author.author_id = author.id "
                + " where book_author.book_isbn = ?";

            PreparedStatement ps2 = getPreparedStatement(false, sql);
            ps2.setString(1, book.getIsbn());
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                Author author = new Author();
                author.setId(rs2.getLong("id"));
                author.setNationality(rs2.getString("nationality"));
                author.setName(rs2.getString("name"));

                book.addAuthor(author);
            }

            sql = "select * from copybook where book_isbn = ?";
            PreparedStatement ps3 = getPreparedStatement(false, sql);
            ps3.setString(1, book.getIsbn());
            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                CopyBook copyBook = new CopyBook();
                copyBook.setId(rs3.getLong("id"));
                copyBook.setStatus(Status.valueOf(rs3.getString("status")));
                Date dateAcquisition = rs3.getDate("date_acquisition");
                if(dateAcquisition != null){
                    copyBook.setDateAcquisition(dateAcquisition.toLocalDate());
                }
                book.addCopy(copyBook);
            }

            books.add(book);
        }

        return books;
    }

    @Override
    public void update(Book object) throws Exception {
        String sql = "update book set name = ?, year = ?, publisher_id = ? where isbn = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getName());
        ps.setInt(2, object.getYear());
        ps.setLong(3, object.getPublisher().getId());
        ps.setString(4, object.getIsbn());
        ps.executeUpdate();
 
        sql = "delete from book_author where book_isbn = ?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getIsbn());
        ps.executeUpdate();

        for (Author author: object.getAuthors()) {
            sql = "insert into book_author (book_isbn, author_id) values (?, ?)";
            ps = getPreparedStatement(false, sql);
            ps.setString(1, object.getIsbn());
            ps.setLong((2), author.getId());
            ps.executeUpdate();
        }        
        
        for (CopyBook copyBook : object.getCopies()) {
            if (copyBook.getId() == null) {
                sql = "insert into copybook (book_isbn, date_acquisition, status) values (?, ?, ?)";
                ps = getPreparedStatement(true, sql);
                ps.setString(1, object.getIsbn());
                ps.setDate(2, Date.valueOf(copyBook.getDateAcquisition()));
                ps.setString(3, copyBook.getStatus().toString());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    copyBook.setId(rs.getLong("id"));
                }
            } else {
                sql = "update copybook set status = ?, date_acquisition = ? where id = ?";
                ps = getPreparedStatement(false, sql);
                ps.setString(1, copyBook.getStatus().toString());
                ps.setDate(2, Date.valueOf(copyBook.getDateAcquisition()));
                ps.setLong(3, copyBook.getId());
                ps.executeUpdate();
            }

        }
    }

    @Override
    public void delete(Book object) throws Exception {

        String sql = "delete from copybook where book_isbn = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getIsbn());
        ps.executeUpdate();

        sql = "delete from book_author where book_isbn = ?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getIsbn());
        ps.executeUpdate();        

        sql = "delete from book where isbn = ?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getIsbn());
        ps.executeUpdate(); 
        
    }

    @Override
    public Book findById(Long id) {
        return null;
    }

    public Book findByIsbn(String isbn) throws Exception {
        String sql = "select book.isbn, book.name as book_name, book.year, publisher.id, publisher.name as " +
                "publisher_name from book inner join publisher on book.publisher_id = publisher.id "
                + " where book.isbn = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, isbn);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            Book book = new Book();
            book.setIsbn(rs.getString("isbn"));
            book.setName(rs.getString("book_name"));
            book.setYear(rs.getInt("year"));

            Publisher publisher = new Publisher();
            publisher.setId(rs.getLong("id"));
            publisher.setName(rs.getString("publisher_name"));
            book.setPublisher(publisher);

            sql = "select author.* from book_author inner join author on book_author.author_id = author.id "
                    + " where book_author.book_isbn = ?";

            PreparedStatement ps2 = getPreparedStatement(false, sql);
            ps2.setString(1, book.getIsbn());
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                Author author = new Author();
                author.setId(rs2.getLong("id"));
                author.setNationality(rs2.getString("nationality"));
                author.setName(rs2.getString("name"));

                book.addAuthor(author);
            }

            sql = "select * from copybook where book_isbn = ?";
            PreparedStatement ps3 = getPreparedStatement(false, sql);
            ps3.setString(1, book.getIsbn());
            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                CopyBook copyBook = new CopyBook();
                copyBook.setId(rs3.getLong("id"));
                copyBook.setStatus(Status.valueOf(rs3.getString("status")));
                Date dateAcquisition = rs3.getDate("date_acquisition");
                if(dateAcquisition != null){
                    copyBook.setDateAcquisition(dateAcquisition.toLocalDate());
                }
                book.addCopy(copyBook);
            }
            return book;
        }
        else {
            return null;
        }
    }

    public CopyBook findCopyBookById(Long copyBookId) throws Exception{
        String sql = "select book.isbn, book.name as book_name, book.year, publisher.id, publisher.name as "
                + "publisher_name from book inner join publisher on book.publisher_id = publisher.id "
                + "inner join copybook on copybook.book_isbn = book.isbn "
                + "where copybook.id = ?";

        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setLong(1, copyBookId);
        ResultSet rs = ps.executeQuery();
        
        CopyBook result = null;

        if (rs.next()) {
            Book book = new Book();
            book.setIsbn(rs.getString("isbn"));
            book.setName(rs.getString("book_name"));
            book.setYear(rs.getInt("year"));

            Publisher publisher = new Publisher();
            publisher.setId(rs.getLong("id"));
            publisher.setName(rs.getString("publisher_name"));
            book.setPublisher(publisher);

            sql = "select author.* from book_author inner join author on book_author.author_id = author.id "
                    + " where book_author.book_isbn = ?";

            PreparedStatement ps2 = getPreparedStatement(false, sql);
            ps2.setString(1, book.getIsbn());
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                Author author = new Author();
                author.setId(rs2.getLong("id"));
                author.setNationality(rs2.getString("nationality"));
                author.setName(rs2.getString("name"));

                book.addAuthor(author);
            }

            sql = "select * from copybook where book_isbn = ?";
            PreparedStatement ps3 = getPreparedStatement(false, sql);
            ps3.setString(1, book.getIsbn());
            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                CopyBook copyBook = new CopyBook();
                copyBook.setId(rs3.getLong("id"));
                copyBook.setStatus(Status.valueOf(rs3.getString("status")));
                Date dateAcquisition = rs3.getDate("date_acquisition");
                if(dateAcquisition != null){
                    copyBook.setDateAcquisition(dateAcquisition.toLocalDate());
                }
                book.addCopy(copyBook);
                if (copyBook.getId().equals(copyBookId)) {
                    result = copyBook;
                }
            }

        }

        return result;
    }

    public List<CopyBook> findCopiesBookAvailableByIsbn(String isbn) throws Exception {
        String sql = "select * from copybook where status = 'AVAILABLE' and book_isbn = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, isbn);
        ResultSet rs = ps.executeQuery();

        sql = "select * from book where book.isbn = ?";

        PreparedStatement ps2 = getPreparedStatement(false, sql);
        ps2.setString(1, isbn);
        ResultSet rs2 = ps2.executeQuery();

        Book book = new Book();
        if(rs2.next()){
            book.setIsbn(rs2.getString("isbn"));
            book.setName(rs2.getString("name"));
            book.setYear(rs2.getInt("year"));
        }

        List<CopyBook> copies = new ArrayList<CopyBook>();

        while (rs.next()) {
            CopyBook copyBook = new CopyBook();
            copyBook.setId(rs.getLong("id"));
            copyBook.setStatus(Status.valueOf(rs.getString("status")));
            Date dateAcquisition = rs.getDate("date_acquisition");
            if(dateAcquisition != null){
                copyBook.setDateAcquisition(dateAcquisition.toLocalDate());
            }
            copyBook.setBook(book);
            copies.add(copyBook);
        }

        return copies;
    }

    public void updateCopyBookStatus(CopyBook object) throws Exception {
        String sql = "update copybook set status = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getStatus().toString());
        ps.setLong(2, object.getId());
        ps.executeUpdate();
    }
}
