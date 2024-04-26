package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.view.ViewContext;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

// CHECKSTYLE: MagicNumber OFF

/**
 * The panel used in the user part.
 */
public abstract class AdminPanel extends JPanel {
    private static final long serialVersionUID = 2L;

    private final ViewContext currentSessionContext;

    /**
     * Constructs an istance of AdminPanel.
     *
     * @param currentSessionContext The session context of the admin.
     */
    protected AdminPanel(final ViewContext currentSessionContext) {
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
        final DefaultTableModel filmTableModel = new DefaultTableModel();
        filmTableModel.addColumn("ID");
        filmTableModel.addColumn("Titolo");
        filmTableModel.addColumn("Limite di eta'");
        filmTableModel.addColumn("Trama");
        filmTableModel.addColumn("Durata(min)");

        // Adds film data to the model
        for (final Multimedia multimedia : multimediaList) {
            filmTableModel.addRow(new Object[]{
                    multimedia.getId(),
                    multimedia.getTitle(),
                    multimedia.getAgeLimit(),
                    multimedia.getPlot(),
                    multimedia.getDuration()});
        }

        final JTable table = this.createCustomTable(filmTableModel);

        // Sets renderer to center-align cell contents
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setRowHeight(30);

        this.customizeTableHeader(table);

        return table;
    }

    /**
     * Creates a JTable with a custom renderer to alternate row colors.
     *
     * @param filmTableModel The data model to use for the table.
     * @return The JTable with the custom renderer.
     */
    private JTable createCustomTable(final TableModel filmTableModel) {
        // Crea la tabella con il modello dei dati fornito
        final JTable table = new JTable(filmTableModel);

        // Imposta il renderer personalizzato per alternare i colori delle righe
        table.setDefaultRenderer(
                Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    final JTable table,
                    final Object value,
                    final boolean isSelected,
                    final boolean hasFocus,
                    final int row,
                    final int column) {
                // Ottiene il componente renderer di default
                final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Imposta il colore di sfondo basato sulla selezione della riga
                if (isSelected) {
                    // Colore per riga selezionata
                    component.setBackground(new Color(254, 250, 246));
                } else if (row % 2 == 0) {
                    // Colore per riga pari
                    component.setBackground(Color.WHITE);
                } else {
                    // Colore per riga dispari
                    component.setBackground(new Color(240, 240, 240));
                }
                return component;
            }
        });
        return table;
    }

    /**
     * Customizes the header of the given JTable by setting the font, foreground color, and background color.
     *
     * @param table The JTable whose header is to be customized.
     */
    private void customizeTableHeader(final JTable table) {
        final JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setForeground(Color.BLACK);
        header.setBackground(Color.WHITE);
        header.setOpaque(true);
    }
}

// CHECKSTYLE: MagicNumber ON
