package unibo.cineradar.controller.administrator;

import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.request.Request;

import java.util.List;

/**
 * Controller class for the admin.
 */
public final class AdminSessionController extends SessionControllerImpl {

    private final AdministratorContext administratorContext;

    /**
     * Creates the controller for an admin session.
     *
     * @param username  The username of the admin.
     * @param password  The password of the admin.
     * @param loginType ADMINISTRATOR.
     */
    public AdminSessionController(final String username, final char[] password, final LoginType loginType) {
        super(username, password, loginType);
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
