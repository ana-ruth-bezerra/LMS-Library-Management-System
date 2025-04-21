package br.edu.ifba.inf008.shell;

import java.io.Serializable;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class Loan implements Serializable {
    private static final int MAX_BORROWED_BOOKS = 5;
    private static final int MAX_LOAN_DAYS = 14;
    private static final double DAILY_FINE = 0.5;

    private User user;
    private Book book;
    private Date borrowDate;
    private Date dueDate;
    private LibraryDatabase libraryDatabase;

    public Loan(User user, Book book, Date borrowDate, LibraryDatabase libraryDatabase) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.libraryDatabase = libraryDatabase;
        this.dueDate = Date.valueOf(borrowDate.toLocalDate().plusDays(MAX_LOAN_DAYS));
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String borrow() {
        if (user.getBorrowedBooks().size() >= MAX_BORROWED_BOOKS) {
            return "You already borrowed 5 books";
        }

        if (!libraryDatabase.getBooks().contains(book)) {
            return "Book not available in the library";
        }

        user.getBorrowedBooks().add(book);
        libraryDatabase.getBooks().remove(book);
        return "success";
    }

    public boolean isOverdue() {
        return new Date(System.currentTimeMillis()).after(dueDate);
    }

    public double calculateFine() {
        if (isOverdue()) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate.toLocalDate(), new Date(System.currentTimeMillis()).toLocalDate());
            return daysOverdue * DAILY_FINE;
        }
        return 0.0;
    }

    public double returnBook() {
        if (user.getBorrowedBooks().contains(book)) {
            double fine = calculateFine();
            user.getBorrowedBooks().remove(book);
            libraryDatabase.getBooks().add(book);
            return fine;
        }
        return -1;
    }
}