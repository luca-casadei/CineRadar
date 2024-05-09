package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.model.review.Review;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.Serial;
import java.util.List;

/**
 * A generic DetailsView class.
 */
public abstract class DetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = 6530405035927369718L;

    final JPanel getReviewsPanel(final List<Review> reviews) {
        final JPanel reviewsPanel = new JPanel(new BorderLayout());
        reviewsPanel.setBorder(BorderFactory.createTitledBorder("Recensioni"));


        final String[] columnNames = {"Username", "Titolo Recensione", "Voto complessivo", ""};
        final Object[][] data = new Object[reviews.size()][4];

        for (int i = 0; i < reviews.size(); i++) {
            final Review review = reviews.get(i);
            data[i][0] = review.getUsername();
            data[i][1] = review.getTitle();
            data[i][2] = review.getOverallRating();
            data[i][3] = review;
        }

        final DefaultTableModel model = new DefaultTableModel(data, columnNames);
        final JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);

        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(3).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            final JButton button = new JButton("Vedi dettagli");
            button.addActionListener(e -> {
                final Review review = (Review) value;
                // TODO: Visualizza i dettagli della recensione
                JOptionPane.showMessageDialog(null,
                        review.getDescription(),
                        "Dettagli recensione",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            return button;
        });

        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(
                    final JTable table, final Object value, final boolean isSelected, final int row, final int column
            ) {
                return table.getCellRenderer(row, column).getTableCellRendererComponent(
                        table, value, isSelected, true, row, column
                );
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }
        });

        final JScrollPane scrollPane = new JScrollPane(table);
        reviewsPanel.add(scrollPane, BorderLayout.CENTER);

        return reviewsPanel;
    }
}
