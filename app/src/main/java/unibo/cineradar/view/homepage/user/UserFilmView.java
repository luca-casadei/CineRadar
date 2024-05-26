package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.view.ViewContext;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.io.Serial;
import java.util.List;

/**
 * Film view of the user.
 */
public final class UserFilmView extends UserFilteredView {
    @Serial
    private static final long serialVersionUID = 6530405035905149718L;
    private final transient ViewContext currentSessionContext;


    /**
     * Constructor of the user film view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext, "Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina dei film.");
        this.currentSessionContext = currentSessionContext;
    }

    @Override
    protected JTable createContentTable(final int age) {
        return super.createFilmTable(age);
    }

    @Override
    protected void showGenreRanking() {
        final List<Genre> genreRanking = ((UserSessionController) currentSessionContext.getController()).getFilmGenresRanking();
        final StringBuilder message = new StringBuilder("Classifica dei generi:\n\n");
        genreRanking.forEach(g -> {
            message.append(g.name()).append(": ")
                    .append(g.viewNumber()).append(" visual.\n");
        });
        JOptionPane.showMessageDialog(this, message.toString(),
                "Classifica dei generi - Film", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void showPreferredGenres() {
        ((UserSessionController) currentSessionContext.getController()).getUserPrefs();
        //super.filterUserPrefferredGenres();
    }

}
