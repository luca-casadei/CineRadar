package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.view.ViewContext;

import javax.swing.JFrame;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a serie.
 */
public final class SerieDetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1366976476336457844L;

    private final transient ViewContext currentSessionContext;

    /**
     * Constructs a new SerieDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param serieId               The ID of the series to get the details of.
     */
    public SerieDetailsView(final ViewContext currentSessionContext, final int serieId) {
        this.currentSessionContext = currentSessionContext;
        //((UserSessionController) this.currentSessionContext.getController()).getDetailedSeries(); TODO: da fare ancora la query
        setTitle(String.valueOf(serieId));
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
