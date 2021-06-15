package com.mcosta.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class LoanBook {

    private Long id;

    private LocalDate dateLoan = LocalDate.now();

    private LocalDate dateReturn;

    private CopyBook copyBook;

    private Reader reader;

    public LoanBook(){}

    public LoanBook(Reader reader, CopyBook copyBook) {
        this.reader = reader;
        this.copyBook = copyBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateLoan() {
        return dateLoan;
    }

    public void setDateLoan(LocalDate dateLoan) {
        this.dateLoan = dateLoan;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
    }

    public CopyBook getCopyBook() {
        return copyBook;
    }

    public void setCopyBook(CopyBook copyBook) {
        this.copyBook = copyBook;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public String getBookName(){
        return this.copyBook.getBook().getName();
    }

    public String getCpfReader(){
        return this.reader.getCpf();
    }

    public String getNameReader(){
        return this.reader.getName();
    }

    public String getStatus(){
        return isOverdueLoan() ? "Em atraso" : "Em dia";
    }

    public long getDueDays(){
        LocalDate today = LocalDate.now();
        if(this.reader instanceof Teacher){
            return ChronoUnit.DAYS.between(this.dateLoan, today);
        }
        else {
            return ChronoUnit.DAYS.between(this.dateLoan, today);
        }
    }

    public boolean isOverdueLoan(){
        LocalDate today = LocalDate.now();
        if(this.reader instanceof Teacher){
            LocalDate ref = this.dateLoan.plusDays(Teacher.RETURN_LIMIT);
            return today.isAfter(ref);
        }
        else {
            LocalDate ref = this.dateLoan.plusDays(Student.RETURN_LIMIT);
            return today.isAfter(ref);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanBook)) return false;
        LoanBook loanBook = (LoanBook) o;
        return Objects.equals(id, loanBook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
