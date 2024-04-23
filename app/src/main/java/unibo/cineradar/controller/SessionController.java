package unibo.cineradar.controller;

import unibo.cineradar.controller.login.LoginController;
import unibo.cineradar.model.SessionContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Main controller of the application, containing the context of the session.
 */
public final class SessionController {
    private final SessionContext sessionContext;

    /**
     * Constructs a session.
     *
     * @param username  The username author of the session.
     * @param password  The password used to log in.
     * @param loginType The type of the login to perform.
     */
    public SessionController(final String username, final char[] password, final LoginType loginType) {
        final LoginController loginController = new LoginController();
        this.sessionContext = new SessionContext(loginController.login(username, password, loginType));
    }

    /**
     * Returns the details of the current logged user.
     *
     * @return A list containing the details of the current logged account.
     */
    public List<String> getAccountDetails() {
        final List<String> accountDetails = new ArrayList<>();
        final Account account = this.sessionContext.currentlyLoggedAccount();
        accountDetails.add(account.getUsername());
        accountDetails.add(account.getName());
        accountDetails.add(account.getLastName());
        return List.copyOf(accountDetails);
    }

    /**
     * Gets the type of the account to be used for casting.
     *
     * @return An instance of a LoginType choice.
     */
    public LoginType getUserType() {
        return this.sessionContext.getUserType();
    }

    /**
     * Checks the status of the session.
     *
     * @return True if the connection is successful, false otherwise.
     */
    public boolean sessionStatus() {
        return this.sessionContext.isValid();
    }

    public List<Film> getFilms() {
        return this.sessionContext.getFilms();
    }
}
