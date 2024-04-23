package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.view.ViewContext;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
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
 * The panel used in the user part.
 */
public abstract class UserPanel extends JPanel {
    private static final long serialVersionUID = 1L; // TODO: sostituire

    private final ViewContext currentSessionContext;

    /**
     * Constructs an istance of UserPanel.
     *
     * @param currentSessionContext The session context of the user.
     */
    protected UserPanel(final ViewContext currentSessionContext) {
        this.currentSessionContext = currentSessionContext;
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }

    /**
     * Retrieves the current session context.
     *
     * @return The current session context.
     */
    protected ViewContext getCurrentSessionContext() {
        return this.currentSessionContext;
    }

    /**
     * Creates the table of the multimedia.
     *
     * @param multimediaList The list of multimedia.
     * @return A JTable of a multimedia list.
     */
    protected JTable createTable(final List<? extends Multimedia> multimediaList) {
        // Creates the table model
        final DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titolo");
        model.addColumn("Limite di eta'");
        model.addColumn("Trama");
        model.addColumn("Durata(min)");

        // Adds film data to the model
        for (final Multimedia multimedia : multimediaList) {
            model.addRow(new Object[]{multimedia.getId(), multimedia.getTitle(),
                    multimedia.getAgeLimit(), multimedia.getPlot(), multimedia.getDuration()});
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
