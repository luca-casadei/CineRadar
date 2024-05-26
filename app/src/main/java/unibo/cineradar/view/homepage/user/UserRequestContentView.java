package unibo.cineradar.view.homepage.user;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.view.ViewContext;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serial;
import java.util.Objects;

// CHECKSTYLE: MagicNumber OFF

/**
 * This class represents the window for inserting content request.
 */
public class UserRequestContentView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the UserRequestContentView class.
     *
     * @param currentSessionContext The context of the current session.
     */
    public UserRequestContentView(final ViewContext currentSessionContext) {
        setTitle("Richiesta di aggiunta contenuto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        final JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        final JLabel titleLabel = new JLabel("Titolo:");
        panel.add(titleLabel, gbc);

        final JTextField titleField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel yearLabel = new JLabel("Anno di rilascio:");
        panel.add(yearLabel, gbc);

        final DatePicker yearField = new DatePicker();
        gbc.gridx = 1;
        panel.add(yearField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel descriptionLabel = new JLabel("Descrizione:");
        panel.add(descriptionLabel, gbc);

        final JTextArea descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        gbc.gridx = 1;
        panel.add(descriptionArea, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel typeLabel = new JLabel("Tipo:");
        panel.add(typeLabel, gbc);

        final String[] types = {"Film", "Serie TV"};
        final JComboBox<String> typeComboBox = new JComboBox<>(types);
        gbc.gridx = 1;
        panel.add(typeComboBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        final JButton submitButton = new JButton("Invia richiesta");

        submitButton.addActionListener(e -> {
            if (titleField.getText().isEmpty() || yearField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Perfavore riempi tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                if (((UserSessionController) currentSessionContext.getController()).addRequest(
                        !Objects.equals(String.valueOf(typeComboBox.getSelectedItem()), "Film"),
                        titleField.getText(),
                        yearField.getDate(),
                        descriptionArea.getText()
                )) {
                    this.dispose();
                }
            }
        });

        panel.add(submitButton, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
// CHECKSTYLE: MagicNumber ON
