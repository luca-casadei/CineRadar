package unibo.cineradar.controller.login;

import unibo.cineradar.model.login.Logger;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;

import java.util.Optional;

/**
 * API for logging users in.
 */
public final class LoginController {
    /**
     * Requests a user login to the model.
     *
     * @param username The username to log.
     * @param password The password to authorize the login.
     * @return Optional of an Account if the login is successful, empty otherwise.
     */
    public Optional<Account> login(final String username, final String password) {
        return Logger.logIn(username, password, LoginType.ADMINISTRATION);
    }
}
