package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The AdminCastView class represents the user interface for managing cast members and casts by an administrator.
 * It extends the AdminPanel class and provides functionality to add, delete, and display cast members and casts.
 */
public class AdminCastView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -294859604893850L;
    private static final String ERROR = "Errore";
    private static final String COMPLETE_DELETE = "Eliminazione completata";
    private static final String CANCEL = "Cancel";
    private final JTable castMemberTable;
    private final JTable castTable;

    /**
     * Constructs a new AdminCastView with the specified ViewContext.
     *
     * @param currentSessionContext The ViewContext representing the current session.
     */
    public AdminCastView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        setLayout(new GridBagLayout());
        this.castMemberTable = createCastMemberTable();
        final JScrollPane castMemberScrollPane = new JScrollPane(castMemberTable);
        this.castTable = createCastTable();
        final JScrollPane castScrollPane = new JScrollPane(castTable);

        final GridBagConstraints castMemberConstraints = new GridBagConstraints();
        castMemberConstraints.gridx = 1;
        castMemberConstraints.gridy = 0;
        castMemberConstraints.weightx = 0.5;
        castMemberConstraints.weighty = 1.0;
        castMemberConstraints.fill = GridBagConstraints.BOTH;
        add(castMemberScrollPane, castMemberConstraints);

        final GridBagConstraints castConstraints = new GridBagConstraints();
        castConstraints.gridx = 0;
        castConstraints.gridy = 0;
        castConstraints.weightx = 0.5;
        castConstraints.weighty = 1.0;
        castConstraints.fill = GridBagConstraints.BOTH;
        add(castScrollPane, castConstraints);

        final JPanel buttonPanel = getButtonPanel();

        final GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, buttonConstraints);
    }

    private JPanel getButtonPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addMemberButton = new JButton("Aggiungi Membro Cast");
        addMemberButton.addActionListener(e -> addCastMemberDialog());
        buttonPanel.add(addMemberButton);
        final JButton deleteMemberButton = new JButton("Elimina Membro Cast");
        deleteMemberButton.addActionListener(e -> deleteCastMemberDialog());
        buttonPanel.add(deleteMemberButton);
        final JButton addCastButton = new JButton("Aggiungi Cast");
        addCastButton.addActionListener(e -> addCastDialog());
        buttonPanel.add(addCastButton);
        final JButton deleteCastButton = new JButton("Elimina Cast");
        deleteCastButton.addActionListener(e -> deleteCastDialog());
        buttonPanel.add(deleteCastButton);
        final JButton addCastMemberToCastButton = new JButton("Aggiungi Membro Cast ad un Cast");
        addCastMemberToCastButton.addActionListener(e -> addCastMemberToCastDialog());
        buttonPanel.add(addCastMemberToCastButton);
        final JButton deleteCastMemberToCastButton = new JButton("Elimina Membro Cast da un Cast");
        deleteCastMemberToCastButton.addActionListener(e -> deleteCastMemberToCastDialog());
        buttonPanel.add(deleteCastMemberToCastButton);
        return buttonPanel;
    }

    /**
     * Updates the table displaying cast members with the latest data.
     */
    private void updateMemberCastTable() {
        final DefaultTableModel model = (DefaultTableModel) this.castMemberTable.getModel();
        model.setRowCount(0);
        for (final CastMember castMember
                : ((AdminSessionController) getCurrentSessionContext().getController()).getCastMembers()) {
            model.addRow(new Object[]{
                    castMember.getId(),
                    castMember.getName(),
                    castMember.getLastName(),
                    castMember.getBirthDate(),
                    castMember instanceof Actor ? "Attore" : "Regista",
                    castMember.getCareerDebutDate(),
                    castMember.getStageName()
            });
        }
    }

    /**
     * Updates the table displaying casts with the latest data.
     */
    private void updateCastTable() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel model = (DefaultTableModel) this.castTable.getModel();
            model.setRowCount(0);
            for (final Casting casting
                    : ((AdminSessionController) getCurrentSessionContext().getController()).getCasting()) {
                model.addRow(new Object[]{
                        casting.id(),
                        casting.name(),
                });
            }
        });
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
        panel.add(new JLabel("E' un Attore? (1 se Attore, altrimenti 0):"));
        panel.add(actorField);
        panel.add(new JLabel("E' un Regista? (1 se Regista, altrimenti 0):"));
        panel.add(directorField);
        panel.add(new JLabel("Data Debutto Carriera (YYYY-MM-DD):"));
        panel.add(dateDebutCareerField);
        panel.add(new JLabel("Nome d'Arte:"));
        panel.add(artNameField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(nameField.getText())
                    && isFieldFilled(surnameField.getText())
                    && isFieldFilled(birthdayField.getText())
                    && isFieldFilled(actorField.getText())
                    && isFieldFilled(directorField.getText())
                    && isFieldFilled(dateDebutCareerField.getText())
                    && isFieldFilled(artNameField.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        nameField.getDocument().addDocumentListener(listener);
        surnameField.getDocument().addDocumentListener(listener);
        birthdayField.getDocument().addDocumentListener(listener);
        actorField.getDocument().addDocumentListener(listener);
        directorField.getDocument().addDocumentListener(listener);
        dateDebutCareerField.getDocument().addDocumentListener(listener);
        artNameField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            addCastMember(
                    nameField.getText(),
                    surnameField.getText(),
                    LocalDate.parse(birthdayField.getText()),
                    Integer.parseInt(actorField.getText()),
                    Integer.parseInt(directorField.getText()),
                    LocalDate.parse(dateDebutCareerField.getText()),
                    artNameField.getText());
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Membro Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
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
        updateMemberCastTable();
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
                updateMemberCastTable();
                JOptionPane.showMessageDialog(
                        null,
                        "Il Membro del Cast è stato eliminato con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione del Membro del Cast.",
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
     * Deletes the cast member with the specified code.
     *
     * @param code The code of the cast member to be deleted.
     * @return True if the cast member was successfully deleted, false otherwise.
     */
    private boolean deleteCastMember(final int code) {
        return ((AdminSessionController) this.getCurrentSessionContext().getController())
                .deleteCastMember(code);
    }

    /**
     * Displays a dialog for adding a cast.
     * The dialog prompts the administrator to enter the details of the cast.
     */
    private void addCastDialog() {
        final JTextField nameField = new JTextField(20);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nome del Cast:"));
        panel.add(nameField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(nameField.getText());
            okButton.setEnabled(isFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        nameField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            addCast(nameField.getText());
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    /**
     * Adds a cast with the specified details.
     *
     * @param name        The name of the cast.
     */
    private void addCast(
            final String name) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addCast(name);
        updateCastTable();
    }

    /**
     * Displays a dialog for deleting a cast.
     * The dialog prompts the administrator to enter the ID of the cast to be deleted.
     */
    private void deleteCastDialog() {
        final String input = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci l'ID del Cast da eliminare:",
                        "Elimina Cast", JOptionPane.PLAIN_MESSAGE));
        try {
            final int id = Integer.parseInt(input);
            final boolean deleted = deleteCast(id);
            if (deleted) {
                updateCastTable();
                JOptionPane.showMessageDialog(
                        null,
                        "Il Cast è stato eliminato con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione del Cast.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci un numero valido per l'ID.",
                    ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes the cast with the specified ID.
     *
     * @param id The ID of the cast to be deleted.
     * @return True if the cast was successfully deleted, false otherwise.
     */
    private boolean deleteCast(final int id) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteCast(id);
    }

    private void addCastMemberToCastDialog() {
        final JTextField castMemberCodeField = new JTextField(5);
        final JTextField castCodeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Codice del Cast:"));
        panel.add(castCodeField);
        panel.add(new JLabel("Codice del Membro:"));
        panel.add(castMemberCodeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(castCodeField.getText())
                    && isFieldFilled(castMemberCodeField.getText());
            okButton.setEnabled(isFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        castCodeField.getDocument().addDocumentListener(listener);
        castMemberCodeField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            addCastMemberToCast(
                    Integer.parseInt(castMemberCodeField.getText()),
                    Integer.parseInt(castCodeField.getText()));
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi MembroCast ad un Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addCastMemberToCast(final int castMemberCode, final int castCode) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addCastMemberToCast(castMemberCode, castCode);
        updateCasting();
    }

    private void deleteCastMemberToCastDialog() {
        final JTextField castCodeField = new JTextField(5);
        final JTextField castMemberCodeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Codice del Membro:"));
        panel.add(castMemberCodeField);
        panel.add(new JLabel("Codice del Cast:"));
        panel.add(castCodeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(castCodeField.getText())
                    && isFieldFilled(castMemberCodeField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        castMemberCodeField.getDocument().addDocumentListener(listener);
        castCodeField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            final int castMemberCode = Integer.parseInt(castMemberCodeField.getText());
            final int castCode = Integer.parseInt(castCodeField.getText());
            final boolean deleted = deleteCastMemberToCast(castMemberCode, castCode);
            if (deleted) {
                updateCasting();
                JOptionPane.showMessageDialog(
                        null,
                        "Il Membro del Cast è stata eliminato con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione del Membro dal Cast.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Elimina MembroCast da un Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void updateCasting() {
        ((AdminSessionController) getCurrentSessionContext().getController()).updateDetailedFilms();
        ((AdminSessionController) getCurrentSessionContext().getController()).updateDetailedSeries();
    }

    private boolean deleteCastMemberToCast(final int castMemberCode, final int castCode) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteCastMemberToCast(castMemberCode, castCode);
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
