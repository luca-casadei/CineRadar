package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Serie view of the user.
 */
public final class AdminSerieView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = 2801951662303493283L;
    private final JTable serieTable;

    /**
     * Constructor of the admin TV series view.
     *
     * @param currentSessionContext The context of the current admin.
     */
    public AdminSerieView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle serie.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        this.serieTable = super.createTable(((AdminSessionController) currentSessionContext.getController()).getSeries());
        final JScrollPane scrollPane = new JScrollPane(serieTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        updateSeriesTable();
    }

    private void updateSeriesTable() {
        final DefaultTableModel model = (DefaultTableModel) this.serieTable.getModel();
        model.setRowCount(0);
        for (final Serie serie : ((AdminSessionController) this.getCurrentSessionContext().getController()).getSeries()) {
            model.addRow(new Object[]{
                    serie.getId(),
                    serie.getTitle(),
                    serie.getAgeLimit(),
                    serie.getPlot(),
                    serie.getDuration()});
        }
    }
}
// CHECKSTYLE: MagicNumber ON
