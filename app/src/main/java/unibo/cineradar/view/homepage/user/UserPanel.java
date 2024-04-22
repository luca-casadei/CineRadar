package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.SessionContext;
import unibo.cineradar.view.ViewContext;

import javax.swing.*;

public class UserPanel extends JPanel {
    private final ViewContext currentSessionContext;

    protected UserPanel(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
        this.setVisible(true);
    }
}
