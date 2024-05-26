package unibo.cineradar.view.homepage.admin;

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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Rankings view of the Admin.
 */
public class AdminRankingsView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -924897837382651458L;
    private static final int ROW_HEIGHT = 30;
    private static final String ERROR = "Errore";
    private static final String COMPLETE_DELETE = "Eliminazione completata";
    private static final int SIZE = 18;
    private final JTable fiveBestReviewersTable;
    private final JTable fiveWorstUtilitiesTable;
    private final JTable fiveBestUtilitiesTable;

    /**
     * Constructor of the admin rankings view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminRankingsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.setLayout(new BorderLayout());

        final JLabel lblBestReviewers = createLabel("I 5 migliori recensori per numero di recensioni");
        final JLabel lblWorstUtilities = createLabel("I 5 peggiori recensori per punteggio di utilita'");
        final JLabel lblBestUtilities = createLabel("I 5 migliori recensori per punteggio di utilita'");
        final JLabel lblBestDirectors = createLabel("I 5 migliori registi per numero di presenze nei cast");

        this.fiveBestReviewersTable = createRankingTable();
        this.fiveWorstUtilitiesTable = createRankingTable();
        this.fiveBestUtilitiesTable = createRankingTable();
        final JTable fiveBestDirectorsTable = createCastRankingTable();

        final JScrollPane scrollPane1 = new JScrollPane(fiveBestReviewersTable);
        final JScrollPane scrollPane2 = new JScrollPane(fiveWorstUtilitiesTable);
        final JScrollPane scrollPane3 = new JScrollPane(fiveBestUtilitiesTable);
        final JScrollPane scrollPane4 = new JScrollPane(fiveBestDirectorsTable);

        final JPanel gridPanel = new JPanel(new GridLayout(4, 2));
        gridPanel.add(lblBestReviewers);
        gridPanel.add(scrollPane1);
        gridPanel.add(lblWorstUtilities);
        gridPanel.add(scrollPane2);
        gridPanel.add(lblBestUtilities);
        gridPanel.add(scrollPane3);
        gridPanel.add(lblBestDirectors);
        gridPanel.add(scrollPane4);

        this.add(gridPanel, BorderLayout.CENTER);

        addBestReviewersRankingData(fiveBestReviewersTable);
        addWorstUtilitiesRankingData(fiveWorstUtilitiesTable);
        addBestUtilitiesRankingData(fiveBestUtilitiesTable);
        addBestDirectorsRankingData(fiveBestDirectorsTable);

        final JPanel buttonPanel = getButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addMemberButton = new JButton("Assegna Promo ai Migliori 5 Recensori");
        addMemberButton.addActionListener(e -> assignPromoBestFiveReviewersDialog());
        buttonPanel.add(addMemberButton);
        final JButton deleteMemberButton = new JButton("Elimina Utente");
        deleteMemberButton.addActionListener(e -> deleteUserDialog());
        buttonPanel.add(deleteMemberButton);
        return buttonPanel;
    }

    private JLabel createLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, SIZE));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void updateView() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel fiveBestReviewersTableModel = (DefaultTableModel) this.fiveBestReviewersTable.getModel();
            fiveBestReviewersTableModel.setRowCount(0);
            addBestReviewersRankingData(fiveBestReviewersTable);
            final DefaultTableModel fiveWorstUtilitiesTableModel = (DefaultTableModel) this.fiveWorstUtilitiesTable.getModel();
            fiveWorstUtilitiesTableModel.setRowCount(0);
            addWorstUtilitiesRankingData(fiveWorstUtilitiesTable);
            final DefaultTableModel fiveBestUtilitiesTableModel = (DefaultTableModel) this.fiveBestUtilitiesTable.getModel();
            fiveBestUtilitiesTableModel.setRowCount(0);
            addWorstUtilitiesRankingData(fiveBestUtilitiesTable);
        });
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
                updateView();
                JOptionPane.showMessageDialog(
                        null,
                        "L'utente e' stato eliminato con successo.",
                        COMPLETE_DELETE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante l'eliminazione dell'utente.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci un numero valido per Username.",
                    ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean deleteUser(final String username) {
        return ((AdminSessionController) this.getCurrentSessionContext().getController())
                .deleteUser(username);
    }

    private void assignPromoBestFiveReviewersDialog() {
        final JTextField promoCodeField = new JTextField(5);
        final JTextField expirationField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Codice Promo:"));
        panel.add(promoCodeField);
        panel.add(new JLabel("Scadenza:"));
        panel.add(expirationField);

        final JButton okButton = new JButton("OK");
        okButton.setEnabled(false);
        final Runnable checkFields = () -> {
            final boolean allFilled = isFieldFilled(promoCodeField.getText())
                    && isFieldFilled(expirationField.getText());
            okButton.setEnabled(allFilled);
        };
        final DocumentListener listener = new ViewDocumentListener(checkFields);
        promoCodeField.getDocument().addDocumentListener(listener);
        expirationField.getDocument().addDocumentListener(listener);

        okButton.addActionListener(e -> {
            assignPromoBestFiveReviewers(
                    Integer.parseInt(promoCodeField.getText()),
                    LocalDate.parse(expirationField.getText())
            );
            JOptionPane.getRootFrame().dispose();
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
