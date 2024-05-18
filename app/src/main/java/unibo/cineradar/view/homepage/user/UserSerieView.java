package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JTable;
import java.io.Serial;

/**
 * Serie view of the user.
 */
public final class UserSerieView extends UserFilteredView {
    @Serial
    private static final long serialVersionUID = -2884190954467853020L;

    /**
     * Constructor of the user serie view.
     *
     * @param currentSessionContext The context of the current session.
     */
    public UserSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext, "Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie.");
    }

    @Override
    protected JTable createContentTable(final int age) {
        return super.createSerieTable(age);
    }
}
