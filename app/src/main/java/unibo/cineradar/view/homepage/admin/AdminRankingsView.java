package unibo.cineradar.view.homepage.admin;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.ranking.CastRanking;
import unibo.cineradar.model.ranking.EvalType;
import unibo.cineradar.model.ranking.UserRanking;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

/**
 * Rankings view of the Admin.
 */
public class AdminRankingsView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -924897837382651458L;
    private static final int ROW_HEIGHT = 20;
    private static final String ERROR = "Errore";
    private static final String BEST_REVIEWERS_FOR_NUMBER =
            "5 Migliori Recensori per Numero";
    private static final String WORST_REVIEWERS_FOR_UTILITY =
            "5 Peggiori Recensori per Utilita'";
    private static final String BEST_REVIEWERS_FOR_UTILITY =
            "5 Migliori Recensori per Utilita'";
    private static final String BEST_DIRECTORS =
            "5 Migliori Registi";

    /**
     * Constructor of the admin rankings view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminRankingsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JTable fiveBestReviewersTable = createRankingTable();
        addBestReviewersRankingData(fiveBestReviewersTable);
        final JPanel buttonPanel = getButtonPanel();
        final JPanel showRankingsPanel = getRankingsButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(showRankingsPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(fiveBestReviewersTable), BorderLayout.CENTER);
    }

    /**
     * Updates the panel.
     * This method does not trigger any specific updates but can be overridden to provide custom update behavior.
     */
    @Override
    public void updatePanel() {
        // Implement custom update behavior here if needed
    }

    private JPanel getRankingsButtonPanel() {
        final JPanel rankingsButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton fiveBestReviewersButton = new JButton(BEST_REVIEWERS_FOR_NUMBER);
        fiveBestReviewersButton.addActionListener(e -> showRanking(BEST_REVIEWERS_FOR_NUMBER));
        rankingsButtonPanel.add(fiveBestReviewersButton);
        final JButton fiveWorstUtilitiesButton = new JButton(WORST_REVIEWERS_FOR_UTILITY);
        fiveWorstUtilitiesButton.addActionListener(e -> showRanking(WORST_REVIEWERS_FOR_UTILITY));
        rankingsButtonPanel.add(fiveWorstUtilitiesButton);
        final JButton fiveBestUtilitiesButton = new JButton(BEST_REVIEWERS_FOR_UTILITY);
        fiveBestUtilitiesButton.addActionListener(e -> showRanking(BEST_REVIEWERS_FOR_UTILITY));
        rankingsButtonPanel.add(fiveBestUtilitiesButton);
        final JButton fiveBestDirectorsButton = new JButton(BEST_DIRECTORS);
        fiveBestDirectorsButton.addActionListener(e -> showRanking(BEST_DIRECTORS));
        rankingsButtonPanel.add(fiveBestDirectorsButton);
        return rankingsButtonPanel;
    }

    private void showRanking(final String text) {
        this.remove(2);
        final JTable table = switch (text) {
            case BEST_REVIEWERS_FOR_NUMBER,
                 WORST_REVIEWERS_FOR_UTILITY,
                 BEST_REVIEWERS_FOR_UTILITY -> createRankingTable();
            case BEST_DIRECTORS -> createCastRankingTable();
            default -> throw new IllegalStateException("Unexpected value: " + text);
        };
        switch (text) {
            case BEST_REVIEWERS_FOR_NUMBER -> addBestReviewersRankingData(table);
            case WORST_REVIEWERS_FOR_UTILITY -> addWorstUtilitiesRankingData(table);
            case BEST_REVIEWERS_FOR_UTILITY -> addBestUtilitiesRankingData(table);
            case BEST_DIRECTORS -> addBestDirectorsRankingData(table);
            default -> throw new IllegalStateException("Unexpected value: " + text);
        }
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private JPanel getButtonPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addMemberButton = new JButton("Assegna Promo ai Migliori 5 Recensori");
        addMemberButton.addActionListener(e -> assignPromoBestFiveReviewersDialog());
        buttonPanel.add(addMemberButton);
        return buttonPanel;
    }

    private void assignPromoBestFiveReviewersDialog() {
        final JTextField promoCodeField = new JTextField(5);
        final DatePicker expirationField = new DatePicker();

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Scadenza:"));
        panel.add(expirationField);
        panel.add(new JLabel("Codice Promo:"));
        panel.add(promoCodeField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(promoCodeField.getText())
                    && isFieldFilled(expirationField.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        promoCodeField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            try {
                assignPromoBestFiveReviewers(
                        Integer.parseInt(promoCodeField.getText()),
                        expirationField.getDate()
                );
                JOptionPane.getRootFrame().dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore del database: " + ex.getMessage(),
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        final Object[] options = {okButton, "Cancel"};
        JOptionPane.showOptionDialog(null, panel, "Aggiungi Membro Cast",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void assignPromoBestFiveReviewers(final int promoCode, final LocalDate expiration) {
        ((AdminSessionController) this.getCurrentSessionContext().getController())
                .assignPromoBestFiveReviewers(
                        promoCode,
                        expiration,
                        ((AdminSessionController) this.getCurrentSessionContext().getController())
                                .getRankings("MigliorNumeroValutazioni"));
    }

    private JTable createCastRankingTable() {
        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Cognome");
        tableModel.addColumn("NumeroPresenze");
        final JTable table = super.createCustomTable(tableModel);
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(ROW_HEIGHT);
        this.customizeTableHeader(table);

        return table;
    }

    private JTable createRankingTable() {
        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Username");
        tableModel.addColumn("Valutazione");
        final JTable table = super.createCustomTable(tableModel);
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(ROW_HEIGHT);
        this.customizeTableHeader(table);

        return table;
    }

    private void addBestReviewersRankingData(final JTable table) {
        addRankingTableDataTwoAttributes(table, "MigliorNumeroValutazioni");
    }

    private void addWorstUtilitiesRankingData(final JTable table) {
        addRankingTableDataTwoAttributes(table, "PeggiorMediaUtilità");
    }

    private void addBestUtilitiesRankingData(final JTable table) {
        addRankingTableDataTwoAttributes(table, "MigliorMediaUtilità");
    }

    private void addBestDirectorsRankingData(final JTable table) {
        addRankingTableDataThreeAttributes(table);
    }

    private void addRankingTableDataTwoAttributes(final JTable table, final String evaluationType) {
        final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        final List<UserRanking> userRankings = ((AdminSessionController) this.getCurrentSessionContext().getController())
                .getRankings(evaluationType);
        for (final UserRanking userRanking : userRankings) {
            tableModel.addRow(new Object[]{
                    userRanking.username(),
                    userRanking.evaluation()
            });
        }
    }

    private void addRankingTableDataThreeAttributes(final JTable table) {
        final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        final List<CastRanking> castRankings = ((AdminSessionController) this.getCurrentSessionContext().getController())
                .getCastRankings(EvalType.BEST_DIRECTORS);
        for (final CastRanking castRanking : castRankings) {
            tableModel.addRow(new Object[]{
                    castRanking.name(),
                    castRanking.surname(),
                    castRanking.evaluation()
            });
        }
    }

    private boolean isFieldFilled(final String text) {
        return !text.isBlank();
    }

}
