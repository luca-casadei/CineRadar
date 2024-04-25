package unibo.cineradar.model.context.administrator;

import unibo.cineradar.model.context.SessionContextImpl;
import unibo.cineradar.model.db.AdminOps;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;

import java.util.List;

/**
 * The context of an administrator session.
 */
public final class AdministratorContext extends SessionContextImpl {

    /**
     * Constructs the context of an administrator.
     *
     * @param loggedAccount The currently logged account.
     */
    public AdministratorContext(final Account loggedAccount) {
        super(loggedAccount);
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

    /**
     * Gets the list of all series.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getSeries();
        }
    }

    /**
     * Gets the list of all films.
     *
     * @return A list of Films.
     */
    public List<Film> getFilms() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getFilms();
        }
    }
}
