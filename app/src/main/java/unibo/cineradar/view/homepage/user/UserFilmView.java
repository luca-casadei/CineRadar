package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Film view of the user.
 */
public final class UserFilmView extends UserPanel {
    @Serial
    private static final long serialVersionUID = 6530405035905149718L;

    /**
     * Constructor of the user film view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        // Adds the welcome label to the view
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina dei film.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        // Adds the film table to the view
        final JTable filmTable = super.createFilmTable();
        final JScrollPane scrollPane = new JScrollPane(filmTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
