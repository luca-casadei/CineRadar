package unibo.cineradar.view.homepage.registrar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.Serial;

/**
 * The form that permits the registrar to see the associated cinema infos.
 */
public final class CardedCinemaPanel extends JPanel {
    private static final int TITLE_FONT_SIZE = 20;
    @Serial
    private static final long serialVersionUID = 6466145857318357871L;

    /**
     * The constructor of the component.
     */
    public CardedCinemaPanel() {
        setLayout(new BorderLayout());
        final JLabel cinemaLabel = new JLabel("CINEMA ASSOCIATO");
        cinemaLabel.setHorizontalAlignment(JLabel.CENTER);
        cinemaLabel.setFont(new Font("Serif", Font.PLAIN, TITLE_FONT_SIZE));
        this.add(cinemaLabel, BorderLayout.NORTH);
    }
}
