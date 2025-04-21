package br.edu.ifba.inf008.shell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryDatabase implements Serializable {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<Loan> loans;

    public LibraryDatabase(ArrayList<Book> books, ArrayList<User> users) {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }    

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    } 

    public boolean userExists(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public User registerUser(String name, String email, String password) {
        if (!userExists(email)) {
            User newUser = new User(name, email, password, this);
            users.add(newUser);
            return newUser;
        }
        return null;
    }

    public boolean bookExists(int ISBN) {
        return books.stream().anyMatch(book -> book.getISBN() == ISBN);
    }

    public Book registerBook(int ISBN, String title, String author, String genre, Date publishingDate) {
        if (!bookExists(ISBN)) {
            Book newBook = new Book(ISBN, title, author, genre, publishingDate);
            books.add(newBook);
            return newBook;
        }
        return null;
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (String.valueOf(book.getISBN()).contains(query) ||
                book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Loan> getLoansByUser(User user) {
        List<Loan> userLoans = new ArrayList<>();
        for (Loan loan : this.loans) {
            if (loan.getUser().equals(user)) {
                userLoans.add(loan);
            }
        }
        return userLoans;
    }       
}