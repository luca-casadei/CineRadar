package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            final JLabel welcomeLabel = new JLabel("Benvenuto "
                    + currentSessionContext.getController().getAccountDetails().get(0)
                    + " nella pagina di profilo.");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            mainPanel.add(welcomeLabel, BorderLayout.NORTH);

            final JPanel userInfoPanel = createUserInfoPanel(user);
            mainPanel.add(userInfoPanel, BorderLayout.CENTER);

            this.setLayout(new BorderLayout());
            this.add(mainPanel, BorderLayout.CENTER);
        }
    }

    private JPanel createUserInfoPanel(final User user) {
        final JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        final JLabel nameLabel = createLabel("Nome: " + user.getName());
        final JLabel surnameLabel = createLabel("Cognome: " + user.getLastName());
        final JLabel usernameLabel = createLabel("Username: " + user.getUsername());
        final JLabel birthDateLabel = createLabel("Data di nascita: " + user.getBirthDate());

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInfoPanel.add(surnameLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInfoPanel.add(birthDateLabel);

        return userInfoPanel;
    }

    private JLabel createLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}

// CHECKSTYLE: MagicNumber ON
