package unibo.cineradar.view;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.controller.registrar.RegistrarSessionController;
import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.login.LoginType;

/**
 * The view class containing the model context.
 */
public final class ViewContext {
    private final SessionController controller;

    /**
     * Creates the view context.
     *
     * @param username The username that needs to be logged into the context.
     * @param password The password for authentication.
     * @param type     The type of the login to perform.
     */
    public ViewContext(final String username, final char[] password, final LoginType type) {
        switch (type) {
            // CHECKSTYLE: InnerAssignment OFF
            // Valid, readability not an issue here.
            case ADMINISTRATION -> controller = new AdminSessionController(username, password, type);
            case REGISTRATION -> controller = new RegistrarSessionController(username, password, type);
            case USER -> controller = new UserSessionController(username, password, type);
            // CHECKSTYLE: InnerAssignment ON
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    /**
     * Gets the session controller.
     *
     * @return An instance of the session controller.
     */
    public SessionController getController() {
        return this.controller;
    }
}
