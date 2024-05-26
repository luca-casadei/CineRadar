package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SeriesReview;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.details.FilmDetailsView;
import unibo.cineradar.view.homepage.user.details.ReviewDetailsView;
import unibo.cineradar.view.homepage.user.details.SeriesDetailsView;

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
import java.io.Serial;
import java.util.List;
import java.util.Objects;

// CHECKSTYLE: MagicNumber OFF

/**
 * The panel used in the user part.
 */
public abstract class UserPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 8510766881767725989L;

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
     * @param model The table model to use.
     * @return The created JTable.
     */
    private JTable createStyledTable(final DefaultTableModel model) {
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

        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        table.setRowHeight(30);

        final JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setForeground(Color.BLACK);
        header.setBackground(Color.WHITE);
        header.setOpaque(true);
        table.setBackground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setDefaultEditor(Object.class, null);


        return table;
    }

    /**
     * Creates a table of multimedia items.
     *
     * @param multimediaList The list of multimedia items.
     * @return A JTable of multimedia items.
     */
    private JTable createMultimediaTable(final List<? extends Multimedia> multimediaList) {
        final DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titolo");
        model.addColumn("Limite di eta'");
        model.addColumn("Trama");
        model.addColumn("Durata (min)");

        for (final Multimedia multimedia : multimediaList) {
            model.addRow(new Object[]{multimedia instanceof Film film
                    ? film.getFilmId() : multimedia instanceof Serie serie
                    ? serie.getSeriesId() : -1,
                    multimedia.getTitle(),
                    multimedia.getAgeLimit(), multimedia.getPlot(), multimedia.getDuration()});
        }

        return createStyledTable(model);
    }

    /**
     * Creates a table of preferred multimedia items.
     *
     * @param multimediaList  The list of preferred multimedia items.
     * @param preferredGenres The list of preferred genres of the user.
     * @return A JTable of multimedia items.
     */
    private JTable createPreferredMultimediaTable(final List<? extends Multimedia> multimediaList,
                                                  final List<Genre> preferredGenres) {
        final DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titolo");
        model.addColumn("Limite di eta'");
        model.addColumn("Trama");
        model.addColumn("Durata (min)");

        for (final Multimedia multimedia : multimediaList) {
            final boolean isPreferred = multimedia.getGenres().stream().anyMatch(preferredGenres::contains);
            if (isPreferred) {
                model.addRow(new Object[]{
                        multimedia instanceof Film ? ((Film) multimedia).getFilmId()
                                : multimedia instanceof Serie ? ((Serie) multimedia).getSeriesId() : -1,
                        multimedia.getTitle(),
                        multimedia.getAgeLimit(),
                        multimedia.getPlot(),
                        multimedia.getDuration()
                });
            }
        }

        return createStyledTable(model);
    }

    /**
     * Creates a table of films.
     *
     * @param age The limited age to be respected.
     * @return A JTable of films.
     */
    protected JTable createFilmTable(final int age) {
        final JTable filmTable = createMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getFilms(age)
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
     * @param age The limited age to be respected.
     * @return A JTable of series.
     */
    protected JTable createSerieTable(final int age) {
        final JTable serieTable = createMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getSeries(age)
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
     * Creates a table of preferred films based on user's preferred genres.
     *
     * @param age             The limited age to be respected.
     * @param preferredGenres The list of preferred genres of the user.
     * @return A JTable of films filtered by preferred genres.
     */
    protected JTable createPreferredFilmTable(final int age, final List<Genre> preferredGenres) {
        final JTable filmTable = createPreferredMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getFilms(age),
                preferredGenres
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
     * Creates a table of preferred series based on user's preferred genres.
     *
     * @param age             The limited age to be respected.
     * @param preferredGenres The list of preferred genres of the user.
     * @return A JTable of series filtered by preferred genres.
     */
    protected JTable createPreferredSerieTable(final int age, final List<Genre> preferredGenres) {
        final JTable serieTable = createPreferredMultimediaTable(
                ((UserSessionController) currentSessionContext.getController()).getSeries(age),
                preferredGenres
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
        model.addColumn("Tipologia");
        model.addColumn("Titolo multimedia");
        model.addColumn("Titolo recensione");
        model.addColumn("Voto complessivo");

        // Adds review data to the model
        for (final Review review : reviewList) {
            final String multimediaId;
            final String multimediaTitle;
            final String multimediaType;
            if (review instanceof FilmReview filmReview) {
                multimediaId = String.valueOf(filmReview.getIdFilm());
                multimediaTitle = filmReview.getFilmTitle();
                multimediaType = "Film";
            } else if (review instanceof SeriesReview seriesReview) {
                multimediaId = String.valueOf(seriesReview.getIdSerie());
                multimediaTitle = seriesReview.getSerieTitle();
                multimediaType = "Serie";
            } else {
                throw new IllegalArgumentException();
            }
            model.addRow(new Object[]{
                    multimediaId,
                    multimediaType,
                    multimediaTitle,
                    review.getTitle(),
                    review.getOverallRating()
            });
        }
        final JTable reviewTable = createStyledTable(model);
        final ListSelectionListener reviewSelectionListener = e -> {
            if (!e.getValueIsAdjusting()) {
                final int selectedRow = ((DefaultListSelectionModel) e.getSource()).getLeadSelectionIndex();
                if (selectedRow != -1) {
                    if (Objects.equals((String) model.getValueAt(selectedRow, 1), "Film")) {
                        openReviewDetailsView(
                                this.getCurrentSessionContext(),
                                ((UserSessionController) currentSessionContext.getController()).getFullFilmReview(
                                        Integer.parseInt((String) model.getValueAt(selectedRow, 0)),
                                        ((UserSessionController) currentSessionContext.getController()).getAccount().getUsername()
                                )
                        );
                    } else if (Objects.equals((String) model.getValueAt(selectedRow, 1), "Serie")) {
                        openReviewDetailsView(
                                this.getCurrentSessionContext(),
                                ((UserSessionController) currentSessionContext.getController()).getFullSeriesReview(
                                        Integer.parseInt((String) model.getValueAt(selectedRow, 0)),
                                        ((UserSessionController) currentSessionContext.getController()).getAccount().getUsername()
                                )
                        );
                    }
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
        final SeriesDetailsView seriesDetailsView = new SeriesDetailsView(currentSessionContext, serieId);
        seriesDetailsView.setVisible(true);
    }

    private void openReviewDetailsView(final ViewContext currentSessionContext,
                                       final Review review) {
        final ReviewDetailsView reviewDetailsView = new ReviewDetailsView(
                currentSessionContext,
                review,
                review.getUsername()
        );
        reviewDetailsView.setVisible(true);
    }
}

// CHECKSTYLE: MagicNumber ON
