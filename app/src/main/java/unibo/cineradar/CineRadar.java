package unibo.cineradar;

import unibo.cineradar.view.CineRadarViewComponent;
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
        final CineRadarViewComponent loginView = new LogInView();
        loginView.display();
    }
}
