package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.ICore;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;

public class UIController extends Application implements IUIController, Serializable {
    private transient Stage primaryStage;
    private static UIController uiController;
    private LibraryDatabase libraryDatabase = IOController.loadData();
    private Login loginManager = new Login(libraryDatabase);

    public UIController() {
    }

    @Override
    public void init() {
        uiController = this;
    }

    public static UIController getInstance() {
        return uiController;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(event -> {
            IOController.saveData(libraryDatabase);
        });

        showLoginScreen(primaryStage);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void showLoginScreen(Stage stage) {
        stage.setTitle("(LMS) Library Management System");
        Label userLabel = new Label("Email:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Type email");

        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Type password");

        Label messageLabel = new Label();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        Hyperlink registerLink = new Hyperlink("New to LSM? Sign Up");
        registerLink.setOnAction(e -> showRegisterPage());

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (loginManager.attemptLogin(username, password)) {
                showMainScreen(stage);
            } else {
                messageLabel.setText("Incorrect username or password. Please, try again.");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            }
        });

        VBox vbox = new VBox(10, userLabel, usernameField, passLabel, passwordField, loginButton, registerLink, messageLabel);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f0f0;");

        Scene loginScene = new Scene(vbox, 350, 300);
        stage.setScene(loginScene);
        stage.show();
    }

    private void showRegisterPage() {
        primaryStage.setTitle("Register User");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Type email");
        
        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Type new username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Type password");

        Label confirmPasswordLabel = new Label("Confirm password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");

        Label messageLabel = new Label();

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");

        Hyperlink backToLogin = new Hyperlink("Already have an account? Login");
        backToLogin.setOnAction(e -> showLoginScreen(primaryStage));

        registerButton.setOnAction(e -> {
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (libraryDatabase.userExists(email)) {
                messageLabel.setText("Email already in use.");
                messageLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 14px;");
            } else if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Fill in your credentials before submitting.");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            } else if (!confirmPassword.equals(password)) {
                messageLabel.setText("Passwords do not match.");
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            } else {
                loginManager.attemptRegister(username, email, password);
                messageLabel.setText("User registered successfully!");
                messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
            }

        });

        VBox vbox = new VBox(10,
        emailLabel, emailField, userLabel, usernameField, passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField, registerButton, backToLogin, messageLabel);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f0f0;");

        Scene registerScene = new Scene(vbox, 350, 400);
        primaryStage.setScene(registerScene);
        primaryStage.show();
    }

    public void showMainScreen(Stage stage) {
        stage.setTitle("(LMS) Library Management System");
        Label welcomeLabel = new Label("Welcome to the Library, " + SessionManager.getInstance().getLoggedUser().getName() +"!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        VBox headerBox = new VBox(15, welcomeLabel);
        headerBox.setPadding(new Insets(20));
        headerBox.setAlignment(Pos.CENTER);

        Button registerBookButton = new Button("Register Book");
        registerBookButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        registerBookButton.setOnAction(e -> showRegisterBookPage(stage));
        registerBookButton.setMaxWidth(Double.MAX_VALUE);
    
        Button searchBookButton = new Button("Search Book");
        searchBookButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        searchBookButton.setOnAction(e -> showSearchBookPage(stage));
        searchBookButton.setMaxWidth(Double.MAX_VALUE);
    
        Button loanBookButton = new Button("Loan Book");
        loanBookButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        loanBookButton.setOnAction(e -> showLoanBookPage(stage));
        loanBookButton.setMaxWidth(Double.MAX_VALUE);
    
        Button returnBookButton = new Button("Return Book");
        returnBookButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        returnBookButton.setOnAction(e -> showReturnBookPage(stage));
        returnBookButton.setMaxWidth(Double.MAX_VALUE);
        
        // Button loanReportsButton = new Button("Loan Reports");
        // loanReportsButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        // loanReportsButton.setOnAction(e -> showReportsPage(stage));
        // loanReportsButton.setMaxWidth(Double.MAX_VALUE);

        Button loanReportsButton = new Button("Loan Reports");
        loanReportsButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        loanReportsButton.setOnAction(e -> Core.getInstance().getPluginController().init());
        loanReportsButton.setMaxWidth(Double.MAX_VALUE);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> showLoginScreen(stage));
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        VBox buttonBox = new VBox(15, registerBookButton, searchBookButton, loanBookButton, returnBookButton, loanReportsButton, logoutButton);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, headerBox, buttonBox);
        mainLayout.setPadding(new Insets(20));

        Scene mainScene = new Scene(mainLayout, 400, 450);
        stage.setScene(mainScene);
        stage.show();
    }           

    private void showRegisterBookPage(Stage stage) {
        stage.setTitle("Register Book");
    
        Label ISBNLabel = new Label("ISBN:");
        TextField ISBNField = new TextField();
        ISBNField.setPromptText("Enter ISBN");
    
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter title");
    
        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();
        authorField.setPromptText("Enter author");
    
        Label genreLabel = new Label("Genre:");
        TextField genreField = new TextField();
        genreField.setPromptText("Enter genre");
    
        Label dateLabel = new Label("Publishing Date (yyyy-MM-dd):");
        TextField dateField = new TextField();
        dateField.setPromptText("Enter date");
    
        Label messageLabel = new Label();
    
        Button RegisterBookButton = new Button("Register Book");
        RegisterBookButton.setStyle("-fx-font-size: 14px; -fx-background-color:rgb(74, 154, 234); -fx-text-fill: white;");
    
        RegisterBookButton.setOnAction(e -> {
            try {
                int ISBN = Integer.parseInt(ISBNField.getText());
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                Date publishingDate = Date.valueOf(dateField.getText());
    
                if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || dateField.getText().isEmpty()) {
                    messageLabel.setText("Please fill in all fields.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                } else {
                    Book newBook = libraryDatabase.registerBook(ISBN, title, author, genre, publishingDate);
                    if (newBook != null) {
                        messageLabel.setText("Book Registered successfully!");
                        messageLabel.setStyle("-fx-text-fill: green;");
                    } else {
                        messageLabel.setText("A book with this ISBN already registered.");
                        messageLabel.setStyle("-fx-text-fill: orange;");
                    }
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("ISBN must be a number.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } catch (IllegalArgumentException ex) {
                messageLabel.setText("Invalid date format. Use yyyy-MM-dd.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        Button returnToMainPageButton = new Button("Return to main page");
        returnToMainPageButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        
        returnToMainPageButton.setOnAction(e -> {
            showMainScreen(stage);
        });
    
        VBox vbox = new VBox(10, ISBNLabel, ISBNField, titleLabel, titleField, authorLabel, authorField, 
                             genreLabel, genreField, dateLabel, dateField, RegisterBookButton, returnToMainPageButton, messageLabel);
        vbox.setPadding(new Insets(20));
    
        Scene registerBookScene = new Scene(vbox, 450, 500);
        stage.setScene(registerBookScene);
        stage.show();
    }

    private void showSearchBookPage(Stage stage) {
        stage.setTitle("Search Book");

        Label searchLabel = new Label("Search by ISBN, Title, or Author:");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter search term");

        Button searchBookButton = new Button("Search");
        searchBookButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");

        TextArea bookInfoArea = new TextArea();
        bookInfoArea.setEditable(false);
        bookInfoArea.setWrapText(true);
        bookInfoArea.setPrefHeight(200);

        searchBookButton.setOnAction(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                bookInfoArea.setText("Please enter an ISBN, Title, or Author.");
                return;
            }

            List<Book> foundBooks = libraryDatabase.searchBooks(query);

            if (!foundBooks.isEmpty()) {
                StringBuilder results = new StringBuilder("Results:\n\n");
                for (Book book : foundBooks) {
                    results.append(String.format(
                        "Title: %s\nAuthor: %s\nGenre: %s\nISBN: %d\n\n",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getISBN()
                    ));
                }
                bookInfoArea.setText(results.toString());
            } else {
                bookInfoArea.setText("No books found.");
            }
        });

        Button returnToMainPageButton = new Button("Return to main page");
        returnToMainPageButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        returnToMainPageButton.setOnAction(e -> showMainScreen(stage));

        VBox vbox = new VBox(10, searchLabel, searchField, searchBookButton, bookInfoArea, returnToMainPageButton);
        vbox.setPadding(new Insets(20));

        Scene searchBookScene = new Scene(vbox, 450, 500);
        stage.setScene(searchBookScene);
        stage.show();
    }
 
    public void showLoanBookPage(Stage stage) {
        stage.setTitle("Loan Book");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");

        Label availableBooksLabel = new Label("Available books:");

        ListView<Book> bookListView = new ListView<>();
        ObservableList<Book> availableBooks = FXCollections.observableArrayList(libraryDatabase.getBooks());
        bookListView.setItems(availableBooks);

        User currentUser = SessionManager.getInstance().getLoggedUser();

        if (currentUser == null) {
            messageLabel.setText("You must be logged in to borrow a book.");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        bookListView.setCellFactory(param -> new ListCell<Book>() {
            private final Button borrowButton = new Button("Borrow");
        
            {
                borrowButton.setStyle("-fx-font-size: 12px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
                borrowButton.setOnAction(event -> {
                    Book book = getItem();
                    Loan loan = new Loan(currentUser, book, new Date(System.currentTimeMillis()), libraryDatabase);
                    if (book != null) {
                        String result = loan.borrow();
                        if (result.equals("success")) {
                            availableBooks.remove(book);
                            messageLabel.setText("Book borrowed successfully!");
                            messageLabel.setTextFill(Color.GREEN);
                        } else {
                            messageLabel.setText(result);
                            messageLabel.setTextFill(Color.RED);
                        }
                    }
                });
            }
    
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label bookLabel = new Label("'" + book.getTitle() + "'" + " by " + book.getAuthor());
        
                    HBox hbox = new HBox(10, bookLabel, borrowButton);
                    HBox.setHgrow(bookLabel, Priority.ALWAYS);
                    bookLabel.setMaxWidth(Double.MAX_VALUE);
                    hbox.setAlignment(Pos.CENTER_LEFT);
        
                    setGraphic(hbox);
                }
            }
        });

        Button returnToMainPageButton = new Button("Return to main page");
        returnToMainPageButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        returnToMainPageButton.setOnAction(e -> showMainScreen(stage));

        VBox vbox = new VBox(10, availableBooksLabel, bookListView, messageLabel, returnToMainPageButton);
        vbox.setPadding(new Insets(20));

        Scene loanBookScene = new Scene(vbox, 450, 500);
        stage.setScene(loanBookScene);
        stage.show();
    }
       
    private void showReturnBookPage(Stage stage) {
        stage.setTitle("Return Book");
    
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px;");
    
        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser == null) {
            messageLabel.setText("You must be logged in to return a book.");
            messageLabel.setTextFill(Color.RED);
            return;
        }
    
        Label borrowedBooksLabel = new Label("Your borrowed books:");
    
        ListView<Book> borrowedBooksListView = new ListView<>();
        ObservableList<Book> borrowedBooks = FXCollections.observableArrayList(currentUser.getBorrowedBooks());
        borrowedBooksListView.setItems(borrowedBooks);
    
        borrowedBooksListView.setCellFactory(param -> new ListCell<Book>() {
            private final Button returnButton = new Button("Return");
        
            {
                returnButton.setStyle("-fx-font-size: 12px; -fx-background-color: #D9534F; -fx-text-fill: white;");
                returnButton.setOnAction(event -> {
                    Book book = getItem();
                    Loan loan = new Loan(currentUser, book, new Date(System.currentTimeMillis()), libraryDatabase);
                    if (book != null) {
                        loan.returnBook();
                        borrowedBooks.remove(book);
                        libraryDatabase.removeLoan(loan);
                        messageLabel.setText("Book returned successfully!");
                        messageLabel.setTextFill(Color.GREEN);
                    }
                });
            }
        
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label bookLabel = new Label(book.getTitle() + " by " + book.getAuthor());
        
                    HBox hbox = new HBox(10, bookLabel, returnButton);
                    HBox.setHgrow(bookLabel, Priority.ALWAYS);
                    bookLabel.setMaxWidth(Double.MAX_VALUE);
                    hbox.setAlignment(Pos.CENTER_LEFT);
        
                    setGraphic(hbox);
                }
            }
        });
    
        Button returnToMainPageButton = new Button("Return to main page");
        returnToMainPageButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4A9AEA; -fx-text-fill: white;");
        returnToMainPageButton.setOnAction(e -> showMainScreen(stage));
    
        VBox vbox = new VBox(10, borrowedBooksLabel, borrowedBooksListView, messageLabel, returnToMainPageButton);
        vbox.setPadding(new Insets(20));
    
        Scene returnBookScene = new Scene(vbox, 450, 500);
        stage.setScene(returnBookScene);
        stage.show();
    }     
    
    private void showLoanReportPage(VBox reportContainer) {
        reportContainer.getChildren().clear();
    
        Label reportLabel = new Label("Loan Report:");
        ListView<String> loanListView = new ListView<>();
        ObservableList<String> loanList = FXCollections.observableArrayList();
    
        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser == null) {
            loanList.add("You must be logged in to view loan report.");
        } else {
            ArrayList<Book> borrowedBooks = currentUser.getBorrowedBooks();
            if (borrowedBooks.isEmpty()) {
                loanList.add("No borrowed books.");
            } else {
                borrowedBooks.forEach(book -> {
                    Loan loan = new Loan(currentUser, book, new Date(System.currentTimeMillis()), currentUser.getLibraryDatabase());
                    String loanInfo = String.format("%s by %s - Borrowed on: %s - Due on: %s",
                            book.getTitle(), book.getAuthor(), loan.getBorrowDate(), loan.getDueDate());
                    loanList.add(loanInfo);
                });
            }
        }
    
        loanListView.setItems(loanList);
    
        reportContainer.getChildren().addAll(reportLabel, loanListView);
    }    

    public void showOverdueBooksReport(VBox reportContainer) {
        reportContainer.getChildren().clear();
    
        Label reportLabel = new Label("Overdue Books:");
        ListView<String> overdueBooksListView = new ListView<>();
        ObservableList<String> overdueBooksList = FXCollections.observableArrayList();
    
        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser == null) {
            overdueBooksList.add("You must be logged in to view overdue books.");
        } else {
            ArrayList<Book> borrowedBooks = currentUser.getBorrowedBooks();
    
            if (borrowedBooks.isEmpty()) {
                overdueBooksList.add("No late books.");
            } else {
                borrowedBooks.forEach(book -> {
                    Loan loan = new Loan(currentUser, book, new Date(System.currentTimeMillis()), currentUser.getLibraryDatabase());
    
                    if (loan.isOverdue()) {
                        long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate().toLocalDate(), LocalDate.now());
                        double fine = loan.calculateFine();
                        String bookInfo = String.format("%s by %s - Overdue: %d days - Fine: R$%.2f",
                                book.getTitle(), book.getAuthor(), overdueDays, fine);
                        overdueBooksList.add(bookInfo);
                    }
                });
            }
        }
    
        overdueBooksListView.setItems(overdueBooksList);
    
        reportContainer.getChildren().addAll(reportLabel, overdueBooksListView);
    }   

    public void showReportsPage(Stage stage) {
        IUIController uiController = ICore.getInstance().getUIController();
    
        if (uiController instanceof UIController) {    
            stage.setTitle("Reports");
        
            Label selectReportLabel = new Label("Select a report:");
            ComboBox<String> reportOptions = new ComboBox<>();
            reportOptions.getItems().addAll("Overdue Books Report", "Loan Report");
            reportOptions.setValue("Overdue Books Report");
        
            VBox reportContainer = new VBox(10);

            reportOptions.setOnAction(event -> {
                String selectedReport = reportOptions.getValue();
                if ("Overdue Books Report".equals(selectedReport)) {
                    showOverdueBooksReport(reportContainer);
                } else if ("Loan Report".equals(selectedReport)) {
                    showLoanReportPage(reportContainer);
                }
            });
        
            showOverdueBooksReport(reportContainer);
        
            VBox vbox = new VBox(10, selectReportLabel, reportOptions, reportContainer);
            vbox.setPadding(new Insets(20));
        
            Scene reportsScene = new Scene(vbox, 500, 500);
            stage.setScene(reportsScene);
            stage.show();
        }
    }
}