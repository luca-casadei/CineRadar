package unibo.cineradar.view.homepage.admin;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.io.Serial;

/**
 * Welcome panel with a welcome message for the admin.
 */
public class AdminWelcomeView extends JPanel {
    @Serial
    private static final long serialVersionUID = 922337203685477L;
    private static final int FONT_SIZE = 30;

    /**
     * Constructs an instance of AdminWelcomeView.
     */
    public AdminWelcomeView() {
        this.setLayout(new BorderLayout());
        final JLabel welcomeLabel = new JLabel("Benvenuto nel sistema di amministrazione di CineRadar!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        this.add(welcomeLabel, BorderLayout.CENTER);
    }
}
