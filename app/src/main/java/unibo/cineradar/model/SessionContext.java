package unibo.cineradar.model;

import unibo.cineradar.model.utente.Account;

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
        placeHolder();
    }

    private void placeHolder() {
        throw new UnsupportedOperationException(this.currentlyLoggedAccount.toString());
    }
}
