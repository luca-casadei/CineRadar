package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JPanel;

/**
 * The panel used in the user part.
 */
public class UserPanel extends JPanel {
    private static final long serialVersionUID = 1L; // TODO: sostituire

    private final ViewContext currentSessionContext; // TODO

    /**
     * Constructs an istance of UserPanel.
     *
     * @param currentSessionContext The session context of the user.
     */
    protected UserPanel(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
        this.setVisible(true);
    }

    protected ViewContext getCurrentSessionContext(){
        return this.currentSessionContext;
    }
}
