package unibo.cineradar.view.homepage.user;

import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * Serie view of the user.
 */
public final class UserSerieView extends UserPanel {
    private static final long serialVersionUID = -2884190954467853020L;

    /**
     * Constructor of the user serie view.
     *
     * @param currentSessionContext The context of the current session.
     */
    public UserSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        final JPanel mainPanel = new JPanel(new BorderLayout());
        final JTable serieTable = super.createSerieTable();
        final JScrollPane scrollPane = new JScrollPane(serieTable);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
