package unibo.cineradar.controller.login;

import unibo.cineradar.model.login.Logger;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;

/**
 * API for logging users in.
 */
public final class LoginController {
    /**
     * Requests a user login to the model.
     *
     * @param username The username to log.
     * @param password The password to authorize the login.
     * @param type     The type of login to perform.
     * @return Optional of an Account if the login is successful, empty otherwise.
     */
    public Account login(final String username, final char[] password, final LoginType type) {
        return Logger.logIn(username, password, type).orElse(null);
    }
}
