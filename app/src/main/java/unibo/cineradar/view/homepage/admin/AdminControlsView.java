package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Objects;

public class AdminControlsView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -6223416855940387L;

    public AdminControlsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nel pannello di controllo.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(welcomeLabel, gbc);

        addButton("Aggiungi Film", e -> {
            addFilmDialog();
        }, 1, gbc);
        addButton("Elimina Film", e -> {
            deleteFilmDialog();
        }, 2, gbc);
        addButton("Aggiungi SerieTV", e -> {
            addSeriesDialog();
        }, 3, gbc);
        addButton("Elimina SerieTV", e -> {
            deleteSeriesDialog();
        }, 4, gbc);
    }

    private void addSeriesDialog() {
        final JTextField titleField = new JTextField(20);
        final JTextField ageLimitField = new JTextField(5);
        final JTextArea plotArea = new JTextArea(5, 20);
        final JTextField durationField = new JTextField(5);
        final JTextField episodesNumberField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Titolo:"));
        panel.add(titleField);
        panel.add(new JLabel("Età Limite:"));
        panel.add(ageLimitField);
        panel.add(new JLabel("Trama:"));
        panel.add(new JScrollPane(plotArea));
        panel.add(new JLabel("Durata Complessiva (minuti):"));
        panel.add(durationField);
        panel.add(new JLabel("Numero Episodi:"));
        panel.add(episodesNumberField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi SerieTV",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addSeries(
                    titleField.getText(),
                    Integer.parseInt(ageLimitField.getText()),
                    plotArea.getText(),
                    Integer.parseInt(durationField.getText()),
                    Integer.parseInt(episodesNumberField.getText()));
        }
    }

    private void addSeries(final String title, final int ageLimit, final String plot, final int duration, final int episodesNumber) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addSeries(title, ageLimit, plot, duration, episodesNumber);
    }

    private void addFilmDialog() {
        final JTextField titleField = new JTextField(20);
        final JTextField ageLimitField = new JTextField(5);
        final JTextArea plotArea = new JTextArea(5, 20);
        final JTextField durationField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Titolo:"));
        panel.add(titleField);
        panel.add(new JLabel("Età Limite:"));
        panel.add(ageLimitField);
        panel.add(new JLabel("Trama:"));
        panel.add(new JScrollPane(plotArea));
        panel.add(new JLabel("Durata (minuti):"));
        panel.add(durationField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi Film",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addFilm(
                    titleField.getText(),
                    Integer.parseInt(ageLimitField.getText()),
                    plotArea.getText(),
                    Integer.parseInt(durationField.getText()));
        }
    }

    private void addFilm(final String title, final int ageLimit, final String plot, final int duration) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addFilm(title, ageLimit, plot, duration);
    }

    private void deleteFilmDialog() {
        final String input = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null, "Inserisci il Codice del film da eliminare:", "Elimina Film", JOptionPane.PLAIN_MESSAGE));
        try {
            final int code = Integer.parseInt(input);
            final boolean deleted = deleteFilm(code);
            if (deleted) {
                JOptionPane.showMessageDialog(
                        null, "Il film è stato eliminato con successo.", "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null, "Errore durante l'eliminazione del film.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null, "Inserisci un numero valido per il Codice.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSeriesDialog() {
        final String input = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null, "Inserisci il Codice della SerieTV da eliminare:", "Elimina SerieTV", JOptionPane.PLAIN_MESSAGE));
        try {
            final int code = Integer.parseInt(input);
            final boolean deleted = deleteSeries(code);
            if (deleted) {
                JOptionPane.showMessageDialog(
                        null, "La SerieTV è stata eliminata con successo.", "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null, "Errore durante l'eliminazione della SerieTV.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null, "Inserisci un numero valido per il Codice.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean deleteFilm(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteFilm(code);
    }

    private boolean deleteSeries(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteSeries(code);
    }

    private void addButton(final String text, final ActionListener listener, final int gridY, final GridBagConstraints gbc) {
        final JButton button = new JButton(text);
        button.addActionListener(listener);
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        this.add(button, gbc);
    }
}
