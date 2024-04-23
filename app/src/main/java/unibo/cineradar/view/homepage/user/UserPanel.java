package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * The panel used in the user part.
 */
public abstract class UserPanel extends JPanel {
    private static final long serialVersionUID = 1L; // TODO: sostituire

    private final ViewContext currentSessionContext; // TODO

    /**
     * Constructs an istance of UserPanel.
     *
     * @param currentSessionContext The session context of the user.
     */
    protected UserPanel(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }

    /**
     * Retrieves the current session context.
     *
     * @return The current session context.
     */
    protected ViewContext getCurrentSessionContext() {
        return this.currentSessionContext;
    }
}
