package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;
import java.util.Optional;

/**
 * The AdminSerieView class represents the user interface for managing TV series by an administrator.
 * It extends the AdminPanel class and provides functionality to add, delete, and display TV series,
 * seasons, and episodes.
 */
public final class AdminSerieView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -935418209362487512L;
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
        addSeasonButton.addActionListener(e -> addSeasonDialog(
                Optional.empty()));
        buttonPanel.add(addSeasonButton);
        final JButton deleteSeasonButton = new JButton("Elimina Stagione");
        deleteSeasonButton.addActionListener(e -> deleteSeasonDialog());
        buttonPanel.add(deleteSeasonButton);
        final JButton addEpisodeButton = new JButton("Aggiungi Episodio");
        addEpisodeButton.addActionListener(e -> addEpisodeDialog(
                Optional.empty(),
                Optional.empty()));
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
        ((AdminSessionController) getCurrentSessionContext().getController()).updateDetailedSeries();
        SwingUtilities.invokeLater(() -> {
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
        });
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
        panel.add(new JLabel("Eta' Limite:"));
        panel.add(ageLimitField);
        panel.add(new JLabel("Trama:"));
        panel.add(new JScrollPane(plotArea));
        panel.add(new JLabel("Durata Complessiva (minuti):"));
        panel.add(durationField);
        panel.add(new JLabel("Numero Episodi:"));
        panel.add(episodesNumberField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(titleField.getText())
                    && isFieldFilled(ageLimitField.getText())
                    && isFieldFilled(plotArea.getText())
                    && isFieldFilled(durationField.getText())
                    && isFieldFilled(episodesNumberField.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        titleField.getDocument().addDocumentListener(listener);
        ageLimitField.getDocument().addDocumentListener(listener);
        plotArea.getDocument().addDocumentListener(listener);
        durationField.getDocument().addDocumentListener(listener);
        episodesNumberField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            addSeries(
                    titleField.getText(),
                    Integer.parseInt(ageLimitField.getText()),
                    plotArea.getText(),
                    Integer.parseInt(durationField.getText()),
                    Integer.parseInt(episodesNumberField.getText())
            );
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(this, panel, "Aggiungi Serie",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
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
        addSeasonDialog(Optional.of(
                ((AdminSessionController) this.getCurrentSessionContext().getController())
                        .getLastSeriesId()
        ));
    }

    /**
     * Displays a dialog for deleting a TV series.
     * The dialog prompts the administrator to enter the code of the TV series to be deleted.
     */
    private void deleteSeriesDialog() {
        final JTextField codeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Inserisci il Codice della SerieTV da eliminare:"));
        panel.add(codeField);

        final int result = JOptionPane.showConfirmDialog(this, panel, "Elimina SerieTV",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                final int code = Integer.parseInt(codeField.getText());
                final boolean deleted = deleteSeries(code);
                if (deleted) {
                    updateSeriesTable();
                    JOptionPane.showMessageDialog(
                            this,
                            "La SerieTV è stata eliminata con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Errore durante l'eliminazione della SerieTV.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci un numero valido per il Codice.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
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
     *
     * @param idSeries An optional parameter representing the ID of the series to which the season is being added.
     *                 If present, the series code field will be pre-filled with this ID and made uneditable.
     */
    private void addSeasonDialog(final Optional<Integer> idSeries) {
        final JTextField seriesCodeField = new JTextField(5);
        final JTextField seasonNumberField = new JTextField(5);
        final JTextArea summaryArea = new JTextArea(5, 20);
        final JComboBox<Integer> castBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController()).getCasting()
                        .stream()
                        .map(Casting::id)
                        .toArray(Integer[]::new)
        );

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(SERIES_CODE));
        panel.add(seriesCodeField);
        if (idSeries.isPresent()) {
            seriesCodeField.setText(idSeries.get().toString());
            seriesCodeField.setEditable(false);
        }
        panel.add(new JLabel(SEASON_NUMBER));
        panel.add(seasonNumberField);
        panel.add(new JLabel("Sunto:"));
        panel.add(new JScrollPane(summaryArea));
        panel.add(new JLabel("Id Cast:"));
        panel.add(new JScrollPane(castBox));

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(seriesCodeField.getText())
                    && isFieldFilled(seasonNumberField.getText())
                    && isFieldFilled(summaryArea.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        seriesCodeField.getDocument().addDocumentListener(listener);
        seasonNumberField.getDocument().addDocumentListener(listener);
        summaryArea.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            addSeason(
                    Integer.parseInt(seriesCodeField.getText()),
                    Integer.parseInt(seasonNumberField.getText()),
                    summaryArea.getText(),
                    Integer.parseInt(String.valueOf(castBox.getSelectedItem())));
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(this, panel, "Aggiungi Stagione",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * Adds a season to a TV series with the specified details.
     *
     * @param seriesCode   The code of the TV series to which the season belongs.
     * @param seasonNumber The number of the season.
     * @param summary      The summary of the season.
     * @param idCast       The id of the Cast.
     */
    private void addSeason(
            final int seriesCode, final int seasonNumber, final String summary, final int idCast) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addSeason(seriesCode, seasonNumber, summary, idCast);
        addEpisodeDialog(
                Optional.of(seriesCode),
                Optional.of(
                        ((AdminSessionController) this.getCurrentSessionContext().getController())
                                .getLastSeasonId(seriesCode)
        ));
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

        final int result = JOptionPane.showConfirmDialog(this, panel, "Elimina Stagione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                final int seriesCode = Integer.parseInt(seriesCodeField.getText());
                final int seasonNumber = Integer.parseInt(seasonNumberField.getText());
                final boolean deleted = deleteSeason(seriesCode, seasonNumber);
                if (deleted) {
                    JOptionPane.showMessageDialog(
                            this,
                            "La stagione è stata eliminata con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Errore durante l'eliminazione della stagione o la stagione non esiste.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci numeri validi per il Codice e il Numero Stagione.",
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
     *
     * @param idSeries An optional parameter representing the ID of the series to which the episode belongs.
     *                 If present, the series code field will be pre-filled with this ID and made uneditable.
     * @param idSeason An optional parameter representing the ID of the season to which the episode belongs.
     *                 If present, the season number field will be pre-filled with this ID and made uneditable.
     */
    private void addEpisodeDialog(
            final Optional<Integer> idSeries, final Optional<Integer> idSeason) {
        final JTextField seriesCodeField = new JTextField(5);
        final JTextField seasonNumberField = new JTextField(5);
        final JTextField episodeNumberField = new JTextField(5);
        final JTextField durationField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(SERIES_CODE));
        panel.add(seriesCodeField);
        if (idSeries.isPresent()) {
            seriesCodeField.setText(idSeries.get().toString());
            seriesCodeField.setEditable(false);
        }
        panel.add(new JLabel(SEASON_NUMBER));
        panel.add(seasonNumberField);
        if (idSeason.isPresent()) {
            seasonNumberField.setText(idSeason.get().toString());
            seasonNumberField.setEditable(false);
        }
        panel.add(new JLabel("Numero Episodio:"));
        panel.add(episodeNumberField);
        panel.add(new JLabel("Durata (minuti):"));
        panel.add(durationField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(seriesCodeField.getText())
                    && isFieldFilled(seasonNumberField.getText())
                    && isFieldFilled(episodeNumberField.getText())
                    && isFieldFilled(durationField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        seriesCodeField.getDocument().addDocumentListener(listener);
        seasonNumberField.getDocument().addDocumentListener(listener);
        episodeNumberField.getDocument().addDocumentListener(listener);
        durationField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            addEpisode(
                    Integer.parseInt(seriesCodeField.getText()),
                    Integer.parseInt(seasonNumberField.getText()),
                    Integer.parseInt(episodeNumberField.getText()),
                    Integer.parseInt(durationField.getText()));
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(this, panel, "Aggiungi Episodio",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
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
        updateSeriesTable();
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

        final int result = JOptionPane.showConfirmDialog(this, panel, "Elimina Episodio",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                final int seriesCode = Integer.parseInt(seriesCodeField.getText());
                final int seasonNumber = Integer.parseInt(seasonNumberField.getText());
                final int episodeNumber = Integer.parseInt(episodeNumberField.getText());
                final boolean deleted = deleteEpisode(seriesCode, seasonNumber, episodeNumber);

                if (deleted) {
                    JOptionPane.showMessageDialog(
                            this,
                            "L'episodio è stato eliminato con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Errore durante l'eliminazione dell'episodio o l'episodio non esiste.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci numeri validi per il Codice, il Numero Stagione e il Numero Episodio.",
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

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
