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

/**
 * This class represents the graphical user interface (GUI) for the administrator controls.
 * It provides functionality for adding and deleting movies and TV series.
 * The GUI includes text fields and buttons for user interaction.
 */
public class AdminControlsView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -6223416855940387L;
    private static final String ERRORE = "Errore";
    private static final int BOTTOM = 20;

    /**
     * Constructs a new AdminControlsView object with the specified current session context.
     *
     * @param currentSessionContext The current session context containing necessary information.
     */
    public AdminControlsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nel pannello di controllo.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, BOTTOM, 0);
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

    /**
     * Displays a dialog for adding a TV series.
     * The dialog prompts the administrator to enter the title, age limit, plot, duration, and number of episodes.
     */
    private void addSeriesDialog() {
        final JTextField titleField = new JTextField(20);
        final JTextField ageLimitField = new JTextField(5);
        final JTextArea plotArea = new JTextArea(5, 20);
        final JTextField durationField = new JTextField(5);
        final JTextField episodesNumberField = new JTextField(5);

        final JPanel panel = new JPanel();
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

        final int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi SerieTV",
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

    /**
     * Adds a TV series with the specified details.
     *
     * @param title          The title of the TV series.
     * @param ageLimit       The age limit for the TV series.
     * @param plot           The plot summary of the TV series.
     * @param duration       The total duration of the TV series in minutes.
     * @param episodesNumber The number of episodes in the TV series.
     */
    private void addSeries(
            final String title, final int ageLimit, final String plot, final int duration, final int episodesNumber) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addSeries(title, ageLimit, plot, duration, episodesNumber);
    }

    /**
     * Displays a dialog for adding a movie.
     * The dialog prompts the administrator to enter the title, age limit, plot, duration, and cast ID.
     */
    private void addFilmDialog() {
        final JTextField titleField = new JTextField(20);
        final JTextField ageLimitField = new JTextField(5);
        final JTextArea plotArea = new JTextArea(5, 20);
        final JTextField durationField = new JTextField(5);
        final JTextField idCastField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Titolo:"));
        panel.add(titleField);
        panel.add(new JLabel("Età Limite:"));
        panel.add(ageLimitField);
        panel.add(new JLabel("Trama:"));
        panel.add(new JScrollPane(plotArea));
        panel.add(new JLabel("Durata (minuti):"));
        panel.add(durationField);
        panel.add(new JLabel("ID Cast:"));
        panel.add(idCastField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi Film",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            addFilm(
                    titleField.getText(),
                    Integer.parseInt(ageLimitField.getText()),
                    plotArea.getText(),
                    Integer.parseInt(durationField.getText()),
                    Integer.parseInt(idCastField.getText()));
        }
    }

    /**
     * Adds a movie with the specified details.
     *
     * @param title     The title of the movie.
     * @param ageLimit  The age limit for the movie.
     * @param plot      The plot summary of the movie.
     * @param duration  The duration of the movie in minutes.
     * @param idCast    The ID of the cast associated with the movie.
     */
    private void addFilm(
            final String title, final int ageLimit, final String plot, final int duration, final int idCast) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addFilm(title, ageLimit, plot, duration, idCast);
    }

    /**
     * Displays a dialog for deleting a movie.
     * The dialog prompts the administrator to enter the code of the movie to be deleted.
     */
    private void deleteFilmDialog() {
        final String input = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci il Codice del film da eliminare:",
                        "Elimina Film", JOptionPane.PLAIN_MESSAGE));
        try {
            final int code = Integer.parseInt(input);
            final boolean deleted = deleteFilm(code);
            if (deleted) {
                JOptionPane.showMessageDialog(
                        null,
                        "Il film è stato eliminato con successo.",
                        "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione del film.",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci un numero valido per il Codice.",
                    ERRORE, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays a dialog for deleting a TV series.
     * The dialog prompts the administrator to enter the code of the TV series to be deleted.
     */
    private void deleteSeriesDialog() {
        final String input = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci il Codice della SerieTV da eliminare:",
                        "Elimina SerieTV", JOptionPane.PLAIN_MESSAGE));
        try {
            final int code = Integer.parseInt(input);
            final boolean deleted = deleteSeries(code);
            if (deleted) {
                JOptionPane.showMessageDialog(
                        null,
                        "La SerieTV è stata eliminata con successo.",
                        "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione della SerieTV.",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci un numero valido per il Codice.",
                    ERRORE, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes the movie with the specified code.
     *
     * @param code The code of the movie to be deleted.
     * @return True if the movie was successfully deleted, false otherwise.
     */
    private boolean deleteFilm(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteFilm(code);
    }

    /**
     * Deletes the TV series with the specified code.
     *
     * @param code The code of the TV series to be deleted.
     * @return True if the TV series was successfully deleted, false otherwise.
     */
    private boolean deleteSeries(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteSeries(code);
    }

    /**
     * Adds a button with the specified text and action listener to the GUI.
     *
     * @param text     The text to be displayed on the button.
     * @param listener The action listener for the button.
     * @param gridY    The grid Y coordinate for the button placement.
     * @param gbc      The GridBagConstraints object for layout configuration.
     */
    private void addButton(
            final String text, final ActionListener listener, final int gridY, final GridBagConstraints gbc) {
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
