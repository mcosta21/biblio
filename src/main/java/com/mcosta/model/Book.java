package com.mcosta.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {

    private String isbn;

    private String name;

    private int year;

    private Publisher publisher;

    private List<Author> authors = new ArrayList<Author>();

    private List<CopyBook> copies = new ArrayList<CopyBook>();

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<CopyBook> getCopies() {
        return copies;
    }

    public void setCopies(List<CopyBook> copies) {
        this.copies = copies;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPublisherName(){
        return this.publisher.getName();
    }

    public Book(){}

    public Book(String isbn, String name, int year, Publisher publisher) {
        this.isbn = isbn;
        this.name = name;
        this.year = year;
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addCopy(CopyBook copy) {
        copy.setBook(this);
        copy.setStatus(Status.AVAILABLE);
        copies.add(copy);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    @Override
    public String toString() {
        return isbn + " - " + name + " " + getPublisherName() + " " + year ;
    }
}
