package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a multimedia, including reviews.
 */
public class FilmDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;


    private final ViewContext currentSessionContext;

    /**
     * Constructs a new FilmDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param multimediaId          The ID of the film.
     */
    public FilmDetailsView(final ViewContext currentSessionContext, final int multimediaId) {
        this.currentSessionContext = currentSessionContext;
        ((UserSessionController) this.currentSessionContext.getController()).getDetailedFilms();
    }

}

// CHECKSTYLE: MagicNumber ON
