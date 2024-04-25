package unibo.cineradar.controller.registrar;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.context.registrar.RegistrarContext;

import java.time.LocalDate;

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

    /**
     * Creates a card and inserts it into the DB.
     *
     * @param username    The username of the card.
     * @param renewalDate The renewal date of the card.
     * @param cardNr      The number of the card.
     * @param cinemaNr    The cinema number of the card.
     * @return True if the insertion was successful, false otherwise.
     */
    public boolean insertCard(final String username,
                              final LocalDate renewalDate,
                              final int cardNr,
                              final int cinemaNr) {
        return registrarContext.registerCard(new CardReg(username, renewalDate, cinemaNr, cardNr));
    }
}
