package unibo.cineradar.model.context.administrator;

import unibo.cineradar.model.context.SessionContextImpl;
import unibo.cineradar.model.db.AdminOps;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;

import java.util.List;

/**
 * The context of an administrator session.
 */
public final class AdministratorContext extends SessionContextImpl {
    private final Administrator admin;

    /**
     * Constructs the context of an administrator.
     *
     * @param loggedAccount The currently logged account.
     */
    public AdministratorContext(final Account loggedAccount) {
        super(loggedAccount);
        try (AdminOps mgr = new AdminOps()) {
            this.admin = mgr.getAdministrationDetails(super.getUsername()).orElse(null);
        }
    }

    /**
     * To be removed.
     *
     * @return Nothing useful.
     */
    public String placeHolder() {
        //TODO: Rimuovi quando usi admin, è per evitare il rosso nella compilazione.
        return admin.getPhoneNumber();
    }

    /**
     * Gets the list of insertion requests.
     *
     * @return A list containing insertion requests.
     */
    public List<Request> getInsertionsRequests() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getRequests();
        }
    }
}
