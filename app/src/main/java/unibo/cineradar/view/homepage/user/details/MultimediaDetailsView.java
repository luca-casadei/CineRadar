package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a multimedia, including reviews.
 */
public class MultimediaDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;


    private final ViewContext currentSessionContext;
    private final int multimediaId;

    /**
     * Constructs a new MultimediaDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param multimediaId          The ID of the multimedia.
     */
    public MultimediaDetailsView(final ViewContext currentSessionContext, final int multimediaId) {
        this.currentSessionContext = currentSessionContext;
        this.multimediaId = multimediaId;
    }

}

// CHECKSTYLE: MagicNumber ON
