package unibo.cineradar.view;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;

import java.util.Objects;

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
     */
    public ViewContext(final String username, final char[] password) {
        this.controller = SessionControllerImpl.of(username, password).orElse(null);
    }

    /**
     * Checks if the connection is valid.
     *
     * @return True if the connection is valid, false otherwise.
     */
    public boolean isValid() {
        return !Objects.isNull(controller);
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
