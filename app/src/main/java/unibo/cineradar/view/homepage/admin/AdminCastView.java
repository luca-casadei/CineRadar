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

        final GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, buttonConstraints);
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
        final DefaultTableModel model = (DefaultTableModel) this.castTable.getModel();
        model.setRowCount(0);
        for (final Casting casting
                : ((AdminSessionController) getCurrentSessionContext().getController()).getCasting()) {
            model.addRow(new Object[]{
                    casting.id(),
                    casting.name(),
            });
        }
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

        final int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi Cast",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addCast(nameField.getText());
        }
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
}
