package unibo.cineradar;

import unibo.cineradar.view.CineRadarViewFrame;
import unibo.cineradar.view.LogInView;

/**
 * The main application loader.
 */
public final class CineRadar {
    private CineRadar() {

    }

    /**
     * Starts the login view.
     *
     * @param args The arguments passed to the application.
     */
    public static void main(final String[] args) {
        final CineRadarViewFrame loginView = new LogInView();
        loginView.display(false);
    }
}
