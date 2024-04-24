package unibo.cineradar.model.context;

import unibo.cineradar.model.utente.Account;

import java.util.List;

/**
 * The class representing a generic session context.
 */
public abstract class SessionContextImpl implements SessionContext {
    private final Account loggedAccount;

    /**
     * Creates a generic session.
     *
     * @param loggedAccount The generic account of the session.
     */
    protected SessionContextImpl(final Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

    @Override
    public final String getUsername() {
        return loggedAccount.getUsername();
    }

    @Override
    public final String getFirstName() {
        return loggedAccount.getName();
    }

    @Override
    public final String getLastName() {
        return loggedAccount.getLastName();
    }

    @Override
    public final List<String> getAccountDetails() {
        return List.of(
                loggedAccount.getUsername(),
                loggedAccount.getName(),
                loggedAccount.getLastName()
        );
    }
}
