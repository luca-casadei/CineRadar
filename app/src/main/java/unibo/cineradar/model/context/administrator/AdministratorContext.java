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
 * This class manages the context specific to an administrator,
 * including operations related to films, TV series, and insertion requests.
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

    /**
     * Adds a new film to the database.
     *
     * @param film The film to be added.
     */
    public void addFilm(final Film film) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addFilm(film);
        }
    }

    /**
     * Adds a new TV series to the database.
     *
     * @param serie The TV series to be added.
     */
    public void addSerie(final Serie serie) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addSeries(serie);
        }
    }

    /**
     * Deletes a film from the database with the specified code.
     *
     * @param code The code of the film to be deleted.
     * @return True if the film was successfully deleted, false otherwise.
     */
    public boolean deleteFilm(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteFilm(code);
        }
    }

    /**
     * Deletes a TV series from the database with the specified code.
     *
     * @param code The code of the TV series to be deleted.
     * @return True if the TV series was successfully deleted, false otherwise.
     */
    public boolean deleteSeries(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteSeries(code);
        }
    }
}
