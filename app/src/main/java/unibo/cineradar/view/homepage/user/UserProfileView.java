package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Profile view of the user.
 */
public final class UserProfileView extends UserPanel {
    @Serial
    private static final long serialVersionUID = -6223416854460075865L;

    /**
     * Constructor of the user profile view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserProfileView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        // Get user information from the context
        final Account account = currentSessionContext.getController().getAccount();
        if (account instanceof User) {
            final User user;
            user = (User) account;
            // Create panel for user information
            final JPanel userInfoPanel = new JPanel();
            userInfoPanel.setLayout(new GridLayout(0, 1, 10, 10));
            userInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Create labels for user information
            final JLabel welcomeLabel = new JLabel("Benvenuto "
                    + currentSessionContext.getController().getAccountDetails().get(0)
                    + " nella pagina dei film.");
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

            final JLabel nameLabel = new JLabel("Nome: " + user.getName());
            final JLabel surnameLabel = new JLabel("Cognome: " + user.getLastName());
            final JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
            final JLabel birthDateLabel = new JLabel("Data di nascita: " + user.getBirthDate());
            // Add labels to the panel
            userInfoPanel.add(welcomeLabel);
            userInfoPanel.add(nameLabel);
            userInfoPanel.add(surnameLabel);
            userInfoPanel.add(usernameLabel);
            userInfoPanel.add(birthDateLabel);

            // Add user information panel to the center of the view
            this.setLayout(new BorderLayout());
            this.add(userInfoPanel, BorderLayout.CENTER);
        }
    }
}

// CHECKSTYLE: MagicNumber ON
