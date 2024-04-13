package unibo.cineradar.model;

import unibo.cineradar.model.utente.Account;

public final class SessionContext {
    private final Account currentlyLoggedAccount;

    public SessionContext(final Account currentlyLoggedAccount) {
        this.currentlyLoggedAccount = currentlyLoggedAccount;
    }
}
