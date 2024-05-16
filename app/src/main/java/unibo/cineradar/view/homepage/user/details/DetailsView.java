package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SeriesReview;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private final transient ViewContext viewContext;

    /**
     * Constructs a details view instance.
     *
     * @param currentSessionContext The current session context.
     */
    public DetailsView(final ViewContext currentSessionContext) {
        this.viewContext = currentSessionContext;
    }

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

        final TableCellRenderer buttonRenderer = (table1, value, isSelected, hasFocus, row, column)
                -> new JButton("Vedi dettagli");

        class ButtonEditor extends DefaultCellEditor {
            @Serial
            private static final long serialVersionUID = 6530498035927369718L;
            private final JButton button;
            private Review review;

            ButtonEditor() {
                super(new JCheckBox());
                button = new JButton("Vedi dettagli");
                button.addActionListener(e -> {
                    if (review != null) {
                        final ReviewDetailsView reviewDetailsView;
                        if (review instanceof FilmReview) {
                            reviewDetailsView = new ReviewDetailsView(
                                    viewContext, (FilmReview) review, ((FilmReview) review).getUsername()
                            );
                        } else if (review instanceof SeriesReview) {
                            reviewDetailsView = new ReviewDetailsView(
                                    viewContext, (SeriesReview) review, ((SeriesReview) review).getUsername()
                            );
                        } else {
                            throw new IllegalStateException();
                        }
                        reviewDetailsView.setVisible(true);
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(final JTable table, final  Object value,
                                                         final boolean isSelected, final int row, final int column) {
                if (value instanceof Review) {
                    review = (Review) value;
                }
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }
        }

        table.getColumnModel().getColumn(3).setCellRenderer(buttonRenderer);
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor());

        final JScrollPane scrollPane = new JScrollPane(table);
        reviewsPanel.add(scrollPane, BorderLayout.CENTER);

        return reviewsPanel;
    }
}
