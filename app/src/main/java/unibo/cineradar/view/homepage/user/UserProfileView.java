package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
        final Account account = currentSessionContext.getController().getAccount();
        if (account instanceof User user) {

            final JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

            final JLabel welcomeLabel = new JLabel("Benvenuto "
                    + currentSessionContext.getController().getAccountDetails().get(0)
                    + " nella pagina di profilo.");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            mainPanel.add(welcomeLabel, BorderLayout.NORTH);

            final JPanel userInfoPanel = createUserInfoPanel(user);
            mainPanel.add(userInfoPanel, BorderLayout.CENTER);

            add(mainPanel);
        }
    }

    private JPanel createUserInfoPanel(final User user) {
        final JPanel userInfoPanel = new JPanel(new GridLayout(0, 1, 0, 0));

        final JLabel nameLabel = new JLabel("Nome: " + user.getName());
        final JLabel surnameLabel = new JLabel("Cognome: " + user.getLastName());
        final JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        final JLabel birthDateLabel = new JLabel("Data di nascita: " + user.getBirthDate());

        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        surnameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        birthDateLabel.setHorizontalAlignment(JLabel.CENTER);

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(surnameLabel);
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(birthDateLabel);

        return userInfoPanel;
    }
}

// CHECKSTYLE: MagicNumber ON

