package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.FullSeriesReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SeriesReview;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Optional;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a review.
 */
public class ReviewDetailsView extends DetailsView {
    @Serial
    private static final long serialVersionUID = 314494446269420625L;

    private final transient Review review;
    private final String usernameOwnerReview;
    private JButton likeButton;
    private JButton dislikeButton;

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
        this.usernameOwnerReview = username;
        if (review instanceof FilmReview) {
            this.review = uc.getFullFilmReview(((FilmReview) review).getIdFilm(), username);
        } else if (review instanceof SeriesReview) {
            this.review = uc.getFullSeriesReview(((SeriesReview) review).getIdSerie(), username);
        } else {
            throw new IllegalStateException();
        }
        initComponents(uc);
    }

    private void initComponents(final UserSessionController uc) {
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
        if (this.review instanceof FullFilmReview fr) {
            fr.getSections().forEach(e -> {
                ratingsPanel.add(new JLabel(e.section().name()));
                ratingsPanel.add(new JLabel(String.valueOf(e.score())));
            });
        } else if (this.review instanceof FullSeriesReview sr) {
            sr.getSections().forEach(e -> {
                ratingsPanel.add(new JLabel(e.section().name()));
                ratingsPanel.add(new JLabel(String.valueOf(e.score())));
            });
        } else {
            throw new IllegalStateException();
        }

        final JPanel buttonPanel = new JPanel();
        likeButton = new JButton("Like");
        dislikeButton = new JButton("Dislike");

        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                likeButton.setEnabled(false);
                dislikeButton.setEnabled(true);
                if (review instanceof FilmReview fr) {
                    uc.evaluateFilmRec(usernameOwnerReview,
                            uc.getAccount().getUsername(),
                            fr.getIdFilm(),
                            true);
                } else if (review instanceof SeriesReview sr) {
                    uc.evaluateSerieRec(usernameOwnerReview,
                            uc.getAccount().getUsername(),
                            sr.getIdSerie(),
                            true);
                }
            }
        });

        dislikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                likeButton.setEnabled(true);
                dislikeButton.setEnabled(false);
                if (review instanceof FilmReview) {
                    uc.evaluateFilmRec(usernameOwnerReview,
                            uc.getAccount().getUsername(),
                            ((FilmReview) review).getIdFilm(),
                            false);
                } else if (review instanceof SeriesReview) {
                    uc.evaluateSerieRec(usernameOwnerReview,
                            uc.getAccount().getUsername(),
                            ((SeriesReview) review).getIdSerie(),
                            false);
                }
            }
        });

        Optional<Boolean> resEvaluatedUseful = Optional.empty();
        if (review instanceof FilmReview fr) {
            resEvaluatedUseful = uc.findFilmRecEvaluated(usernameOwnerReview,
                    uc.getAccount().getUsername(),
                    fr.getIdFilm());
        } else if (review instanceof SeriesReview sr) {
            resEvaluatedUseful = uc.findSerieRecEvaluated(usernameOwnerReview,
                    uc.getAccount().getUsername(),
                    sr.getIdSerie());
        }

        resEvaluatedUseful.ifPresentOrElse(b -> {
            likeButton.setEnabled(!b);
            dislikeButton.setEnabled(b);
        }, () -> {
            likeButton.setEnabled(true);
            dislikeButton.setEnabled(true);
        });

        buttonPanel.add(likeButton);
        buttonPanel.add(dislikeButton);

        final JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(ratingsPanel, BorderLayout.CENTER);
        combinedPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(combinedPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }
}

// CHECKSTYLE: MagicNumber OFF
