package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;
import java.util.Objects;

/**
 * The AdminSerieView class represents the user interface for managing TV series by an administrator.
 * It extends the AdminPanel class and provides functionality to add, delete, and display TV series,
 * seasons, and episodes.
 */
public final class AdminSerieView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -659685094860486L;
    private static final String ERROR = "Errore";
    private static final String COMPLETE_DELETE = "Eliminazione completata";
    private static final String SEASON_NUMBER = "Numero Stagione:";
    private static final String SERIES_CODE = "Codice Serie:";
    private final JTable seriesTable;

    /**
     * Constructs a new AdminSerieView with the specified ViewContext.
     *
     * @param currentSessionContext The ViewContext representing the current session.
     */
    public AdminSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        setLayout(new BorderLayout());
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);
        this.seriesTable = createSerieTable();
        final JScrollPane scrollPane = new JScrollPane(seriesTable);
        add(scrollPane, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addSeriesButton = new JButton("Aggiungi Serie");
        addSeriesButton.addActionListener(e -> addSeriesDialog());
        buttonPanel.add(addSeriesButton);
        final JButton deleteSeriesButton = new JButton("Elimina Serie");
        deleteSeriesButton.addActionListener(e -> deleteSeriesDialog());
        buttonPanel.add(deleteSeriesButton);
        final JButton addSeasonButton = new JButton("Aggiungi Stagione");
        addSeasonButton.addActionListener(e -> addSeasonDialog());
        buttonPanel.add(addSeasonButton);
        final JButton deleteSeasonButton = new JButton("Elimina Stagione");
        deleteSeasonButton.addActionListener(e -> deleteSeasonDialog());
        buttonPanel.add(deleteSeasonButton);
        final JButton addEpisodeButton = new JButton("Aggiungi Episodio");
        addEpisodeButton.addActionListener(e -> addEpisodeDialog());
        buttonPanel.add(addEpisodeButton);
        final JButton deleteEpisodeButton = new JButton("Elimina Episodio");
        deleteEpisodeButton.addActionListener(e -> deleteEpisodeDialog());
        buttonPanel.add(deleteEpisodeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the table displaying TV series with the latest data.
     */
    private void updateSeriesTable() {
        final DefaultTableModel model = (DefaultTableModel) this.seriesTable.getModel();
        model.setRowCount(0);
        for (final Serie serie : ((AdminSessionController) getCurrentSessionContext().getController()).getSeries()) {
            model.addRow(new Object[]{
                    serie.getSeriesId(),
                    serie.getTitle(),
                    serie.getAgeLimit(),
                    serie.getPlot(),
                    serie.getDuration()});
        }
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
        updateSeriesTable();
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
                updateSeriesTable();
                JOptionPane.showMessageDialog(
                        null,
                        "La SerieTV è stata eliminata con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione della SerieTV.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci un numero valido per il Codice.",
                    ERROR, JOptionPane.ERROR_MESSAGE);
        }
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
     * Displays a dialog for adding a season to a TV series.
     * The dialog prompts the administrator to enter the series code, season number, summary, and cast ID.
     */
    private void addSeasonDialog() {
        final JTextField seriesCodeField = new JTextField(5);
        final JTextField seasonNumberField = new JTextField(5);
        final JTextArea summaryArea = new JTextArea(5, 20);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(SERIES_CODE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(SEASON_NUMBER));
        panel.add(seasonNumberField);
        panel.add(new JLabel("Sunto:"));
        panel.add(new JScrollPane(summaryArea));

        final int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi Stagione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addSeason(
                    Integer.parseInt(seriesCodeField.getText()),
                    Integer.parseInt(seasonNumberField.getText()),
                    summaryArea.getText());
        }
    }

    /**
     * Adds a season to a TV series with the specified details.
     *
     * @param seriesCode    The code of the TV series to which the season belongs.
     * @param seasonNumber  The number of the season.
     * @param summary       The summary of the season.
     */
    private void addSeason(
            final int seriesCode, final int seasonNumber, final String summary) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addSeason(seriesCode, seasonNumber, summary);
    }

    /**
     * Displays a dialog for deleting a season from a TV series.
     * The dialog prompts the administrator to enter the series code and season number.
     */
    private void deleteSeasonDialog() {
        final JTextField seriesCodeField = new JTextField(5);
        final JTextField seasonNumberField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(SERIES_CODE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(SEASON_NUMBER));
        panel.add(seasonNumberField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Elimina Stagione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            final boolean deleted = deleteSeason(
                    Integer.parseInt(seriesCodeField.getText()),
                    Integer.parseInt(seasonNumberField.getText()));

            if (deleted) {
                JOptionPane.showMessageDialog(
                        null,
                        "La stagione è stata eliminata con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione della stagione o la stagione non esiste.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes a season from a TV series with the specified series code and season number.
     *
     * @param seriesCode    The code of the TV series from which the season will be deleted.
     * @param seasonNumber  The number of the season to be deleted.
     * @return True if the season was successfully deleted, false otherwise.
     */
    private boolean deleteSeason(final int seriesCode, final int seasonNumber) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteSeason(seriesCode, seasonNumber);
    }

    /**
     * Displays a dialog for adding an episode to a TV series season.
     * The dialog prompts the administrator to enter the series code, season number, episode number, and duration.
     */
    private void addEpisodeDialog() {
        final JTextField seriesCodeField = new JTextField(5);
        final JTextField seasonNumberField = new JTextField(5);
        final JTextField episodeNumberField = new JTextField(5);
        final JTextField durationField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(SERIES_CODE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(SEASON_NUMBER));
        panel.add(seasonNumberField);
        panel.add(new JLabel("Numero Episodio:"));
        panel.add(episodeNumberField);
        panel.add(new JLabel("Durata (minuti):"));
        panel.add(durationField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi Episodio",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addEpisode(
                    Integer.parseInt(seriesCodeField.getText()),
                    Integer.parseInt(seasonNumberField.getText()),
                    Integer.parseInt(episodeNumberField.getText()),
                    Integer.parseInt(durationField.getText()));
        }
    }

    /**
     * Adds an episode to a TV series season with the specified details.
     *
     * @param seriesCode      The code of the TV series to which the episode belongs.
     * @param seasonNumber    The number of the season to which the episode belongs.
     * @param episodeNumber   The number of the episode.
     * @param duration        The duration of the episode in minutes.
     */
    private void addEpisode(
            final int seriesCode, final int seasonNumber, final int episodeNumber, final int duration) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addEpisode(seriesCode, seasonNumber, episodeNumber, duration);
    }

    /**
     * Displays a dialog for deleting an episode from a TV series season.
     * The dialog prompts the administrator to enter the series code, season number, and episode number.
     */
    private void deleteEpisodeDialog() {
        final JTextField seriesCodeField = new JTextField(5);
        final JTextField seasonNumberField = new JTextField(5);
        final JTextField episodeNumberField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(SERIES_CODE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(SEASON_NUMBER));
        panel.add(seasonNumberField);
        panel.add(new JLabel("Numero Episodio:"));
        panel.add(episodeNumberField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Elimina Episodio",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            final boolean deleted = deleteEpisode(
                    Integer.parseInt(seriesCodeField.getText()),
                    Integer.parseInt(seasonNumberField.getText()),
                    Integer.parseInt(episodeNumberField.getText()));

            if (deleted) {
                JOptionPane.showMessageDialog(
                        null,
                        "L'episodio è stato eliminato con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione dell'episodio o l'episodio non esiste.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes an episode from a TV series season with the specified details.
     *
     * @param seriesCode      The code of the TV series from which the episode will be deleted.
     * @param seasonNumber    The number of the season from which the episode will be deleted.
     * @param episodeNumber   The number of the episode to be deleted.
     * @return True if the episode was successfully deleted, false otherwise.
     */
    private boolean deleteEpisode(final int seriesCode, final int seasonNumber, final int episodeNumber) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteEpisode(seriesCode, seasonNumber, episodeNumber);
    }
}
