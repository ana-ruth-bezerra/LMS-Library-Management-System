package br.edu.ifba.inf008.shell;

import java.io.Serializable;

public class Login implements Serializable {
    private LibraryDatabase libraryDatabase;

    public Login(LibraryDatabase libraryDatabase) {
        this.libraryDatabase = libraryDatabase;
    }

    public boolean checkCredentials(String email, String password) {
        return libraryDatabase.getUsers().stream()
                    .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
    }

    public boolean attemptLogin(String email, String password) {
        User authenticatedUser = libraryDatabase.getUsers().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    
        if (authenticatedUser != null) {
            SessionManager.getInstance().setLoggedUser(authenticatedUser);
            return true;
        }
        
        return false;
    }    

    public boolean attemptRegister(String name, String email, String password) {
        return libraryDatabase.registerUser(name, email, password) != null;
    }
}