package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a serie, including reviews.
 */
public final class SerieDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final transient ViewContext currentSessionContext;

    /**
     * Constructs a new SerieDetailsView.
     *
     * @param currentSessionContext The current session context.
     */
    public SerieDetailsView(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
        //((UserSessionController) this.currentSessionContext.getController()).getDetailedSeries(); TODO: da fare ancora la query
    }

    /**
     * Retrieves the current session context.
     *
     * @return The current session context.
     */
    public ViewContext getCurrentSessionContext() {
        return currentSessionContext;
    }

}

// CHECKSTYLE: MagicNumber ON