package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;
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
public final class AdminProfileView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -6223416854460075865L;

    /**
     * Constructor of the user profile view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public AdminProfileView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final Account account = currentSessionContext.getController().getAccount();
        if (account instanceof Administrator) {
            final Administrator admin;
            admin = (Administrator) account;
            final JPanel userInfoPanel = this.getUserInfoPanel(admin);
            this.setLayout(new BorderLayout());
            this.add(userInfoPanel, BorderLayout.CENTER);
        }
    }

    private JPanel getUserInfoPanel(final Administrator admin) {
        final JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(0, 1, 10, 10));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final JLabel nameLabel = new JLabel("Nome: " + admin.getName());
        final JLabel surnameLabel = new JLabel("Cognome: " + admin.getLastName());
        final JLabel usernameLabel = new JLabel("Username: " + admin.getUsername());
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        surnameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(surnameLabel);
        userInfoPanel.add(usernameLabel);
        return userInfoPanel;
    }
}

// CHECKSTYLE: MagicNumber ON
