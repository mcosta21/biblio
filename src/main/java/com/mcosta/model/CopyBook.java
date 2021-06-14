package com.mcosta.model;

import java.time.LocalDate;
import java.util.Objects;

public class CopyBook {

    private Long id;

    private Book book;

    private LocalDate dateAcquisition;

    private Status status = Status.AVAILABLE;;

    public CopyBook(){}

    public CopyBook(Book book, LocalDate dateAcquisition) {
        this.book = book;
        this.dateAcquisition = dateAcquisition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(LocalDate dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CopyBook)) return false;
        CopyBook copyBook = (CopyBook) o;
        return Objects.equals(id, copyBook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        if(id == null){
            return dateAcquisition.toString();
        }
        else {
            return book.getName() + " - " + dateAcquisition.toString();
        }
    }
}
