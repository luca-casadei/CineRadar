package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a multimedia, including reviews.
 */
public final class ReviewDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;

    private final transient ViewContext currentSessionContext;

    /**
     * Constructs a new MultimediaDetailsView.
     *
     * @param currentSessionContext The current session context.
     */
    public ReviewDetailsView(final ViewContext currentSessionContext) {
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
