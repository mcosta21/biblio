package com.mcosta.validator;

import com.mcosta.dao.BookDao;
import com.mcosta.dao.Persistence;
import com.mcosta.model.Book;
import com.mcosta.util.DateConverter;

public class BookValidator {

    private BookDao bookDao = new BookDao();

    public void isValid(Book book, String year) throws Exception {
        isValid(book, year, true);
    }

    public void isValid(Book book, String year, boolean isCreating) throws Exception {

        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new Exception("ISBN não informado.");
        }

        if (book.getName() == null || book.getName().isEmpty()) {
            throw new Exception("Nome não informado.");
        }

        if (year == null || year.isEmpty()) {
            throw new Exception("Ano de lançamento não informado.");
        }

        if(!DateConverter.isLocalDateValid(year + "-12-01")){
            throw new Exception("Ano de lançamento inválido");
        }

        if (book.getPublisher() == null) {
            throw new Exception("Editora não informada.");
        }

        Book b = bookDao.findByIsbn(book.getIsbn());
        if(b != null && isCreating){
            throw new Exception("ISBN já utilizado.");
        }
    }

}
