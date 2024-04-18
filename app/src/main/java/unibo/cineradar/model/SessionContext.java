package unibo.cineradar.model;

import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;

/**
 * Saves the current session when logged in.
 *
 * @param currentlyLoggedAccount The account owner of the session.
 */
public record SessionContext(Account currentlyLoggedAccount) {
    /**
     * The current session constructor.
     *
     * @param currentlyLoggedAccount The account author of the session.
     */
    public SessionContext {
    }

    /**
     * Gets the type of the current user.
     *
     * @return A LoginType instance that states the type of the current user.
     */
    public LoginType getUserType() {
        if (this.currentlyLoggedAccount instanceof Administrator) {
            return LoginType.ADMINISTRATION;
        } else if (this.currentlyLoggedAccount instanceof User) {
            return LoginType.USER;
        } else if (this.currentlyLoggedAccount instanceof Registrar) {
            return LoginType.REGISTRATION;
        } else {
            throw new IllegalStateException("Login type not supported.");
        }
    }

    /**
     * Gets the currently logged account.
     *
     * @return The instance of the currently logged account.
     */
    @Override
    public Account currentlyLoggedAccount() {
        return this.currentlyLoggedAccount;
    }

}
