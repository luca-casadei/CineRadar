package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Review view of the user.
 */
public final class UserReviewView extends UserPanel {
    @Serial
    private static final long serialVersionUID = -636784351989220399L;

    /**
     * Constructor of the user review view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserReviewView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        // Adds the welcome label to the view
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle recensioni.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        // Adds the film table to the view
        final JTable reviewTable = super
                .createReviewTable(((UserSessionController) currentSessionContext.getController()).getReviews());
        final JScrollPane scrollPane = new JScrollPane(reviewTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}

// CHECKSTYLE: MagicNumber ON
