package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.film.Film;
import unibo.cineradar.view.ViewContext;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

// CHECKSTYLE: MagicNumber OFF

/**
 * Film view of the user.
 */
public final class UserFilmView extends UserPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the user film view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserFilmView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        // Adds the welcome label to the view
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina dei film.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        // Adds the film table to the view
        final JTable filmTable = createFilmTable(currentSessionContext);
        final JScrollPane scrollPane = new JScrollPane(filmTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private JTable createFilmTable(final ViewContext currentSessionContext) {
        // Retrieves the list of films from the controller
        final List<Film> films = currentSessionContext.getController().getFilms();

        // Creates the table model
        final DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titolo");
        model.addColumn("Limite di eta'");
        model.addColumn("Trama");
        model.addColumn("Durata(min)");

        // Adds film data to the model
        for (final Film film : films) {
            /*if (this.getCurrentSessionContext().getController().getUserType().equals(LoginType.USER)) {
                if (this.getCurrentSessionContext().getController().getAccountDetails().get() >= film.getAgeLimit()){
                   TODO: controllo sull'età, bisogna fare in modo di prendere l'età dall'account(USER).
                }

            }*/
            model.addRow(new Object[]{film.getId(), film.getTitle(), film.getAgeLimit(), film.getPlot(), film.getDuration()});
        }

        // Creates the table with custom renderer for alternating row colors
        final JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(final TableCellRenderer renderer, final int row, final int column) {
                final Component component = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    component.setBackground(new Color(254, 250, 246));
                } else {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };

        // Sets renderer to center-align cell contents
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Sets row height
        table.setRowHeight(30);

        // Customizes the table header
        final JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setForeground(Color.BLACK);
        header.setBackground(Color.WHITE);
        header.setOpaque(true);
        table.setBackground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        return table;
    }
}

// CHECKSTYLE: MagicNumber ON
