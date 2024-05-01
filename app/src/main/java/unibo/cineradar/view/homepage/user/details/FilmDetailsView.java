package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a film, including reviews.
 */
public final class FilmDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final ViewContext currentSessionContext;

    /**
     * Constructs a new FilmDetailsView.
     *
     * @param currentSessionContext The current session context.
     */
    public FilmDetailsView(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
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
