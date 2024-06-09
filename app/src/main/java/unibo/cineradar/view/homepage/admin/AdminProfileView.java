package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.view.ViewContext;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Profile view of the user.
 */
public final class AdminProfileView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -7108264925073091345L;

    /**
     * Constructor of the user profile view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public AdminProfileView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final Account account = currentSessionContext.getController().getAccount();
        if (account instanceof Administrator admin) {
            final JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            final JLabel welcomeLabel = new JLabel("Benvenuto "
                    + currentSessionContext.getController().getAccountDetails().get(0)
                    + " nella pagina di profilo.");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            mainPanel.add(welcomeLabel, BorderLayout.NORTH);

            final JPanel userInfoPanel = createAdminInfoPanel(admin);
            mainPanel.add(userInfoPanel, BorderLayout.CENTER);

            this.setLayout(new BorderLayout());
            this.add(mainPanel, BorderLayout.CENTER);
        }
    }

    /**
     * Updates the panel.
     * This method does not trigger any specific updates but can be overridden to provide custom update behavior.
     */
    @Override
    public void updatePanel() {
        // Implement custom update behavior here if needed
    }

    private JPanel createAdminInfoPanel(final Administrator admin) {
        final JPanel adminInfoPanel = new JPanel();
        adminInfoPanel.setLayout(new BoxLayout(adminInfoPanel, BoxLayout.Y_AXIS));
        adminInfoPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        final JLabel adminNameLabel = createLabel("Nome: " + admin.getName());
        final JLabel adminSurnameLabel = createLabel("Cognome: " + admin.getLastName());
        final JLabel adminUsernameLabel = createLabel("Username: " + admin.getUsername());
        final JLabel adminPhoneNumberLabel = createLabel("Telefono: " + admin.getPhoneNumber());

        adminInfoPanel.add(adminNameLabel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        adminInfoPanel.add(adminSurnameLabel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        adminInfoPanel.add(adminUsernameLabel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        adminInfoPanel.add(adminPhoneNumberLabel);

        return adminInfoPanel;
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
