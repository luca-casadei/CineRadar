package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;

/**
 * Serie view of the user.
 */
public final class UserSerieView extends UserPanel {
    private static final long serialVersionUID = 1L; // TODO: sostituire
    /**
     * Constructor of the user serie view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.add(new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie."));
    }
}
