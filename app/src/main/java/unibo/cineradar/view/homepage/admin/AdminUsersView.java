package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import java.util.Objects;

/**
 * Represents the view for administering users in the admin interface.
 * This view allows an admin user to view existing users and delete them.
 */
public class AdminUsersView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -724679605230890248L;
    private static final String ERROR = "Errore!";
    private final JTable userTable;

    /**
     * Constructor of the admin users view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminUsersView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.userTable = super.createUserTable();
        final JScrollPane userScrollPane = new JScrollPane(userTable);
        this.add(userScrollPane);
        final JPanel buttonPanel = getButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton deleteMemberButton = new JButton("Elimina Utente");
        deleteMemberButton.addActionListener(e -> deleteUserDialog());
        buttonPanel.add(deleteMemberButton);
        return buttonPanel;
    }

    private void deleteUserDialog() {
        final String username = Objects.requireNonNull(
                JOptionPane.showInputDialog(
                        null,
                        "Inserisci Username dell'utente da eliminare:",
                        "Elimina Membro Cast", JOptionPane.PLAIN_MESSAGE));
        try {
            final boolean deleted = deleteUser(username);
            if (deleted) {
                updateUsers();
                JOptionPane.showMessageDialog(
                        null,
                        "L'utente e' stato eliminato con successo.",
                        "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Inserire un Username Corretto",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci un numero valido per Username.",
                    ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Errore del database: " + ex.getMessage(),
                    ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean deleteUser(final String username) {
        return ((AdminSessionController) this.getCurrentSessionContext().getController())
                .deleteUser(username);
    }

    private void updateUsers() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel model = (DefaultTableModel) this.userTable.getModel();
            model.setRowCount(0);
            for (final User user
                    : ((AdminSessionController) getCurrentSessionContext().getController()).getUsers()) {
                model.addRow(new Object[]{
                        user.getUsername(),
                        user.getName(),
                        user.getLastName(),
                        user.getBirthDate(),
                        user.getAge(),
                        user.isPrizeTag() ? "Si" : "No"
                });
            }
        });
    }
}
