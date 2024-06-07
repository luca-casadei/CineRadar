package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents the view for administering registrators in the admin interface.
 * This view allows an admin user to view existing registrators and delete them.
 */
public class AdminRegistratorView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -172029805230806848L;
    private static final String ERRORE = "Errore!";
    private final JTable cineTable;
    private final JTable regTable;

    /**
     * Constructor of the admin users view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminRegistratorView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.regTable = super.createRegistratorTable();
        final JScrollPane regScrollPane = new JScrollPane(regTable);
        this.add(regScrollPane, BorderLayout.CENTER);
        this.cineTable = super.createCinemaTable();
        final JScrollPane cineScrollPane = new JScrollPane(cineTable);
        this.add(cineScrollPane, BorderLayout.EAST);
        final JPanel buttonPanel = getButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addRegButton = new JButton("Aggiungi Registratore");
        addRegButton.addActionListener(e -> addRegistrarDialog());
        buttonPanel.add(addRegButton);
        final JButton deleteRegButton = new JButton("Elimina Registratore");
        deleteRegButton.addActionListener(e -> deleteRegistrarDialog());
        buttonPanel.add(deleteRegButton);
        final JButton addCineButton = new JButton("Aggiungi Cinema");
        addCineButton.addActionListener(e -> addCinemaDialog());
        buttonPanel.add(addCineButton);
        final JButton deleteCineButton = new JButton("Elimina Cinema");
        deleteCineButton.addActionListener(e -> deleteCinemaDialog());
        buttonPanel.add(deleteCineButton);
        return buttonPanel;
    }

    private void deleteCinemaDialog() {
        final int code = Integer.parseInt(Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci Codice del Cinema da eliminare:",
                        "Elimina Cinema", JOptionPane.PLAIN_MESSAGE)));
        final boolean deleted = deleteCinema(code);
        if (deleted) {
            updateCinemas();
            updateRegistrars();
            JOptionPane.showMessageDialog(
                    null,
                    "Il Cinema e' stato eliminato con successo.",
                    "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserire un Codice Corretto",
                    ERRORE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean deleteCinema(final int code) {
        return ((AdminSessionController) this.getCurrentSessionContext().getController())
                .deleteCinema(code);
    }

    private void addCinemaDialog() {
        final JTextField nameField = new JTextField(20);
        final JTextField streetField = new JTextField(20);
        final JTextField capField = new JTextField(20);
        final JTextField civicField = new JTextField(20);
        final JTextField cityField = new JTextField(20);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Via:"));
        panel.add(streetField);
        panel.add(new JLabel("CAP:"));
        panel.add(capField);
        panel.add(new JLabel("Civico:"));
        panel.add(civicField);
        panel.add(new JLabel("Citta':"));
        panel.add(cityField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(nameField.getText())
                    && isFieldFilled(streetField.getText())
                    && isFieldFilled(capField.getText())
                    && isFieldFilled(civicField.getText())
                    && isFieldFilled(cityField.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        nameField.getDocument().addDocumentListener(listener);
        streetField.getDocument().addDocumentListener(listener);
        capField.getDocument().addDocumentListener(listener);
        civicField.getDocument().addDocumentListener(listener);
        cityField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                final int civic = Integer.parseInt(civicField.getText());
                final int cap = Integer.parseInt(capField.getText());
                if (civic <= 0 || cap <= 0) {
                    throw new NumberFormatException();
                }
                addCinema(
                        nameField.getText(),
                        streetField.getText(),
                        capField.getText(),
                        Integer.parseInt(civicField.getText()),
                        cityField.getText());
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire Civico e CAP Validi",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore del database: " + ex.getMessage(),
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Cinema",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addCinema(
            final String name, final String street, final String cap,
            final int civic, final String city) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addCinema(name, street, cap, civic, city);
        updateCinemas();
    }

    private void addRegistrarDialog() {
        final JTextField usernameField = new JTextField(20);
        final JTextField nameField = new JTextField(20);
        final JTextField surnameField = new JTextField(20);
        final JPasswordField passwordField = new JPasswordField(20);
        final JTextField emailField = new JTextField(20);
        final JTextField cineCodeField = new JTextField(20);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Cognome:"));
        panel.add(surnameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email Cinema:"));
        panel.add(emailField);
        panel.add(new JLabel("Codice Cinema:"));
        panel.add(cineCodeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(nameField.getText())
                    && isFieldFilled(surnameField.getText())
                    && isFieldFilled(Arrays.toString(passwordField.getPassword()))
                    && isFieldFilled(usernameField.getText())
                    && isFieldFilled(cineCodeField.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        nameField.getDocument().addDocumentListener(listener);
        surnameField.getDocument().addDocumentListener(listener);
        passwordField.getDocument().addDocumentListener(listener);
        usernameField.getDocument().addDocumentListener(listener);
        cineCodeField.getDocument().addDocumentListener(listener);
        emailField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                if (!emailField.getText().isBlank() && !isEmailValid(emailField.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Email non corretta",
                            ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isCinemaAvailable(Integer.parseInt(cineCodeField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Cinema non inserito",
                            ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addRegistrar(
                        usernameField.getText(),
                        nameField.getText(),
                        surnameField.getText(),
                        passwordField.getPassword(),
                        emailField.getText(),
                        Integer.parseInt(cineCodeField.getText()));
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire un Codice Valido",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore del database: " + ex.getMessage(),
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Registratore",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addRegistrar(
            final String username, final String name, final String surname,
            final char[] password, final String email, final int cineCode) {
        ((AdminSessionController) getCurrentSessionContext().getController())
                .addRegistrar(username, name, surname, password, email, cineCode);
        updateRegistrars();
    }

    private boolean isEmailValid(final String email) {
        final Pattern pat = Pattern.compile(
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)"
                        + "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        return pat.matcher(email).matches();
    }

    private void deleteRegistrarDialog() {
        final String username = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci Username del Registratore da eliminare:",
                        "Elimina Registratore", JOptionPane.PLAIN_MESSAGE));
        final boolean deleted = deleteRegistrar(username);
        if (deleted) {
            updateRegistrars();
            JOptionPane.showMessageDialog(
                    null,
                    "Il Registratore e' stato eliminato con successo.",
                    "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserire un Username Corretto",
                    ERRORE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean deleteRegistrar(final String username) {
        return ((AdminSessionController) this.getCurrentSessionContext().getController())
                .deleteRegistrar(username);
    }

    private void updateRegistrars() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel model = (DefaultTableModel) this.regTable.getModel();
            model.setRowCount(0);
            for (final Registrar registrar
                    : ((AdminSessionController) getCurrentSessionContext().getController()).getRegistrars()) {
                model.addRow(new Object[]{
                        registrar.getUsername(),
                        registrar.getName(),
                        registrar.getLastName(),
                        registrar.getEmailCinema(),
                        registrar.getCinema()
                });
            }
        });
    }

    private void updateCinemas() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel model = (DefaultTableModel) this.cineTable.getModel();
            model.setRowCount(0);
            for (final Cinema cinema
                    : ((AdminSessionController) getCurrentSessionContext().getController()).getCinemas()) {
                model.addRow(new Object[]{
                        cinema.codice(),
                        cinema.indVia(),
                        cinema.indCAP(),
                        cinema.civico(),
                        cinema.citta()
                });
            }
        });
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
