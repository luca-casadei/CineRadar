package unibo.cineradar.view;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.model.login.LoginType;

/**
 * The view class containing the model context.
 */
public final class ViewContext {
    private final SessionController controller;

    /**
     * Cretes the view context.
     *
     * @param username The username that needs to be logged into the context.
     * @param password The password for authentication.
     * @param type     The type of the login to perform.
     */
    public ViewContext(final String username, final char[] password, final LoginType type) {
        this.controller = new SessionController(username, password, type);
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
