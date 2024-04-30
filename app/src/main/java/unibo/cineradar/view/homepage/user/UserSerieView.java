package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;

// CHECKSTYLE: MagicNumber OFF

/**
 * Serie view of the user.
 */
public final class UserSerieView extends UserPanel {
    private static final long serialVersionUID = 1L; // TODO: sostituire

    /**
     * Constructor of the user serie view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        final JTable serieTable = super.createMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getSeries()
        );
        final JScrollPane scrollPane = new JScrollPane(serieTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
// CHECKSTYLE: MagicNumber ON
