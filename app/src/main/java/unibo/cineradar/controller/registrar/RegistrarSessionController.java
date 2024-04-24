package unibo.cineradar.controller.registrar;

import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.context.registrar.RegistrarContext;
import unibo.cineradar.model.login.LoginType;

/**
 * The controller class for the registrar.
 */
public final class RegistrarSessionController extends SessionControllerImpl {
    private final RegistrarContext registrarContext;

    /**
     * Constructs a session.
     *
     * @param username  The username author of the session.
     * @param password  The password used to log in.
     * @param loginType The type of the login to perform.
     */
    public RegistrarSessionController(final String username, final char[] password, final LoginType loginType) {
        super(username, password, loginType);
        this.registrarContext = (RegistrarContext) getGenericContext();
    }

    /**
     * Gets the details of the associated cinema.
     *
     * @return The cinema associated with the registrar.
     */
    public Cinema getCinemaDetails() {
        return registrarContext.getCinema();
    }
}
