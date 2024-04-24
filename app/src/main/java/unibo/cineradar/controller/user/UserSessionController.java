package unibo.cineradar.controller.user;

import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.serie.Serie;

import java.util.List;

/**
 * The user session controller class.
 */
public class UserSessionController extends SessionControllerImpl {

    private final UserContext userContext;

    /**
     * Constructs the user session controller.
     *
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param loginType The login type of the user.
     */
    public UserSessionController(final String username, final char[] password, final LoginType loginType) {
        super(username, password, loginType);
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
}
