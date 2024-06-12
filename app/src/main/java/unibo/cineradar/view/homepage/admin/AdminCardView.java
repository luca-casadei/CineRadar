package unibo.cineradar.view.homepage.admin;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import java.time.LocalDate;

/**
 * Represents the view for administering cards in the admin interface.
 * This view allows an admin user to view existing cards and assign promotional codes to users.
 */
public class AdminCardView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -924898759230851648L;
    private static final String ERRORE = "Errore!";

    /**
     * Constructor of the admin cards view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminCardView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JTable cardTable = super.createCardTable();
        final JScrollPane cardScrollPane = new JScrollPane(cardTable);
        this.add(cardScrollPane);
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton assignPromo = new JButton("Assegna Promo");
        assignPromo.addActionListener(e -> assignPromoDialog());
        buttonPanel.add(assignPromo);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the panel.
     * This method does not trigger any specific updates but can be overridden to provide custom update behavior.
     */
    @Override
    public void updatePanel() {
        // Implement custom update behavior here if needed
    }

    private void assignPromoDialog() {
        if (((AdminSessionController) getCurrentSessionContext().getController())
                .getCinemas().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun Cinema inserito",
                    ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (((AdminSessionController) getCurrentSessionContext().getController())
                .getUsers().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun utente inserito",
                    ERRORE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JTextField idPromoField = new JTextField(5);
        final DatePicker expPromoField = new DatePicker();
        final JComboBox<Integer> cinemaBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController()).getCinemas()
                        .stream()
                        .map(Cinema::codice)
                        .toArray(Integer[]::new)
        );
        final JComboBox<String> userBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController()).getUsers()
                        .stream()
                        .map(User::getUsername)
                        .toArray(String[]::new)
        );

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID Cinema:"));
        panel.add(cinemaBox);
        panel.add(new JLabel("Username:"));
        panel.add(userBox);
        panel.add(new JLabel("Scadenza Promo:"));
        panel.add(expPromoField);
        panel.add(new JLabel("ID Promo:"));
        panel.add(idPromoField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(idPromoField.getText())
                    && isFieldFilled(expPromoField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        idPromoField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            if (expPromoField.getDate().isAfter(LocalDate.now())) {
                try {
                    if (isNonNegativeNumber(idPromoField.getText())
                            || isNonNegativeNumber(String.valueOf(cinemaBox.getSelectedItem()))) {
                        throw new NumberFormatException();
                    }
                    if (((AdminSessionController) getCurrentSessionContext().getController())
                            .isPromoAvailable(Integer.parseInt(idPromoField.getText()))) {
                        JOptionPane.showMessageDialog(null,
                                "Errore: Promo non inserita",
                                ERRORE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (((AdminSessionController) getCurrentSessionContext().getController())
                            .isCardAvailable(
                                    String.valueOf(userBox.getSelectedItem()),
                                    Integer.parseInt(String.valueOf(cinemaBox.getSelectedItem()))
                            )) {
                        JOptionPane.showMessageDialog(null,
                                "Errore: Utente non ha la Tessera del Cinema inserita",
                                ERRORE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    assignPromo(
                            Integer.parseInt(idPromoField.getText()),
                            expPromoField.getDate(),
                            Integer.parseInt(
                                    String.valueOf(cinemaBox.getSelectedItem())),
                            String.valueOf(userBox.getSelectedItem())
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Inserire Codici Validi",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Errore del database: " + ex.getMessage(),
                            ERRORE, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Inserisci una scadenza valida per la Promo.",
                        ERRORE, JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Assegna Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void assignPromo(
            final int promoCode, final LocalDate expiration, final int cinemaCode, final String username) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .assignPromo(promoCode, expiration, cinemaCode, username);
    }

    private boolean isNonNegativeNumber(final String str) {
        return !str.matches("\\d+");
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
