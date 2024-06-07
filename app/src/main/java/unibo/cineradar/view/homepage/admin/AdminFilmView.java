package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.Casting;
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
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;

/**
 * The AdminFilmView class represents the user interface for managing films by an administrator.
 * It extends the AdminPanel class and provides functionality to add, delete, and display films.
 */
public final class AdminFilmView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -302785493612487L;
    private static final String ERROR = "Errore";
    private static final String COMPLETE_DELETE = "Eliminazione completata";
    private JTable filmTable;
    private JScrollPane filmScrollPane;

    /**
     * Constructs a new AdminFilmView with the specified ViewContext.
     *
     * @param currentSessionContext The ViewContext representing the current session.
     */
    public AdminFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina dei film.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);
        this.filmTable = createFilmTable();
        this.filmScrollPane = new JScrollPane(filmTable);
        this.add(this.filmScrollPane, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addButton = new JButton("Aggiungi Film");
        addButton.addActionListener(e -> addFilmDialog());
        buttonPanel.add(addButton);
        final JButton deleteButton = new JButton("Elimina Film");
        deleteButton.addActionListener(e -> deleteFilmDialog());
        buttonPanel.add(deleteButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Displays a dialog for adding a movie.
     * The dialog prompts the administrator to enter the title, age limit, plot, duration, and cast ID.
     */
    private void addFilmDialog() {
        if (((AdminSessionController) getCurrentSessionContext().getController()).getCasting().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun casting disponibile",
                    ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JTextField titleField = new JTextField(20);
        final JTextField ageLimitField = new JTextField(5);
        final JTextArea plotArea = new JTextArea(5, 20);
        final JTextField durationField = new JTextField(5);
        final JComboBox<Integer> castBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController()).getCasting()
                        .stream()
                        .map(Casting::id)
                        .toArray(Integer[]::new)
        );

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Titolo:"));
        panel.add(titleField);
        panel.add(new JLabel("Eta' Limite:"));
        panel.add(ageLimitField);
        panel.add(new JLabel("Trama:"));
        panel.add(new JScrollPane(plotArea));
        panel.add(new JLabel("Durata (minuti):"));
        panel.add(durationField);
        panel.add(new JLabel("ID Cast:"));
        panel.add(new JScrollPane(castBox));

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(titleField.getText())
                    && isFieldFilled(ageLimitField.getText())
                    && isFieldFilled(plotArea.getText())
                    && isFieldFilled(durationField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        titleField.getDocument().addDocumentListener(listener);
        ageLimitField.getDocument().addDocumentListener(listener);
        plotArea.getDocument().addDocumentListener(listener);
        durationField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                if (Integer.parseInt(ageLimitField.getText()) < 0
                        || Integer.parseInt(durationField.getText()) < 0) {
                    throw new NumberFormatException();
                }
                addFilm(
                        titleField.getText(),
                        Integer.parseInt(ageLimitField.getText()),
                        plotArea.getText(),
                        Integer.parseInt(durationField.getText()),
                        Integer.parseInt(String.valueOf(castBox.getSelectedItem())));
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire un Numero Valido in Eta' e Durata",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Film",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
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
        updateFilmTable();
    }

    /**
     * Displays a dialog for deleting a movie.
     * The dialog prompts the administrator to enter the code of the movie to be deleted.
     */
    private void deleteFilmDialog() {
        final JTextField codeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Inserisci il Codice del film da eliminare:"));
        panel.add(codeField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Elimina Film",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                final int code = Integer.parseInt(codeField.getText());
                final boolean deleted = deleteFilm(code);
                if (deleted) {
                    updateFilmTable();
                    JOptionPane.showMessageDialog(
                            null,
                            "Il film e' stato eliminato con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Errore durante l'eliminazione del film.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Inserisci un numero valido per il Codice.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
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

    private void updateFilmTable() {
        remove(this.filmScrollPane);
        ((AdminSessionController) getCurrentSessionContext().getController()).updateDetailedFilms();
        this.filmTable = super.createFilmTable();
        this.filmScrollPane = new JScrollPane(this.filmTable);
        add(this.filmScrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
