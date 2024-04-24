package unibo.cineradar.view.homepage.registrar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.io.Serial;

/**
 * The component that permits the registrar to add cards to the database.
 */
public final class CardedRegistrationForm extends JPanel {
    @Serial
    private static final long serialVersionUID = 7153975793281845722L;

    /**
     * The constructor of the component.
     */
    public CardedRegistrationForm() {
        this.setLayout(new BorderLayout());
        final JLabel topLabel = new JLabel("Benvenuto, qui puoi registrare gli utenti tesserati al tuo cinema.");
        topLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(topLabel, BorderLayout.NORTH);
    }
}
