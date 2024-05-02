package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SerieReview;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.details.FilmDetailsView;
import unibo.cineradar.view.homepage.user.details.ReviewDetailsView;
import unibo.cineradar.view.homepage.user.details.SerieDetailsView;

import javax.swing.DefaultListSelectionModel;
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
     * Constructs an instance of UserPanel.
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
     * @return The created JTable.
     */
    private JTable createStyledTable(final DefaultTableModel model) {
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
        //table.getSelectionModel().addListSelectionListener(actionListener);

        return table;
    }

    /**
     * Creates a table of multimedia items.
     *
     * @param multimediaList The list of multimedia items.
     * @return A JTable of multimedia items.
     */
    private JTable createMultimediaTable(final List<? extends Multimedia> multimediaList) {
        // Creates the table model
        final DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titolo");
        model.addColumn("Limite di eta'");
        model.addColumn("Trama");
        model.addColumn("Durata (min)");

        // Adds multimedia data to the model
        for (final Multimedia multimedia : multimediaList) {
            model.addRow(new Object[]{multimedia.getId(), multimedia.getTitle(),
                    multimedia.getAgeLimit(), multimedia.getPlot(), multimedia.getDuration()});
        }

        return createStyledTable(model);
    }

    /**
     * Creates a table of films.
     *
     * @return A JTable of films.
     */
    protected JTable createFilmTable() {
        final JTable filmTable = createMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getFilms()
        );

        final ListSelectionListener filmSelectionListener = e -> {
            if (!e.getValueIsAdjusting()) {
                final int selectedRow = ((DefaultListSelectionModel) e.getSource()).getLeadSelectionIndex();
                if (selectedRow != -1) {
                    openFilmDetailsView(this.getCurrentSessionContext(), (int) filmTable.getValueAt(selectedRow, 0));
                }
            }
        };

        filmTable.getSelectionModel().addListSelectionListener(filmSelectionListener);

        return filmTable;
    }

    /**
     * Creates a table of series.
     *
     * @return A JTable of series.
     */
    protected JTable createSerieTable() {
        final JTable serieTable = createMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getSeries()
        );

        final ListSelectionListener serieSelectionListener = e -> {
            if (!e.getValueIsAdjusting()) {
                final int selectedRow = ((DefaultListSelectionModel) e.getSource()).getLeadSelectionIndex();
                if (selectedRow != -1) {
                    openSerieDetailsView(this.getCurrentSessionContext(), (int) serieTable.getValueAt(selectedRow, 0));
                }
            }
        };

        serieTable.getSelectionModel().addListSelectionListener(serieSelectionListener);

        return serieTable;
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
        model.addColumn("Codice multimedia");
        model.addColumn("Titolo multimedia");
        model.addColumn("Titolo recensione");
        model.addColumn("Descrizione");
        model.addColumn("Voto complessivo");

        // Adds review data to the model
        for (final Review review : reviewList) {
            final String multimediaId;
            final String multimediaTitle;
            if (review instanceof FilmReview) {
                final FilmReview filmReview = (FilmReview) review;
                multimediaId = filmReview.getIdFilm()
                        + " - [Film]";
                multimediaTitle = filmReview.getFilmTitle();
            } else if (review instanceof SerieReview) {
                final SerieReview serieReview = (SerieReview) review;
                multimediaId = serieReview.getIdSerie()
                        + " - [Serie]";
                multimediaTitle = serieReview.getSerieTitle();
            } else {
                throw new IllegalArgumentException();
            }
            model.addRow(new Object[]{
                    multimediaId,
                    multimediaTitle,
                    review.getTitle(),
                    review.getDescription(),
                    review.getOverallRating()
            });
        }
        final JTable reviewTable = createStyledTable(model);
        final ListSelectionListener reviewSelectionListener = e -> {
            if (!e.getValueIsAdjusting()) {
                final int selectedRow = ((DefaultListSelectionModel) e.getSource()).getLeadSelectionIndex();
                if (selectedRow != -1) {
                    openReviewDetailsView(this.getCurrentSessionContext());
                }
            }
        };

        reviewTable.getSelectionModel().addListSelectionListener(reviewSelectionListener);

        return reviewTable;
    }


    private void openFilmDetailsView(final ViewContext currentSessionContext, final int filmId) {
        final FilmDetailsView filmDetailsView = new FilmDetailsView(currentSessionContext, filmId);
        filmDetailsView.setVisible(true);
    }

    private void openSerieDetailsView(final ViewContext currentSessionContext, final int serieId) {
        final SerieDetailsView serieDetailsView = new SerieDetailsView(currentSessionContext, serieId);
        serieDetailsView.setVisible(true);
    }

    private void openReviewDetailsView(final ViewContext currentSessionContext) {
        final ReviewDetailsView reviewDetailsView = new ReviewDetailsView(currentSessionContext);
        reviewDetailsView.setVisible(true);
    }
}

// CHECKSTYLE: MagicNumber ON
