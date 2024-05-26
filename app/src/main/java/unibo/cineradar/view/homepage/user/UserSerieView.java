package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.view.ViewContext;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.io.Serial;
import java.util.List;

/**
 * Serie view of the user.
 */
public final class UserSerieView extends UserFilteredView {
    @Serial
    private static final long serialVersionUID = -2884190954467853020L;
    private final transient ViewContext currentSessionContext;

    /**
     * Constructor of the user serie view.
     *
     * @param currentSessionContext The context of the current session.
     */
    public UserSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext, "Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie.");
        this.currentSessionContext = currentSessionContext;
    }

    @Override
    protected JTable createContentTable(final int age) {
        return super.createSerieTable(age);
    }

    @Override
    protected void showGenreRanking() {
        final List<Genre> genreRanking = ((UserSessionController) currentSessionContext.getController()).getSeriesGenresRanking();
        final StringBuilder message = new StringBuilder("Classifica dei generi:\n\n");
        genreRanking.forEach(g -> {
            message.append(g.name()).append(": ")
                    .append(g.viewNumber()).append(" visual.\n");
        });
        JOptionPane.showMessageDialog(this, message.toString(),
                "Classifica dei generi - Serie", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void showPreferredGenres() {

    }
}
