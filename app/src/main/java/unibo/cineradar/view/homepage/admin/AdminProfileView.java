package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.view.ViewContext;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Profile view of the user.
 */
public final class AdminProfileView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -7108264925323091345L;
    private static final String ERROR = "Errore";
    private static final String DATABASE_ERROR = "Errore del database: ";
    private static final String COMPLETE_DELETE = "Eliminazione completata";

    /**
     * Constructor of the user profile view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public AdminProfileView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final Account account = currentSessionContext.getController().getAccount();
        if (account instanceof Administrator admin) {
            final JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            final JLabel welcomeLabel = new JLabel("Benvenuto "
                    + currentSessionContext.getController().getAccountDetails().get(0)
                    + " nella pagina di profilo.");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            mainPanel.add(welcomeLabel, BorderLayout.NORTH);
            final JPanel userInfoPanel = createAdminInfoPanel(admin);
            mainPanel.add(userInfoPanel, BorderLayout.CENTER);
            this.setLayout(new BorderLayout());
            this.add(mainPanel, BorderLayout.CENTER);
            final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            final JButton addGenreButton = new JButton("Aggiungi Genere");
            addGenreButton.addActionListener(e -> addGenreDialog());
            buttonPanel.add(addGenreButton);
            final JButton deleteGenreButton = new JButton("Elimina Genere");
            deleteGenreButton.addActionListener(e -> deleteGenreDialog());
            buttonPanel.add(deleteGenreButton);
            this.add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    /**
     * Updates the panel.
     * This method does not trigger any specific updates but can be overridden to provide custom update behavior.
     */
    @Override
    public void updatePanel() {
        // Implement custom update behavior here if needed
    }

    private void addGenreDialog() {
        final JTextField nameField = new JTextField(20);
        final JTextArea descArea = new JTextArea(5, 20);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Genere:"));
        panel.add(nameField);
        panel.add(new JLabel("Descrizione:"));
        panel.add(new JScrollPane(descArea));

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(nameField.getText())
                    && isFieldFilled(descArea.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        nameField.getDocument().addDocumentListener(listener);
        descArea.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                if (containsNumbers(nameField.getText())) {
                    throw new NumberFormatException();
                }
                addGenre(
                        nameField.getText(),
                        descArea.getText());
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire un Genere Valido",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Genere",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addGenre(final String genre, final String description) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addGenre(genre, description);
    }

    private void deleteGenreDialog() {
        final JTextField nameField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Inserisci il Nome del Genere da eliminare:"));
        panel.add(nameField);

        final int result = JOptionPane.showConfirmDialog(null, panel, "Elimina Genere",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                final String genre = nameField.getText();
                if (containsNumbers(genre)) {
                    throw new NumberFormatException();
                }
                final boolean deleted = deleteGenre(genre);
                if (deleted) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Il Genere e' stato eliminato con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                    deleteMultimediaEmptyGenres();
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Inserisci un Genere Presente.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Inserisci un Genere Valido.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean deleteGenre(final String genre) {
        return ((AdminSessionController) this.getCurrentSessionContext().getController())
                .deleteGenre(genre);
    }

    private void deleteMultimediaEmptyGenres() {
        for (final Film film
                : ((AdminSessionController) getCurrentSessionContext().getController())
                .getFilms()) {
            if (((AdminSessionController) getCurrentSessionContext().getController())
                    .isEmptyGenreFilm(film.getFilmId())) {
                ((AdminSessionController) getCurrentSessionContext().getController())
                        .deleteFilm(film.getFilmId());
            }
        }
        for (final Serie serie
                : ((AdminSessionController) getCurrentSessionContext().getController())
                .getSeries()) {
            if (((AdminSessionController) getCurrentSessionContext().getController())
                    .isEmptyGenreSeries(serie.getSeriesId())) {
                ((AdminSessionController) getCurrentSessionContext().getController())
                        .deleteSeries(serie.getSeriesId());
            }
        }
    }

    private JPanel createAdminInfoPanel(final Administrator admin) {
        final JPanel adminInfoPanel = new JPanel();
        adminInfoPanel.setLayout(new BoxLayout(adminInfoPanel, BoxLayout.Y_AXIS));
        adminInfoPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        final JLabel adminNameLabel = createLabel("Nome: " + admin.getName());
        final JLabel adminSurnameLabel = createLabel("Cognome: " + admin.getLastName());
        final JLabel adminUsernameLabel = createLabel("Username: " + admin.getUsername());
        final JLabel adminPhoneNumberLabel = createLabel("Telefono: " + admin.getPhoneNumber());

        adminInfoPanel.add(adminNameLabel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        adminInfoPanel.add(adminSurnameLabel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        adminInfoPanel.add(adminUsernameLabel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        adminInfoPanel.add(adminPhoneNumberLabel);

        return adminInfoPanel;
    }

    private JLabel createLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }

    private static boolean containsNumbers(final String text) {
        return text.matches(".*\\d.*");
    }
}

// CHECKSTYLE: MagicNumber ON
