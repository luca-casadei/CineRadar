package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.promo.Promo;
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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * The {@code AdminPromoView} class represents the administrator's view for managing promotional offers.
 * It extends {@code AdminPanel} and provides functionalities to display, add, and delete promotions.
 * The view includes a table displaying current promotions and buttons to add or delete promotions.
 *
 * This class interacts with the {@link AdminSessionController} to perform promotional operations.
 */
public final class AdminPromoView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -302785493612487L;
    private static final String ERROR = "Errore";
    private static final String COMPLETE_DELETE = "Eliminazione completata";
    private final JTable promoTable;

    /**
     * Constructs a new AdminFilmView with the specified ViewContext.
     *
     * @param currentSessionContext The ViewContext representing the current session.
     */
    public AdminPromoView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        setLayout(new BorderLayout());
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle Promo.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);
        this.promoTable = createPromoTable();
        final JScrollPane scrollPane = new JScrollPane(promoTable);
        add(scrollPane, BorderLayout.CENTER);
        initButtons();
    }

    private void initButtons() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addMultipleButton = new JButton("Aggiungi Promo Multipla");
        addMultipleButton.addActionListener(e -> addMultiplePromoDialog());
        buttonPanel.add(addMultipleButton);
        final JButton addGenreButton = new JButton("Aggiungi Promo su Genere");
        addGenreButton.addActionListener(e -> addGenrePromoDialog());
        buttonPanel.add(addGenreButton);
        final JButton addSinglePromoButton = new JButton("Aggiungi Promo su Multimedia specifico");
        addSinglePromoButton.addActionListener(e -> addSinglePromoDialog());
        buttonPanel.add(addSinglePromoButton);
        final JButton deleteButton = new JButton("Elimina Promo");
        deleteButton.addActionListener(e -> deletePromoDialog());
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addSinglePromoDialog() {
        final JComboBox<String> multimediaBox = new JComboBox<>(List.of("Serie", "Film")
                .toArray(String[]::new));
        final JTextField multimediaCode = new JTextField(5);
        final JTextField percentageField = new JTextField(5);
        final JTextField expirationField = new JTextField(5);

        final JPanel panel = createPanel(multimediaBox, multimediaCode, percentageField, expirationField);

        final JButton okButton = new JButton("OK");
        configureOkButton(okButton, percentageField, expirationField, multimediaCode);

        okButton.addActionListener(e -> {
            if (LocalDate.parse(expirationField.getText()).isAfter(LocalDate.now())) {
                addSinglePromo(
                        Integer.parseInt(percentageField.getText()),
                        LocalDate.parse(expirationField.getText()),
                        String.valueOf(multimediaBox.getSelectedItem()),
                        Integer.parseInt(multimediaCode.getText())
                );
            } else {
                showErrorDialog();
            }
            disposeOptionPane();
        });

        showOptionDialog(panel, okButton);
    }

    private void addSinglePromo(
            final int percentage, final LocalDate expiration, final String multimediaType, final int multimediaCode) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addSinglePromo(percentage, expiration, multimediaType, multimediaCode);
        updatePromoTable();
    }

    private void addGenrePromoDialog() {
        final JComboBox<String> genreBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController()).getGenres()
                        .stream()
                        .map(Genre::name)
                        .toArray(String[]::new)
        );
        final JTextField percentageField = new JTextField(5);
        final JTextField expirationField = new JTextField(5);

        final JPanel panel = createPanel(genreBox, null, percentageField, expirationField);

        final JButton okButton = new JButton("OK");
        configureOkButton(okButton, percentageField, expirationField);

        okButton.addActionListener(e -> {
            if (LocalDate.parse(expirationField.getText()).isAfter(LocalDate.now())) {
                addGenrePromo(
                        Integer.parseInt(percentageField.getText()),
                        LocalDate.parse(expirationField.getText()),
                        String.valueOf(genreBox.getSelectedItem())
                );
            } else {
                showErrorDialog();
            }
            disposeOptionPane();
        });

        showOptionDialog(panel, okButton);
    }

    private void addGenrePromo(final int percentage, final LocalDate expiration, final String genre) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addGenrePromo(percentage, expiration, genre);
        updatePromoTable();
    }

    private void addMultiplePromoDialog() {
        final JTextField percentageField = new JTextField(5);
        final JTextField expirationField = new JTextField(5);

        final JPanel panel = createPanel(null, null, percentageField, expirationField);

        final JButton okButton = new JButton("OK");
        configureOkButton(okButton, percentageField, expirationField);

        okButton.addActionListener(e -> {
            if (LocalDate.parse(expirationField.getText()).isAfter(LocalDate.now())) {
                addMultiplePromo(
                        Integer.parseInt(percentageField.getText()),
                        LocalDate.parse(expirationField.getText())
                );
            } else {
                showErrorDialog();
            }
            disposeOptionPane();
        });

        showOptionDialog(panel, okButton);
    }

    private void addMultiplePromo(final int percentage, final LocalDate expiration) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addMultiplePromo(percentage, expiration);
        updatePromoTable();
    }

    private JPanel createPanel(
            final JComboBox<String> box, final JTextField multimediaCode,
            final JTextField percentageField, final JTextField expirationField) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if (!Objects.isNull(box)) {
            panel.add(new JLabel("Promo su Serie o Film?:"));
            panel.add(new JScrollPane(box));
        }
        if (!Objects.isNull(multimediaCode)) {
            panel.add(new JLabel("Codice Film/Serie:"));
            panel.add(multimediaCode);
        }
        panel.add(new JLabel("Percentuale:"));
        panel.add(percentageField);
        panel.add(new JLabel("Scadenza (yyyy-MM-dd):"));
        panel.add(expirationField);
        return panel;
    }

    private void configureOkButton(final JButton okButton, final JTextField... fields) {
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            boolean allFilled = true;
            for (final JTextField field : fields) {
                allFilled &= isFieldFilled(field.getText());
            }
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        for (final JTextField field : fields) {
            field.getDocument().addDocumentListener(listener);
        }
    }

    private void showErrorDialog() {
        JOptionPane.showMessageDialog(
                null,
                "Inserisci una scadenza valida per la Promo.",
                ERROR, JOptionPane.ERROR_MESSAGE);
    }

    private void disposeOptionPane() {
        JOptionPane.getRootFrame().dispose();
    }

    private void showOptionDialog(final JPanel panel, final JButton okButton) {
        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void deletePromoDialog() {
        final JTextField codePromoField = new JTextField(5);
        final JTextField expirationField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("CodicePromo:"));
        panel.add(codePromoField);
        panel.add(new JLabel("Scadenza (yyyy-MM-dd):"));
        panel.add(expirationField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(codePromoField.getText())
                    && isFieldFilled(expirationField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        codePromoField.getDocument().addDocumentListener(listener);
        expirationField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            final int code = Integer.parseInt(codePromoField.getText());
            final LocalDate expiration = LocalDate.parse(expirationField.getText());
            final boolean deleted = deletePromo(code, expiration);
            if (deleted) {
                updatePromoTable();
                JOptionPane.showMessageDialog(
                        null,
                        "La Promo è stata eliminata con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione della Promo.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Elimina Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private boolean deletePromo(final int code, final LocalDate expiration) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deletePromo(code, expiration);
    }

    private void updatePromoTable() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel model = (DefaultTableModel) this.promoTable.getModel();
            model.setRowCount(0);
            for (final Promo promo : ((AdminSessionController) getCurrentSessionContext().getController()).getPromos()) {
                model.addRow(new Object[]{
                        promo.id(),
                        promo.percentageDiscount(),
                        promo.expiration()
                });
            }
        });
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
