package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SerieReview;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.details.MultimediaDetailsView;
import unibo.cineradar.view.homepage.user.details.ReviewDetailsView;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
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
     * Creates a JTable with custom renderer for alternating row colors and the specified action listener.
     *
     * @param model          The table model to use.
     * @param actionListener The action listener for row selection events.
     * @return The created JTable.
     */
    protected JTable createStyledTable(final DefaultTableModel model, final ListSelectionListener actionListener) {
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

        // Set table as READ ONLY
        table.setDefaultEditor(Object.class, null);

        // Add action listener for row selection events
        table.getSelectionModel().addListSelectionListener(actionListener);

        return table;
    }

    /**
     * Creates the table of the multimedia.
     *
     * @param multimediaList The list of multimedia.
     * @return A JTable of a multimedia list.
     */
    protected JTable createMultimediaTable(final List<? extends Multimedia> multimediaList) {
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

        return createStyledTable(model, e -> {
            if (!e.getValueIsAdjusting()) {
                final int selectedRow = ((JTable) e.getSource()).getSelectedRow();
                if (selectedRow != -1) {
                    final int multimediaId = (int) ((JTable) e.getSource()).getValueAt(selectedRow, 0);

                    openMultimediaDetailsView(currentSessionContext, multimediaId);
                }
            }
        });
    }

    /**
     * Creates the table of the reviews.
     *
     * @param reviewList The list of reviews.
     * @return A JTable of a review list.
     */
    protected JTable createReviewTable(final List<Review> reviewList) {
        // Creates the table model
        final DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Titolo multimedia");
        model.addColumn("Titolo recensione");
        model.addColumn("Descrizione");
        model.addColumn("Voto complessivo");

        // Adds review data to the model
        for (final Review review : reviewList) {
            if (review instanceof FilmReview) {
                final FilmReview filmReview = (FilmReview) review;
                model.addRow(new Object[]{
                        filmReview.getFilmTitle(),
                        filmReview.getTitle(),
                        filmReview.getDescription(),
                        filmReview.getOverallRating()
                });
            } else {
                final SerieReview serieReview = (SerieReview) review;
                model.addRow(new Object[]{
                        serieReview.getSerieTitle(),
                        serieReview.getTitle(),
                        serieReview.getDescription(),
                        serieReview.getOverallRating()
                });
            }
        }

        return createStyledTable(model, e -> {
            if (!e.getValueIsAdjusting()) {
                final int selectedRow = ((JTable) e.getSource()).getSelectedRow();
                if (selectedRow != -1) {
                    // Get review ID from the table (assuming it's in the first column)
                    final int reviewId = (int) ((JTable) e.getSource()).getValueAt(selectedRow, 0);

                    openReviewDetailsView(currentSessionContext, reviewId);
                }
            }
        });
    }

    private void openMultimediaDetailsView(final ViewContext currentSessionContext, final Integer multimediaId) {
        final MultimediaDetailsView multimediaDetailsView = new MultimediaDetailsView(currentSessionContext, multimediaId);
        multimediaDetailsView.setVisible(true);
    }

    private void openReviewDetailsView(final ViewContext currentSessionContext, final Integer multimediaId) {
        final ReviewDetailsView reviewDetailsView = new ReviewDetailsView(currentSessionContext, multimediaId);
        reviewDetailsView.setVisible(true);
    }
}

// CHECKSTYLE: MagicNumber ON
