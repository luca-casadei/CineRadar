package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a serie, including reviews.
 */
public class SerieDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final ViewContext currentSessionContext;

    /**
     * Constructs a new SerieDetailsView.
     *
     * @param currentSessionContext The current session context.
     */
    public SerieDetailsView(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
        //((UserSessionController) this.currentSessionContext.getController()).getDetailedSeries(); TODO: da fare ancora la query
    }

}

// CHECKSTYLE: MagicNumber ON
