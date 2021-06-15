package com.mcosta.validator;

import com.mcosta.model.Book;
import com.mcosta.model.LoanBook;

public class LoanBookValidator {

    public static void isValid(LoanBook loanBook, Book book) throws Exception {
        if (loanBook.getReader() == null) {
            throw new Exception("Leitor não informado.");
        }

        if (book == null) {
            throw new Exception("Livro não informado.");
        }

        if (loanBook.getCopyBook() == null) {
            throw new Exception("Exemplar não informado.");
        }

    }

}
