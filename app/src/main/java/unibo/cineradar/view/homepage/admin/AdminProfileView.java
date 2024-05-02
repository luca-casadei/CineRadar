package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import java.io.Serial;

/**
 * Profile view of the user.
 */
public class AdminProfileView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -1671465329372673262L;

    /**
     * Constructor of the user profile view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public AdminProfileView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.add(new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina del profilo."));
    }
}
