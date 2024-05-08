package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.film.Film;
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
 * Film view of the user.
 */
public final class AdminFilmView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -2417782855116749418L;
    private final JTable filmTable;

    /**
     * Constructor of the admin film view.
     *
     * @param currentSessionContext The context of the current admin.
     */
    public AdminFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        // Adds the welcome label to the view
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina dei film.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        // Adds the film table to the view
        this.filmTable = super
                .createFilmTable();
        final JScrollPane scrollPane = new JScrollPane(filmTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        updateFilmTable();
    }

    private void updateFilmTable() {
        final DefaultTableModel model = (DefaultTableModel) this.filmTable.getModel();
        model.setRowCount(0);
        for (final Film film : ((AdminSessionController) this.getCurrentSessionContext().getController()).getFilms()) {
            model.addRow(new Object[]{
                    film.getFilmId(),
                    film.getTitle(),
                    film.getAgeLimit(),
                    film.getPlot(),
                    film.getDuration()});
        }
    }
}
