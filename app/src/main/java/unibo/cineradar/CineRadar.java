package unibo.cineradar;

import unibo.cineradar.view.CineRadarViewComponent;
import unibo.cineradar.view.LogInView;

public class CineRadar {
    public static void main(final String[] args) {
        final CineRadarViewComponent loginView = new LogInView();
        loginView.display();
    }
}
