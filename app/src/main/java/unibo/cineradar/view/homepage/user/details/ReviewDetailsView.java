package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.FullSeriesReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SeriesReview;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a review.
 */
public class ReviewDetailsView extends DetailsView {
    @Serial
    private static final long serialVersionUID = 314494446269420625L;

    private final transient Review review;

    /**
     * Constructs a new ReviewDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param review                The review to display details of.
     * @param username              The username of the user that made the review.
     */
    public ReviewDetailsView(final ViewContext currentSessionContext,
                             final Review review,
                             final String username) {
        super(currentSessionContext);
        final UserSessionController uc = (UserSessionController) currentSessionContext.getController();
        if (review instanceof FilmReview) {
            this.review = uc.getFullFilmReview(((FilmReview) review).getIdFilm(), username);
        } else if (review instanceof SeriesReview) {
            this.review = uc.getFullSeriesReview(((SeriesReview) review).getIdSerie(), username);
        } else {
            throw new IllegalStateException();
        }
        initComponents();
    }

    private void initComponents() {
        setTitle("Dettagli Recensione");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        final JPanel mainPanel = new JPanel(new BorderLayout());

        // Title panel
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Titolo: "));
        titlePanel.add(new JLabel(review.getTitle()));
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Description panel
        final JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Descrizione"));
        final JTextArea descriptionArea = new JTextArea(review.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        final JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);
        mainPanel.add(descriptionPanel, BorderLayout.CENTER);

        // Ratings panel
        final JPanel ratingsPanel = new JPanel(new GridLayout(0, 2));
        ratingsPanel.setBorder(BorderFactory.createTitledBorder("Voti sezione"));


        // Add each section and its rating
        if (this.review instanceof FilmReview) {
            ((FullFilmReview) review).getSections().forEach(e -> {
                ratingsPanel.add(new JLabel(e.section().getName()));
                ratingsPanel.add(new JLabel(String.valueOf(e.score())));
            });
        } else if (review instanceof SeriesReview) {
            ((FullSeriesReview) review).getSections().forEach(e -> {
                ratingsPanel.add(new JLabel(e.section().getName()));
                ratingsPanel.add(new JLabel(String.valueOf(e.score())));
            });
        } else {
            throw new IllegalStateException();
        }

        mainPanel.add(ratingsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}

// CHECKSTYLE: MagicNumber OFF
