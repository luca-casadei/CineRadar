package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.UserPanel;

import javax.swing.JLabel;
import java.io.Serial;

/**
 * Rankings view of the Admin.
 */
public class AdminRankingsView extends UserPanel {
    @Serial
    private static final long serialVersionUID = -924897837300851458L;

    /**
     * Constructor of the admin rankings view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminRankingsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);

        // Adds the welcome label to the view
        this.add(new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle classifiche degli utenti."));

        // Adds the ranking table to the view - TODO
    }
}
