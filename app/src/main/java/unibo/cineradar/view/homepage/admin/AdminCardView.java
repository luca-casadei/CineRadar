package unibo.cineradar.view.homepage.admin;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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

    private void assignPromoDialog() {
        final JTextField codePromoField = new JTextField(5);
        final DatePicker expirationField = new DatePicker();
        final JTextField cinemaCodeField = new JTextField(5);
        final JTextField usernameField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("CodicePromo:"));
        panel.add(codePromoField);
        panel.add(new JLabel("Scadenza:"));
        panel.add(expirationField);
        panel.add(new JLabel("Codice Cinema:"));
        panel.add(cinemaCodeField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(cinemaCodeField.getText())
                    && isFieldFilled(usernameField.getText())
                    && isFieldFilled(codePromoField.getText())
                    && isFieldFilled(expirationField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        cinemaCodeField.getDocument().addDocumentListener(listener);
        usernameField.getDocument().addDocumentListener(listener);
        codePromoField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            if (expirationField.getDate().isAfter(LocalDate.now())) {
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isPromoAvailable(Integer.parseInt(codePromoField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Promo non inserita",
                            ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isCinemaAvailable(Integer.parseInt(cinemaCodeField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Cinema non inserito",
                            ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isUserAvailable(usernameField.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Utente non inserito",
                            ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                assignPromo(
                        Integer.parseInt(codePromoField.getText()),
                        expirationField.getDate(),
                        Integer.parseInt(cinemaCodeField.getText()),
                        usernameField.getText()
                );
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

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
