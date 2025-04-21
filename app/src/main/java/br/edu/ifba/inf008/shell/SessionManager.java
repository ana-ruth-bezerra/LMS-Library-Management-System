package br.edu.ifba.inf008.shell;

public class SessionManager {
    private static SessionManager instance;
    private User loggedUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isUserLogged() {
        return loggedUser != null;
    }
}
