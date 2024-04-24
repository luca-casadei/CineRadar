package unibo.cineradar.model.context.registrar;

import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.context.SessionContextImpl;
import unibo.cineradar.model.db.RegistrarOps;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Registrar;

/**
 * The context of a registrar session.
 */
public final class RegistrarContext extends SessionContextImpl {
    private final Registrar registrar;

    /**
     * Creates the context of a registrar session.
     *
     * @param currentlyLoggedAccount The currently logged account of the generic session.
     */
    public RegistrarContext(final Account currentlyLoggedAccount) {
        super(currentlyLoggedAccount);
        try (RegistrarOps mgr = new RegistrarOps()) {
            this.registrar = mgr.getRegistrarDetails(super.getUsername()).orElse(null);
        }
    }

    /**
     * Gets the associated cinema of the account.
     *
     * @return The associated cinema instance.
     */
    public Cinema getCinema() {
        try (RegistrarOps mgr = new RegistrarOps()) {
            return mgr.getAssociatedCinema(registrar.getCinema()).orElse(null);
        }
    }
}
