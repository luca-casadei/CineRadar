// CPD-OFF
package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

/**
 * Serie view of the user.
 */
public final class UserSerieView extends UserPanel {
    private static final long serialVersionUID = -2884190954467853020L;

    private JTable serieTable;
    private JScrollPane scrollPane;

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
        this.add(welcomeLabel, BorderLayout.NORTH);

        if (currentSessionContext.getController().getAccount() instanceof User user) {
            serieTable = super.createSerieTable(user.getAge());
        }
        scrollPane = new JScrollPane(serieTable);
        this.add(scrollPane, BorderLayout.CENTER);

        final JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        final JCheckBox filterCheckbox = new JCheckBox("Filtra per eta'");
        filterPanel.add(filterCheckbox);

        final JTextArea ageTextArea = new JTextArea(1, 10);
        ageTextArea.setEnabled(false);
        filterPanel.add(new JLabel("Eta' limite:"));
        filterPanel.add(ageTextArea);

        final JButton filterButton = new JButton("Applica filtro");
        filterButton.setEnabled(false);
        filterPanel.add(filterButton);

        this.add(filterPanel, BorderLayout.SOUTH);

        filterCheckbox.addActionListener(e -> {
            final boolean selected = filterCheckbox.isSelected();
            ageTextArea.setEnabled(selected);
            filterButton.setEnabled(selected);

            if (!selected) {
                resetTable(currentSessionContext);
            }
        });

        filterButton.addActionListener(e -> {
            final String text = ageTextArea.getText();
            if (!text.isEmpty() && filterCheckbox.isSelected()) {
                try {
                    final int ageLimit = Integer.parseInt(text);
                    applyAgeFilter(ageLimit);
                } catch (NumberFormatException ignored) {
                }
            }
        });
    }

    private void applyAgeFilter(final int ageLimit) {
        this.remove(scrollPane);
        serieTable = super.createSerieTable(ageLimit);
        scrollPane = new JScrollPane(serieTable);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void resetTable(final ViewContext currentSessionContext) {
        this.remove(scrollPane);
        if (currentSessionContext.getController().getAccount() instanceof User user) {
            serieTable = super.createSerieTable(user.getAge());
        }
        scrollPane = new JScrollPane(serieTable);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
// CPD-ON
