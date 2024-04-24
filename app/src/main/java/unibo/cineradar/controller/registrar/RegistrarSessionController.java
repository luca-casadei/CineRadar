package unibo.cineradar.controller.registrar;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.context.registrar.RegistrarContext;

/**
 * The controller class for the registrar.
 */
public final class RegistrarSessionController extends SessionControllerImpl {
    private final RegistrarContext registrarContext;

    /**
     * Creates the session controller of the registrar.
     *
     * @param ctr The existing generic controller.
     */
    public RegistrarSessionController(final SessionController ctr) {
        super(ctr);
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
