package br.edu.ifba.inf008.shell;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static int nextId = 1;
    private int id;
    private String email;
    private String name;
    private String password;
    private ArrayList<Book> borrowedBooks;
    private LibraryDatabase libraryDatabase;

    protected User(String name, String email, String password, LibraryDatabase libraryDatabase) {
        this.id = nextId++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
        this.libraryDatabase = libraryDatabase;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public LibraryDatabase getLibraryDatabase() {
        return libraryDatabase;
    }
}