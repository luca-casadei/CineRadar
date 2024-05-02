package unibo.cineradar.model.context.user;

import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.context.SessionContextImpl;

import unibo.cineradar.model.db.UserOps;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.User;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The context of a user session.
 */
public final class UserContext extends SessionContextImpl {
    private final User user;
    private final Map<Film, Cast> detailedFilms;

    /**
     * Constructs the context of a user.
     *
     * @param loggedAccount The currently logged account.
     */
    public UserContext(final Account loggedAccount) {
        super(loggedAccount);
        try (UserOps mgr = new UserOps()) {
            this.user = mgr.getUserDetails(super.getUsername()).orElse(null);
            this.detailedFilms = mgr.getFilmsDetails();
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
     * Gets detailed films.
     *
     * @return The list of all detailed films.
     */
    public Map<Film, Cast> getDetailedFilms() {
        return Map.copyOf(detailedFilms);
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

    /**
     * Retrieves a film by its ID.
     *
     * @param id The ID of the film to retrieve.
     * @return The film corresponding to the given ID.
     * @throws NoSuchElementException if no film is found with the specified ID.
     */
    public Film getFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFilm(id)
                    .orElseThrow(() -> new NoSuchElementException("Film non trovato con id: " + id));
        }
    }

    /**
     * Retrieves a series by its ID.
     *
     * @param id The ID of the series to retrieve.
     * @return The series corresponding to the given ID.
     * @throws NoSuchElementException if no series is found with the specified ID.
     */
    public Serie getSerie(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSerie(id)
                    .orElseThrow(() -> new NoSuchElementException("Serie non trovata con id: " + id));
        }
    }

    /**
     * Visualizes a film.
     *
     * @param id The film's id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.visualizeFilm(id, this.user.getUsername());
        }
    }

    /**
     * Removes film's visualization.
     *
     * @param id The film's id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.forgetFilm(id, this.user.getUsername());
        }
    }

    /**
     * Checks if the film has been viewed or not.
     *
     * @param id The ID of the film.
     * @return True if the film has been viewed, false otherwise.
     */
    public boolean isFilmViewed(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.isFilmViewed(id, this.user.getUsername());
        }
    }

    /**
     * Retrieves the reviews associated with the user.
     *
     * @return The list of reviews associated with the user.
     */
    public List<Review> getReviews() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getReviews(super.getUsername());
        }
    }
}
