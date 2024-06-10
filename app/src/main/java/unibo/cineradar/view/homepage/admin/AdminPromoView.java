package unibo.cineradar.view.homepage.admin;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.promo.GenrePromo;
import unibo.cineradar.model.promo.Promo;
import unibo.cineradar.model.promo.SinglePromo;
import unibo.cineradar.model.promo.TemplatePromo;
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
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code AdminPromoView} class represents the administrator's view for managing promotional offers.
 * It extends {@code AdminPanel} and provides functionalities to display, add, and delete promotions.
 * The view includes a table displaying current promotions and buttons to add or delete promotions.
 * This class interacts with the {@link AdminSessionController} to perform promotional operations.
 */
public final class AdminPromoView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -302785493982487L;
    private static final String ERROR = "Errore";
    private static final String COMPLETE_DELETE = "Eliminazione completata";
    private static final String DATABASE_ERROR = "Errore del database: ";
    private static final String CANCEL = "Cancel";
    private static final String TEMPLATE_PROMO = "Template Promo";
    private static final String SINGLE_PROMO = "Singolo Promo";
    private static final String MULTIPLE_PROMO = "Multiplo Promo";
    private static final String GENRE_PROMO = "Genere Promo";
    private static final String PROMO = "Promo";

    /**
     * Constructs a new AdminFilmView with the specified ViewContext.
     *
     * @param currentSessionContext The ViewContext representing the current session.
     */
    public AdminPromoView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JTable promoTable = createPromoTable();
        promoTable.setName(PROMO);
        final JScrollPane scrollPane = new JScrollPane(promoTable);
        initButtons();
        add(getPromoButtonPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Updates the panel with the latest information.
     * This method triggers the refresh of the current promo table.
     */
    @Override
    public void updatePanel() {
        updateCurrentTable();
    }

    private void initButtons() {
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        final JButton addMultipleButton = new JButton("Aggiungi Promo Multipla");
        addMultipleButton.addActionListener(e -> addMultiplePromoDialog());
        buttonPanel.add(addMultipleButton);
        final JButton addSinglePromoButton = new JButton("Aggiungi Promo Singola");
        addSinglePromoButton.addActionListener(e -> addSinglePromoDialog());
        buttonPanel.add(addSinglePromoButton);
        final JButton addTemplatePromoButton = new JButton("Aggiungi Template Promo");
        addTemplatePromoButton.addActionListener(e -> addTemplatePromoDialog());
        buttonPanel.add(addTemplatePromoButton);
        final JButton addGenreButton = new JButton("Aggiungi Promo su Genere");
        addGenreButton.addActionListener(e -> addGenrePromoDialog());
        buttonPanel.add(addGenreButton);
        final JButton addPromoButton = new JButton("Aggiungi Promo");
        addPromoButton.addActionListener(e -> addPromoDialog());
        buttonPanel.add(addPromoButton);
        final JButton deleteButton = new JButton("Elimina Promo");
        deleteButton.addActionListener(e -> deletePromoDialog());
        buttonPanel.add(deleteButton);
        final JButton deleteTemplateButton = new JButton("Elimina Template Promo");
        deleteTemplateButton.addActionListener(e -> deleteTemplatePromoDialog());
        buttonPanel.add(deleteTemplateButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getPromoButtonPanel() {
        final JPanel rankingsButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton templatePromoButton = new JButton(TEMPLATE_PROMO);
        templatePromoButton.addActionListener(e -> showPromo(TEMPLATE_PROMO));
        rankingsButtonPanel.add(templatePromoButton);
        final JButton singlePromoButton = new JButton(SINGLE_PROMO);
        singlePromoButton.addActionListener(e -> showPromo(SINGLE_PROMO));
        rankingsButtonPanel.add(singlePromoButton);
        final JButton multiplePromoButton = new JButton(MULTIPLE_PROMO);
        multiplePromoButton.addActionListener(e -> showPromo(MULTIPLE_PROMO));
        rankingsButtonPanel.add(multiplePromoButton);
        final JButton genrePromoButton = new JButton(GENRE_PROMO);
        genrePromoButton.addActionListener(e -> showPromo(GENRE_PROMO));
        rankingsButtonPanel.add(genrePromoButton);
        final JButton promoButton = new JButton(PROMO);
        promoButton.addActionListener(e -> showPromo(PROMO));
        rankingsButtonPanel.add(promoButton);
        return rankingsButtonPanel;
    }

    private void showPromo(final String text) {
        this.remove(2);
        final JTable table = switch (text) {
            case TEMPLATE_PROMO -> createTemplatePromoTable();
            case SINGLE_PROMO -> createSinglePromoTable();
            case MULTIPLE_PROMO -> createMultiplePromoTable();
            case GENRE_PROMO -> createGenrePromoTable();
            case PROMO -> createPromoTable();
            default -> throw new IllegalStateException("Unexpected value: " + text);
        };
        table.setName(text);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void addSinglePromoDialog() {
        if (((AdminSessionController) getCurrentSessionContext().getController()).getTemplatePromos().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun Template Promo disponibile",
                    ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JComboBox<String> multimediaBox = new JComboBox<>(List.of("Serie", "Film")
                .toArray(String[]::new));
        final JTextField multimediaCode = new JTextField(5);
        final JTextField templateCodeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Tipo di Multimedia:"));
        panel.add(multimediaBox);
        panel.add(new JLabel("ID Multimedia:"));
        panel.add(multimediaCode);
        panel.add(new JLabel("ID Template Promo:"));
        panel.add(templateCodeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(templateCodeField.getText())
                    && isFieldFilled(multimediaCode.getText());
            okButton.setEnabled(isFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        templateCodeField.getDocument().addDocumentListener(listener);
        multimediaCode.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            final int templateCodeLimit = Integer.parseInt(templateCodeField.getText());
            final int multimediaLimit = Integer.parseInt(multimediaCode.getText());
            try {
                if (templateCodeLimit <= 0 && multimediaLimit <= 0) {
                    throw new NumberFormatException();
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isTemplatePromoAvailable(Integer.parseInt(templateCodeField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Template Promo non inserita",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ("Serie".equals(multimediaBox.getSelectedItem())
                        && ((AdminSessionController) getCurrentSessionContext().getController())
                        .isSeriesAvailable(Integer.parseInt(multimediaCode.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Serie non inserita",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (((AdminSessionController) getCurrentSessionContext().getController())
                            .isFilmAvailable(Integer.parseInt(multimediaCode.getText()))) {
                        JOptionPane.showMessageDialog(null,
                                "Errore: Film non inserito",
                                ERROR, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                addSinglePromo(
                        Integer.parseInt(templateCodeField.getText()),
                        String.valueOf(multimediaBox.getSelectedItem()),
                        Integer.parseInt(multimediaCode.getText())
                );
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire Codici Validi",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Singolo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addSinglePromo(
            final int templateCode, final String multimediaType, final int multimediaCode) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addSinglePromo(templateCode, multimediaType, multimediaCode);
        updateCurrentTable();
    }

    private void addGenrePromoDialog() {
        if (((AdminSessionController) getCurrentSessionContext().getController()).getMultiples().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun Multiplo disponibile",
                    ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JComboBox<String> genreBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController()).getGenres()
                        .stream()
                        .map(Genre::name)
                        .toArray(String[]::new)
        );
        final JComboBox<Integer> multipleBox = new JComboBox<>(
                ((AdminSessionController) getCurrentSessionContext().getController())
                        .getMultiples().toArray(Integer[]::new)
        );

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Generi:"));
        panel.add(genreBox);
        panel.add(new JLabel("ID Multiplo:"));
        panel.add(multipleBox);

        final JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isMultipleAvailable(
                                Integer.parseInt(String.valueOf(multipleBox.getSelectedItem())))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Multiplo non inserito",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addGenrePromo(
                        String.valueOf(genreBox.getSelectedItem()),
                        Integer.parseInt(String.valueOf(multipleBox.getSelectedItem()))
                );
                disposeOptionPane();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire una Percentuale Valida",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        showOptionDialog(panel, okButton);
    }

    private void addGenrePromo(final String genre, final int multipleId) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addGenrePromo(genre, multipleId);
        updateCurrentTable();
    }

    private void addTemplatePromoDialog() {
        final JTextField percentageField = new JTextField(20);
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Percentuale:"));
        panel.add(percentageField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(percentageField.getText());
            okButton.setEnabled(isFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        percentageField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            final int percentageLimit = Integer.parseInt(percentageField.getText());
            if (percentageLimit < 0) {
                throw new NumberFormatException();
            }
            try {
                addTemplatePromo(
                        Integer.parseInt(percentageField.getText())
                );
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire una Percentuale Valida",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Template Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addTemplatePromo(final int percentage) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addTemplatePromo(percentage);
        updateCurrentTable();
    }

    private void addMultiplePromoDialog() {
        if (((AdminSessionController) getCurrentSessionContext().getController()).getTemplatePromos().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun Template Promo disponibile",
                    ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JTextField codeField = new JTextField(20);
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("ID Template Promo:"));
        panel.add(codeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(codeField.getText());
            okButton.setEnabled(isFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        codeField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                final int percentageLimit = Integer.parseInt(codeField.getText());
                if (percentageLimit <= 0) {
                    throw new NumberFormatException();
                }
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isTemplatePromoAvailable(Integer.parseInt(codeField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Template Promo non inserita",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addMultiplePromo(
                        Integer.parseInt(codeField.getText())
                );
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire un Codice Valido",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Multiplo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addMultiplePromo(final int percentage) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addMultiplePromo(percentage);
        updateCurrentTable();
    }

    private void addPromoDialog() {
        if (((AdminSessionController) getCurrentSessionContext().getController()).getTemplatePromos().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Errore: Nessun Template Promo disponibile",
                    ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JTextField codeField = new JTextField(20);
        final DatePicker expirationField = new DatePicker();
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Scadenza:"));
        panel.add(expirationField);
        panel.add(new JLabel("ID Promo:"));
        panel.add(codeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(codeField.getText());
            okButton.setEnabled(isFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        codeField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                if (((AdminSessionController) getCurrentSessionContext().getController())
                        .isTemplatePromoAvailable(Integer.parseInt(codeField.getText()))) {
                    JOptionPane.showMessageDialog(null,
                            "Errore: Template Promo non inserita",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                addPromo(
                        Integer.parseInt(codeField.getText()),
                        expirationField.getDate()
                );
                JOptionPane.getRootFrame().dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore: Inserire un Codice Valido",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void addPromo(final int code, final LocalDate expiration) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .addPromo(code, expiration);
    }

    private void disposeOptionPane() {
        JOptionPane.getRootFrame().dispose();
    }

    private void showOptionDialog(final JPanel panel, final JButton okButton) {
        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void deletePromoDialog() {
        final JTextField codePromoField = new JTextField(5);
        final DatePicker expirationField = new DatePicker();

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Scadenza:"));
        panel.add(expirationField);
        panel.add(new JLabel("CodicePromo:"));
        panel.add(codePromoField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(codePromoField.getText())
                    && isFieldFilled(expirationField.getText());
            okButton.setEnabled(allFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        codePromoField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                final int code = Integer.parseInt(codePromoField.getText());
                final LocalDate expiration = expirationField.getDate();
                final boolean deleted = deletePromo(code, expiration);
                if (deleted) {
                    updateCurrentTable();
                    JOptionPane.showMessageDialog(
                            null,
                            "La Promo e' stata eliminata con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Inserire un Codice Valido per la Promo.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Elimina Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private boolean deletePromo(final int code, final LocalDate expiration) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deletePromo(code, expiration);
    }

    private void deleteTemplatePromoDialog() {
        final JTextField codeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("CodiceTemplatePromo:"));
        panel.add(codeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean isFilled = isFieldFilled(codeField.getText());
            okButton.setEnabled(isFilled);
        };

        final DocumentListener listener = new ViewDocumentListener(checkFields);
        codeField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                final int code = Integer.parseInt(codeField.getText());
                final boolean deleted = deleteTemplatePromo(code);
                if (deleted) {
                    updateCurrentTable();
                    JOptionPane.showMessageDialog(
                            null,
                            "La Template Promo e' stata eliminata con successo.",
                            COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Inserire un Codice Valido per Template Promo.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        DATABASE_ERROR + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.getRootFrame().dispose();
        });

        final Object[] options = {okButton, CANCEL};
        JOptionPane.showOptionDialog(null, panel, "Elimina Template Promo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private boolean deleteTemplatePromo(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .deleteTemplatePromo(code);
    }

    private void updateCurrentTable() {
        final JScrollPane scrollPane = (JScrollPane) this.getComponent(2);
        final JTable table = (JTable) scrollPane.getViewport().getView();
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        switch (table.getName()) {
            case TEMPLATE_PROMO -> {
                for (final TemplatePromo promo
                        : ((AdminSessionController) this.getCurrentSessionContext().getController())
                        .getTemplatePromos()) {
                    model.addRow(new Object[]{
                            promo.codePromo(),
                            promo.percentage()
                    });
                }
            }
            case SINGLE_PROMO -> {
                for (final SinglePromo promo
                        : ((AdminSessionController) this.getCurrentSessionContext().getController())
                        .getSinglePromos()) {
                    model.addRow(new Object[]{
                            promo.codePromo(),
                            promo.seriesCode(),
                            promo.filmCode()
                    });
                }
            }
            case MULTIPLE_PROMO -> {
                for (final Integer idPromo
                        : ((AdminSessionController) this.getCurrentSessionContext().getController())
                        .getMultiples()) {
                    model.addRow(new Object[]{
                            idPromo
                    });
                }
            }
            case GENRE_PROMO -> {
                for (final GenrePromo promo
                        : ((AdminSessionController) this.getCurrentSessionContext().getController())
                        .getGenrePromos()) {
                    model.addRow(new Object[]{
                            promo.genrePromoCode(),
                            promo.genre()
                    });
                }
            }
            case PROMO -> {
                for (final Promo promo
                        : ((AdminSessionController) this.getCurrentSessionContext().getController())
                        .getPromos()) {
                    model.addRow(new Object[]{
                            promo.id(),
                            promo.percentageDiscount(),
                            promo.expiration()
                    });
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + table.getName());
        }
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }
}
