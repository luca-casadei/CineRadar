package unibo.cineradar.view.homepage.registrar;

import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serial;
import java.util.Objects;

/**
 * The component that permits the registrar to add cards to the database.
 */
// CHECKSTYLE: MagicNumber OFF
public final class RegInfoPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 7153975793281845722L;

    /**
     * The constructor of the component.
     *
     * @param ctx The view context.
     */
    public RegInfoPanel(final ViewContext ctx) {
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        final Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
        final Account acc = ctx.getController().getAccount();
        gbc.fill = 1;

        ViewUtilities.setGridBagConstraints(gbc, 0, 0, 2, 1,
                new Insets(0, 0, 50, 0));
        final JLabel title = new JLabel("INFORMAZIONI REGISTRATORE");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        this.add(title, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 1, 1, 1);
        final JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(labelFont);
        this.add(nameLabel, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 1, 1, 1,
                new Insets(0, 50, 0, 0));
        final JLabel nameContent = new JLabel(acc.getName());
        nameContent.setFont(labelFont);
        this.add(nameContent, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 2, 1, 1);
        final JLabel surnameLabel = new JLabel("Cognome:");
        surnameLabel.setFont(labelFont);
        this.add(surnameLabel, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 2, 1, 1,
                new Insets(0, 50, 0, 0));
        final JLabel surnameContent = new JLabel(acc.getLastName());
        surnameContent.setFont(labelFont);
        this.add(surnameContent, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 3, 1, 1);
        final JLabel emailLabel = new JLabel("Email Cinema:");
        emailLabel.setFont(labelFont);
        this.add(emailLabel, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 3, 1, 1,
                new Insets(0, 50, 0, 0));
        if (acc instanceof Registrar) {
            final JLabel emailContent = new JLabel(
                    Objects.isNull(((Registrar) acc).getEmailCinema())
                            ? "NESSUNA" : ((Registrar) acc).getEmailCinema());
            emailContent.setFont(labelFont);
            this.add(emailContent, gbc);
        }

        ViewUtilities.setGridBagConstraints(gbc, 0, 4, 1, 1);
        final JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        this.add(usernameLabel, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 4, 1, 1,
                new Insets(0, 50, 0, 0));
        final JLabel usernameContent = new JLabel(acc.getUsername());
        usernameContent.setFont(labelFont);
        this.add(usernameContent, gbc);
    }
}

// CHECKSTYLE: MagicNumber ON
