package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.ranking.CastRanking;
import unibo.cineradar.model.ranking.EvalType;
import unibo.cineradar.model.ranking.UserRanking;
import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.util.List;

/**
 * Rankings view of the Admin.
 */
public class AdminRankingsView extends AdminPanel {
    private static final long serialVersionUID = -924897837300851458L;
    private static final int ROW_HEIGHT = 30;

    /**
     * Constructor of the admin rankings view.
     *
     * @param currentSessionContext The session context of the current admin.
     */
    public AdminRankingsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        this.setLayout(new GridLayout(2, 2));

        final JLabel lblBestReviewers = new JLabel("I 5 migliori recensori per numero di recensioni");
        final JLabel lblWorstUtilities = new JLabel("I 5 peggiori recensori per punteggio di utilita'");
        final JLabel lblBestUtilities = new JLabel("I 5 migliori recensori per punteggio di utilita'");
        final JLabel lblBestDirectors = new JLabel("I 5 migliori registi per numero di presenze nei cast");

        final JTable fiveBestReviewersTable = createRankingTable();
        final JTable fiveWorstUtilitiesTable = createRankingTable();
        final JTable fiveBestUtilitiesTable = createRankingTable();
        final JTable fiveBestDirectorsTable = createCastRankingTable();

        final JScrollPane scrollPane1 = new JScrollPane(fiveBestReviewersTable);
        final JScrollPane scrollPane2 = new JScrollPane(fiveWorstUtilitiesTable);
        final JScrollPane scrollPane3 = new JScrollPane(fiveBestUtilitiesTable);
        final JScrollPane scrollPane4 = new JScrollPane(fiveBestDirectorsTable);

        this.add(lblBestReviewers);
        this.add(scrollPane1);
        this.add(lblWorstUtilities);
        this.add(scrollPane2);
        this.add(lblBestUtilities);
        this.add(scrollPane3);
        this.add(lblBestDirectors);
        this.add(scrollPane4);

        addBestReviewersRankingData(fiveBestReviewersTable);
        addWorstUtilitiesRankingData(fiveWorstUtilitiesTable);
        addBestUtilitiesRankingData(fiveBestUtilitiesTable);
        addBestDirectorsRankingData(fiveBestDirectorsTable);
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

}
