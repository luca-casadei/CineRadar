package unibo.cineradar.controller.user;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.serie.Serie;

import java.util.List;
import java.util.Map;

/**
 * The user session controller class.
 */
public class UserSessionController extends SessionControllerImpl {

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
    public Map<Multimedia, Cast> getDetailedFilms() {
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
     * Gets the list of reviews that the user has written.
     *
     * @return A list of Reviews.
     */
    public List<Review> getReviews() {
        return userContext.getReviews();
    }
}
