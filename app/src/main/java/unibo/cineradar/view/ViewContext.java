package unibo.cineradar.view;

import unibo.cineradar.controller.SessionController;

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
     */
    public ViewContext(final String username, final String password) {
        this.controller = new SessionController(username, password);
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
