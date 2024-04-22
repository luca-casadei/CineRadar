package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.*;

/**
 * Profile view of the user.
 */
public class UserProfileView extends UserPanel {
    /**
     * Constructor of the user profile view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserProfileView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.add(new JLabel("Benvenuto " +
                currentSessionContext.getController().getAccountDetails().get(0) +
                " nella pagina del profilo."));
    }
}
