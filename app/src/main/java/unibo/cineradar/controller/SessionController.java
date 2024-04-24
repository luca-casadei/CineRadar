package unibo.cineradar.controller;

import unibo.cineradar.model.login.LoginType;

import java.util.List;

/**
 * The controller for a logged account.
 */
public interface SessionController {
    /**
     * Checks the session status.
     *
     * @return True if the session is valid, false otherwise.
     */
    boolean isSessionValid();

    /**
     * Gets the type of the session.
     *
     * @return ADMINISTRATOR, USER or REGISTRAR
     */
    LoginType getType();

    /**
     * Gets the generic details of an account.
     *
     * @return The list of account details.
     */
    List<String> getAccountDetails();
}
