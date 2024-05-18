package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JTable;
import java.io.Serial;

/**
 * Film view of the user.
 */
public final class UserFilmView extends UserFilteredView {
    @Serial
    private static final long serialVersionUID = 6530405035905149718L;

    /**
     * Constructor of the user film view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext, "Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina dei film.");
    }

    @Override
    protected JTable createContentTable(final int age) {
        return super.createFilmTable(age);
    }
}
