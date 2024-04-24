package unibo.cineradar.controller.administrator;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.request.Request;

import java.util.List;

/**
 * Controller class for the admin.
 */
public final class AdminSessionController extends SessionControllerImpl {

    private final AdministratorContext administratorContext;

    /**
     * Creates the session controller of the administrator.
     *
     * @param ctr The existing generic controller.
     */
    public AdminSessionController(final SessionController ctr) {
        super(ctr);
        this.administratorContext = (AdministratorContext) getGenericContext();
    }

    /**
     * Gets every insertion request.
     *
     * @return A list containing insertion requests.
     */
    public List<Request> getInsertionRequests() {
        return administratorContext.getInsertionsRequests();
    }

}
