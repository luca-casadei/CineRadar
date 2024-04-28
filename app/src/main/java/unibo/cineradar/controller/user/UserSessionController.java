package unibo.cineradar.controller.user;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.model.serie.Serie;

import java.util.List;

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
     * Gets the list of series that the user can view.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        return userContext.getSeries();
    }

    /**
     * Get the multimedia with a particular id.
     *
     * @param id The id of the multimedia to retrieve.
     * @return The multimedia to a specific id.
     */
    //public Multimedia getMultimedia(final int id) { return userContext.getMultimedia(id); }

    /**
     * Get the film with a particular id.
     *
     * @param id The id of the film to retrieve.
     * @return The film to a specific id.
     */
    public Film getFilm(final int id) { return userContext.getFilm(id); }

    /**
     * Get the serie with a particular id.
     *
     * @param id The id of the serie to retrieve.
     * @return The serie to a specific id.
     */
    public Serie getSerie(final int id) { return userContext.getSerie(id); }
}
