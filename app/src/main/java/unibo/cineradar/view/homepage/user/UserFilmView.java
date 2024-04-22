package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;

/**
 * Film view of the user.
 */
public final class UserFilmView extends UserPanel {

    /**
     * Constructor of the user film view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.add(new JLabel("Benvenuto " +
                currentSessionContext.getController().getAccountDetails().get(0) +
                " nella pagina dei film."));
    }

}
