package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.*;

/**
 * Review view of the user.
 */
public class UserReviewView extends UserPanel {


    /**
     * Constructor of the user review view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserReviewView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.add(new JLabel("Benvenuto " +
                currentSessionContext.getController().getAccountDetails().get(0) +
                " nella pagina delle recensioni."));
    }
}
