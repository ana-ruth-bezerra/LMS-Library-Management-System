package br.edu.ifba.inf008.shell;

import java.io.Serializable;

import java.util.Date;

public class Book implements Serializable {
    private int ISBN;
    private String title;
    private String author;
    private String genre;
    private Date publishingDate;

    protected Book(int ISBN, String title, String author, String genre, Date publishingDate) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishingDate = publishingDate;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }
}
