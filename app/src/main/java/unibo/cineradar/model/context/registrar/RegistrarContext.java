package unibo.cineradar.model.context.registrar;

import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.context.SessionContextImpl;
import unibo.cineradar.model.db.operations.RegistrarOps;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.card.CardReg;

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

    /**
     * Inserts a new card into the database.
     *
     * @param card The card to add.
     * @return True if the insertion was successful, false otherwise.
     */
    public boolean registerCard(final CardReg card) {
        try (RegistrarOps mgr = new RegistrarOps()) {
            return mgr.registerCard(card);
        }
    }
}
