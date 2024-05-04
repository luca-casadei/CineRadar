package unibo.cineradar.controller.user;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.serie.Serie;

import java.util.List;
import java.util.Map;

/**
 * The user session controller class.
 */
public final class UserSessionController extends SessionControllerImpl {

    private final UserContext userContext;

    /**
     * Creates the session controller of the user.
     *
     * @param ctr The existing generic controller.
     */
    public UserSessionController(final SessionController ctr) {
        super(ctr);
        this.userContext = (UserContext) getGenericContext();
    }

    /**
     * Gets the list of films that the user can view.
     *
     * @return A list of Films.
     */
    public List<Film> getFilms() {
        return userContext.getFilms();
    }

    /**
     * Gets detailed films.
     *
     * @return The list of all detailed films.
     */
    public Map<Film, Cast> getDetailedFilms() {
        return userContext.getDetailedFilms();
    }


    /**
     * Gets the list of series that the user can view.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        return userContext.getSeries();
    }

    /**
     * Get the film with a particular id.
     *
     * @param id The id of the film to retrieve.
     * @return The film to a specific id.
     */
    public Film getFilm(final int id) {
        return userContext.getFilm(id);
    }

    /**
     * Get the serie with a particular id.
     *
     * @param id The id of the serie to retrieve.
     * @return The serie to a specific id.
     */
    public Serie getSerie(final int id) {
        return userContext.getSerie(id);
    }

    /**
     * Visualizes a film.
     *
     * @param filmId The film to be visualized.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeFilm(final int filmId) {
        return this.userContext.visualizeFilm(filmId);
    }

    /**
     * Removes all the preferences from the current user.
     */
    public void clearPreferences() {
        this.userContext.clearPreferences();
    }

    /**
     * Sets the preferences of the current user.
     *
     * @param preferences The preferences to set.
     */
    public void addPreference(final List<Genre> preferences) {
        this.userContext.addPreferences(preferences);
    }

    /**
     * Un-visualizes a film.
     *
     * @param filmId The film to be un-visualized.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetFilm(final int filmId) {
        return this.userContext.forgetFilm(filmId);
    }

    /**
     * Checks if the film has been viewed or not.
     *
     * @param filmId The ID of the film.
     * @return True if the film has been viewed, false otherwise.
     */
    public boolean isFilmViewed(final int filmId) {
        return this.userContext.isFilmViewed(filmId);
    }

    /**
     * Gets the list of reviews that the user has written.
     *
     * @return A list of Reviews.
     */
    public List<Review> getReviews() {
        return userContext.getReviews();
    }

    /**
     * Gets the list of current preferences.
     *
     * @return A list of genres.
     */
    public List<Genre> getUserPrefs() {
        return userContext.getUserPrefs();
    }
}
