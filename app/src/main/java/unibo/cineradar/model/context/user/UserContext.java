package unibo.cineradar.model.context.user;

import unibo.cineradar.model.context.SessionContextImpl;

import unibo.cineradar.model.db.UserOps;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.User;

import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The context of a user session.
 */
public final class UserContext extends SessionContextImpl {
    private final User user;

    /**
     * Constructs the context of a user.
     *
     * @param loggedAccount The currently logged account.
     */
    public UserContext(final Account loggedAccount) {
        super(loggedAccount);
        try (UserOps mgr = new UserOps()) {
            this.user = mgr.getUserDetails(super.getUsername()).orElse(null);
        }
    }

    private int getUserAge() {
        return Year.now().getValue() - (user.getBirthDate().getYear());
    }

    /**
     * Gets the films.
     *
     * @return The list of all films.
     */
    public List<Film> getFilms() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFilms(
                    getUserAge()
            );
        }
    }

    /**
     * Gets the series that this user can watch.
     *
     * @return A list of series that the logged user can watch.
     */
    public List<Serie> getSeries() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSeries(getUserAge());
        }
    }

    public Film getFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFilm(id)
                    .orElseThrow(() -> new NoSuchElementException("Film non trovato con id: " + id));
        }
    }

    public Serie getSerie(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSerie(id)
                    .orElseThrow(() -> new NoSuchElementException("Serie non trovata con id: " + id));
        }
    }

    public List<Review> getReviews() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getReviews(
                    super.getUsername()
            );
        }
    }
}
