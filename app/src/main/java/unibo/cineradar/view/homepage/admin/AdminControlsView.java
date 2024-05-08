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
import java.time.LocalDate;
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
    private static final String ELIMINAZIONE_COMPLETATA = "Eliminazione completata";
    private static final String NUMERO_STAGIONE = "Numero Stagione:";
    private static final String CODICE_SERIE = "Codice Serie:";
    private static final int GRID_Y = 5;
    private static final int GRID_Y1 = 6;
    private static final int GRID_Y2 = 7;
    private static final int GRID_Y3 = 9;
    private static final int GRID_Y4 = 8;
    private static final int GRID_Y5 = 10;

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
        addButton("Aggiungi Membro Cast", e -> {
            addCastMemberDialog();
        }, GRID_Y, gbc);
        addButton("Elimina Membro Cast", e -> {
            deleteCastMemberDialog();
        }, GRID_Y1, gbc);
        addButton("Aggiungi Stagione", e -> {
            addSeasonDialog();
        }, GRID_Y2, gbc);
        addButton("Elimina Stagione", e -> {
            deleteSeasonDialog();
        }, GRID_Y4, gbc);
        addButton("Aggiungi Episodio", e -> {
            addEpisodeDialog();
        }, GRID_Y3, gbc);
        addButton("Elimina Episodio", e -> {
            deleteEpisodeDialog();
        }, GRID_Y5, gbc);
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
                        ELIMINAZIONE_COMPLETATA, JOptionPane.INFORMATION_MESSAGE);
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
                        ELIMINAZIONE_COMPLETATA, JOptionPane.INFORMATION_MESSAGE);
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
     * Displays a dialog for adding a cast member.
     * The dialog prompts the administrator to enter the details of the cast member.
     */
    private void addCastMemberDialog() {
        final JTextField nameField = new JTextField(20);
        final JTextField surnameField = new JTextField(20);
        final JTextField birthdayField = new JTextField(10);
        final JTextField actorField = new JTextField(5);
        final JTextField directorField = new JTextField(5);
        final JTextField dateDebutCareerField = new JTextField(10);
        final JTextField artNameField = new JTextField(30);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Cognome:"));
        panel.add(surnameField);
        panel.add(new JLabel("Data di Nascita (YYYY-MM-DD):"));
        panel.add(birthdayField);
        panel.add(new JLabel("É un Attore? (1 se Attore, altrimenti 0):"));
        panel.add(actorField);
        panel.add(new JLabel("É un Regista? (1 se Regista, altrimenti 0):"));
        panel.add(directorField);
        panel.add(new JLabel("Data Debutto Carriera (YYYY-MM-DD):"));
        panel.add(dateDebutCareerField);
        panel.add(new JLabel("Nome d'Arte:"));
        panel.add(artNameField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi Membro Cast",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addCastMember(
                    nameField.getText(),
                    surnameField.getText(),
                    LocalDate.parse(birthdayField.getText()),
                    Integer.parseInt(actorField.getText()),
                    Integer.parseInt(directorField.getText()),
                    LocalDate.parse(dateDebutCareerField.getText()),
                    artNameField.getText());
        }
    }

    /**
     * Adds a cast member with the specified details.
     *
     * @param name               The first name of the cast member.
     * @param surname            The last name of the cast member.
     * @param birthday           The date of birth of the cast member (in format YYYY-MM-DD).
     * @param isActor            The type of actor (1 for true, 0 for false).
     * @param isDirector         The type of director (1 for true, 0 for false).
     * @param dateDebutCareer    The career debut date of the cast member (in format YYYY-MM-DD).
     * @param artName           The stage name or artistic name of the cast member.
     */
    private void addCastMember(
            final String name, final String surname, final LocalDate birthday,
            final int isActor, final int isDirector,
            final LocalDate dateDebutCareer, final String artName) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addCastMember(name, surname, birthday, isActor, isDirector, dateDebutCareer, artName);
    }

    /**
     * Displays a dialog for deleting a cast member.
     * The dialog prompts the administrator to enter the code of the cast member to be deleted.
     */
    private void deleteCastMemberDialog() {
        final String input = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci il Codice del Membro del Cast da eliminare:",
                        "Elimina Membro Cast", JOptionPane.PLAIN_MESSAGE));
        try {
            final int code = Integer.parseInt(input);
            final boolean deleted = deleteCastMember(code);
            if (deleted) {
                JOptionPane.showMessageDialog(
                        null,
                        "Il Membro del Cast è stato eliminato con successo.",
                        ELIMINAZIONE_COMPLETATA, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione del Membro del Cast.",
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
     * Deletes the cast member with the specified code.
     *
     * @param code The code of the cast member to be deleted.
     * @return True if the cast member was successfully deleted, false otherwise.
     */
    private boolean deleteCastMember(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteCastMember(code);
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
        panel.add(new JLabel(CODICE_SERIE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(NUMERO_STAGIONE));
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
        panel.add(new JLabel(CODICE_SERIE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(NUMERO_STAGIONE));
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
                        ELIMINAZIONE_COMPLETATA, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione della stagione o la stagione non esiste.",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
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
        panel.add(new JLabel(CODICE_SERIE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(NUMERO_STAGIONE));
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
        panel.add(new JLabel(CODICE_SERIE));
        panel.add(seriesCodeField);
        panel.add(new JLabel(NUMERO_STAGIONE));
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
                        ELIMINAZIONE_COMPLETATA, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione dell'episodio o l'episodio non esiste.",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
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
