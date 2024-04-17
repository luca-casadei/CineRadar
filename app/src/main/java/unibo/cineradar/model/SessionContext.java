package unibo.cineradar.model;

import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Amministratore;
import unibo.cineradar.model.utente.Registratore;
import unibo.cineradar.model.utente.Utente;

/**
 * Saves the current session when logged in.
 */
public final class SessionContext {
    private final Account currentlyLoggedAccount;

    /**
     * The current session constructor.
     *
     * @param currentlyLoggedAccount The account author of the session.
     */
    public SessionContext(final Account currentlyLoggedAccount) {
        this.currentlyLoggedAccount = currentlyLoggedAccount;
    }

    /**
     * Gets the type of the current user.
     *
     * @return A LoginType instance that states the type of the current user.
     */
    public LoginType getUserType() {
        if (this.currentlyLoggedAccount instanceof Amministratore) {
            return LoginType.ADMINISTRATION;
        } else if (this.currentlyLoggedAccount instanceof Utente) {
            return LoginType.USER;
        } else if (this.currentlyLoggedAccount instanceof Registratore) {
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
    public Account getCurrentlyLoggedAccount() {
        return this.currentlyLoggedAccount;
    }

}
