package unibo.cineradar.view.homepage.admin;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private static final String DATABASE_ERROR = "Errore del database: ";
    private final JTable castMemberTable;
    private final JTable castTable;

    /**
     * Constructs a new AdminCastView with the specified ViewContext.
     *
     * @param currentSessionContext The ViewContext representing the current session.
     */
    public AdminCastView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.castMemberTable = createCastMemberTable();
        final JScrollPane castMemberScrollPane = new JScrollPane(castMemberTable);
        this.castTable = createCastTable();
        final JScrollPane castScrollPane = new JScrollPane(castTable);
        add(castScrollPane, BorderLayout.WEST);
        add(castMemberScrollPane, BorderLayout.CENTER);
        final JPanel buttonPanel = getButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the panel with the latest information.
     * This method triggers the update of the cast table, member cast table, and casting information.
     */
    @Override
    public void updatePanel() {
        updateCastTable();
        updateMemberCastTable();
        updateCasting();
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
        addCastMemberToCastButton.addActionListener(e -> addCastMemberToCastDialog(Optional.empty()));
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
                    castMember instanceof Actor ? "Attore" : castMember instanceof Director ? "Regista" : "Attore e Regista",
                    castMember.getCareerDebutDate(),
                    castMember.getStageName()
            });
        }
    }

    /**
     * Updates the table displaying casts with the latest data by replacing the scroll pane.
     */
    private void updateCastTable() {
        final DefaultTableModel model = (DefaultTableModel) this.castTable.getModel();
        model.setRowCount(0);
        for (final Casting cast
                : ((AdminSessionController) getCurrentSessionContext().getController()).getCasting()) {
            model.addRow(new Object[]{
                    cast.id(),
                    cast.name()
            });
        }
    }

    /**
     * Displays a dialog for adding a cast member.
     * The dialog prompts the administrator to enter the details of the cast member.
     *
     * @return True if the dialog is closed without adding a cast member, otherwise false.
     */
    private boolean addCastMemberDialog() {
        final JTextField nameField = new JTextField(20);
        final JTextField surnameField = new JTextField(20);
        final DatePicker birthdayField = new DatePicker();
        final JCheckBox actorCheck = new JCheckBox("E' un Attore?");
        final JCheckBox directorCheck = new JCheckBox("E' un Regista?");
        final DatePicker dateDebutCareerField = new DatePicker();
        final JTextField artNameField = new JTextField(30);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Cognome:"));
        panel.add(surnameField);
        panel.add(new JLabel("Data di Nascita:"));
        panel.add(birthdayField);
        panel.add(actorCheck);
        panel.add(directorCheck);
        panel.add(new JLabel("Data Debutto Carriera:"));
        panel.add(dateDebutCareerField);
        panel.add(new JLabel("Nome d'Arte:"));
        panel.add(artNameField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(nameField.getText())
                    && isFieldFilled(surnameField.getText())
                    && isFieldFilled(actorCheck.getText())
                    && isFieldFilled(directorCheck.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        nameField.getDocument().addDocumentListener(listener);
        surnameField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            final boolean isActor = actorCheck.isSelected();
            final boolean isDirector = directorCheck.isSelected();
            if (!(isActor || isDirector)) {
                JOptionPane.showMessageDialog(null,
                        "Errore: Inserire almeno un Ruolo",
                        ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (birthdayField.getText().isBlank() || dateDebutCareerField.getText().isBlank()) {
                JOptionPane.showMessageDialog(null,
                        "Errore: Inserire le date in modo corretto",
                        ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (birthdayField.getDate().isAfter(dateDebutCareerField.getDate())) {
                JOptionPane.showMessageDialog(null,
                        "Errore: Inserire una Data di Nascita corretta",
                        ERROR, JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                addCastMember(
                        nameField.getText(),
                        surnameField.getText(),
                        birthdayField.getDate(),
                        isActor,
                        isDirector,
                        dateDebutCareerField.getDate(),
                        Optional.of(artNameField.getText()));
                JOptionPane.getRootFrame().dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, CANCEL};
        final int option = JOptionPane.showOptionDialog(null, panel, "Aggiungi Membro Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        return option == -1;
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
            final boolean isActor, final boolean isDirector,
            final LocalDate dateDebutCareer, final Optional<String> artName) {
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
            final List<Integer> castCodes = ((AdminSessionController) getCurrentSessionContext().getController())
                    .getCastLinked(code);
            final boolean deleted = deleteCastMember(code);
            if (deleted) {
                for (final Integer castCode : castCodes) {
                    if (((AdminSessionController) getCurrentSessionContext().getController())
                            .isEmptyCast(castCode)) {
                        ((AdminSessionController) getCurrentSessionContext().getController())
                                .deleteMultimediaCast(castCode);
                        ((AdminSessionController) getCurrentSessionContext().getController())
                                .deleteCast(castCode);
                    }
                }
                updateCastTable();
                updateCasting();
                updateMemberCastTable();
                JOptionPane.showMessageDialog(
                        null,
                        "Il Membro del Cast e' stato eliminato con successo.",
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
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    DATABASE_ERROR + ex.getMessage(),
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

        okButton.addActionListener(e -> {
            addCast(Optional.of(nameField.getText()));
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
    private void addCast(final Optional<String> name) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addCast(name);
        updateCastTable();

        final int option = JOptionPane.showConfirmDialog(null,
                "Vuoi creare un nuovo membro del cast?",
                "Aggiungi Membro Cast",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            final boolean isMemberAdded = addCastMemberDialog();
            if (!isMemberAdded) {
                deleteCast(((AdminSessionController) getCurrentSessionContext().getController())
                        .getLastCastId());
                updateCastTable();
                return;
            }
        }
        final boolean isMemberToCastAdded = addCastMemberToCastDialog(
                Optional.of(
                        ((AdminSessionController) getCurrentSessionContext().getController())
                                .getLastCastId()));
        if (!isMemberToCastAdded) {
            deleteCast(((AdminSessionController) getCurrentSessionContext().getController())
                    .getLastCastId());
            updateCastTable();
        }
    }

    /**
     * Displays a dialog for deleting a cast.
     * The dialog prompts the administrator to enter the ID of the cast to be deleted.
     */
    private void deleteCastDialog() {
        final String input = Objects.requireNonNull(JOptionPane.showInputDialog(
                        null,
                        "Inserisci l'ID del Cast da eliminare:",
                        "Elimina Cast", JOptionPane.PLAIN_MESSAGE));
        try {
            final int id = Integer.parseInt(input);
            ((AdminSessionController) getCurrentSessionContext().getController())
                    .deleteMultimediaCast(id);
            final boolean deleted = deleteCast(id);
            if (deleted) {
                updateCastTable();
                JOptionPane.showMessageDialog(
                        null,
                        "Il Cast e' stato eliminato con successo.",
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
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    DATABASE_ERROR + ex.getMessage(),
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

    /**
     * Displays a dialog for adding a cast member to a cast.
     * The dialog prompts the administrator to enter IDs of the cast and the cast member.
     *
     * @param castId An optional parameter representing the ID of the cast.
     *               If provided, the corresponding field is pre-filled and disabled.
     * @return True if the dialog is closed without adding a cast member to the cast, otherwise false.
     */
    private boolean addCastMemberToCastDialog(final Optional<Integer> castId) {
        if (((AdminSessionController) getCurrentSessionContext().getController()).getCasting().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun casting disponibile",
                    ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        final JTextField castMemberCodeField = new JTextField(5);
        final JTextField castField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Codice del Cast:"));
        panel.add(castField);
        if (castId.isPresent()) {
            castField.setText(castId.get().toString());
            castField.setEditable(false);
        }
        panel.add(new JLabel("Codice del Membro:"));
        panel.add(castMemberCodeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(castField.getText())
                    && isFieldFilled(castMemberCodeField.getText());
            okButton.setEnabled(isFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        castMemberCodeField.getDocument().addDocumentListener(listener);
        castField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                if (isNonNegativeNumber(castField.getText())
                        || isNonNegativeNumber(castMemberCodeField.getText())) {
                    throw new NumberFormatException();
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isCastAvailable(Integer.parseInt(castField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Cast non inserito",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isCastMemberAvailable(Integer.parseInt(castMemberCodeField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Membro Cast non inserito",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addCastMemberToCast(
                        Integer.parseInt(castMemberCodeField.getText()),
                        Integer.parseInt(castField.getText()));
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire Codici Validi",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, CANCEL};
        final int option = JOptionPane.showOptionDialog(null, panel, "Aggiungi MembroCast ad un Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        return option == -1;
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
            try {
                if (isNonNegativeNumber(castMemberCodeField.getText())
                        || isNonNegativeNumber(castCodeField.getText())) {
                    throw new NumberFormatException();
                }
                final int castMemberCode = Integer.parseInt(castMemberCodeField.getText());
                final int castCode = Integer.parseInt(castCodeField.getText());
                final boolean deleted = deleteCastMemberToCast(castMemberCode, castCode);
                if (deleted) {
                    if (((AdminSessionController) getCurrentSessionContext().getController())
                            .isEmptyCast(castCode)) {
                        ((AdminSessionController) getCurrentSessionContext().getController())
                                .deleteMultimediaCast(castCode);
                        ((AdminSessionController) getCurrentSessionContext().getController())
                                .deleteCast(castCode);
                    }
                    updateCastTable();
                    updateCasting();
                    JOptionPane.showMessageDialog(
                            null,
                            "Il Membro del Cast e' stata eliminato con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Errore durante l'eliminazione del Membro dal Cast.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire Codici Validi",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
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

    private boolean isNonNegativeNumber(final String str) {
        return !str.matches("\\d+");
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
