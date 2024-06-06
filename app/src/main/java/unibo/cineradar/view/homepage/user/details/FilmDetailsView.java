package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.review.WriteFilmReviewView;
import unibo.cineradar.view.homepage.user.review.WriteReviewView;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A view to display detailed information about a film.
 */
public final class FilmDetailsView extends DetailsView {
    @Serial
    private static final long serialVersionUID = -5729493403413904557L;
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 400;
    private static final int VERTICAL_MARGIN = 5;

    private final transient UserSessionController uc;
    private final transient Film detailedFilm;
    private final transient Cast detailedFilmCast;

    /**
     * Constructs a new FilmDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param filmId                The id of the detailed film.
     */
    public FilmDetailsView(final ViewContext currentSessionContext, final int filmId) {
        super(currentSessionContext);
        this.uc = (UserSessionController) currentSessionContext.getController();
        final Map<Film, Cast> detailedFilms =
                this.uc.getDetailedFilms();
        for (final Map.Entry<Film, Cast> entry : detailedFilms.entrySet()) {
            if (entry.getKey().getFilmId() == filmId) {
                this.detailedFilm = entry.getKey();
                this.detailedFilmCast = entry.getValue();
                initComponents(currentSessionContext);
                return;
            }
        }

        throw new IllegalStateException("Detailed film or detailed film cast not initialized");
    }

    private void initComponents(final ViewContext currentSessionContext) {
        setTitle(detailedFilm.getTitle());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final JPanel mainPanel = new JPanel(new BorderLayout());

        // Film details panel
        final JPanel filmDetailsPanel = new JPanel(new GridLayout(0, 2));
        filmDetailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli film"));
        filmDetailsPanel.add(new JLabel("Titolo:"));
        filmDetailsPanel.add(new JLabel(detailedFilm.getTitle()));
        filmDetailsPanel.add(new JLabel("Limite di eta':"));
        filmDetailsPanel.add(new JLabel(String.valueOf(detailedFilm.getAgeLimit())));
        filmDetailsPanel.add(new JLabel("Trama:"));
        filmDetailsPanel.add(new JLabel(detailedFilm.getPlot()));
        filmDetailsPanel.add(new JLabel("Durata (min):"));
        filmDetailsPanel.add(new JLabel(String.valueOf(detailedFilm.getDuration())));
        filmDetailsPanel.add(new JLabel("Generi:"));
        final String genres = detailedFilm.getGenres().stream()
                .map(Genre::name)
                .reduce((g1, g2) -> g1 + ", " + g2)
                .orElse("N/A");
        filmDetailsPanel.add(new JLabel(genres));

        mainPanel.add(filmDetailsPanel, BorderLayout.NORTH);

        // Cast details panel
        final JPanel castPanel = new JPanel();
        castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));
        castPanel.setBorder(BorderFactory.createTitledBorder("Cast"));
        castPanel.setAlignmentX(LEFT_ALIGNMENT);

        if (detailedFilmCast != null) {
            final List<CastMember> castMembers = detailedFilmCast.getCastMemberList();
            for (final CastMember castMember : castMembers) {
                final JLabel castMemberLabel = new JLabel(castMember.getName()
                        + " "
                        + castMember.getLastName()
                        + " - "
                        + castMember.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                castPanel.add(castMemberLabel);
                castPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN));
            }
        }

        // Reviews panel
        final JPanel reviewsPanel = super.getReviewsPanel(this.uc.getFilmReviews(detailedFilm.getFilmId()));

        // Combine cast and reviews panel
        final JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(castPanel, BorderLayout.NORTH);
        centerPanel.add(reviewsPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        final JPanel bottomPanel = getBottomPanel(currentSessionContext);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel getBottomPanel(final ViewContext currentSessionContext) {
        final JCheckBox cb = getViewedSelector();
        final JButton reviewButton = new JButton();
        final boolean notReviewedYet = Objects.isNull(uc.getFullFilmReview(detailedFilm.getFilmId(),
                uc.getAccount().getUsername()));

        if (notReviewedYet) {
            reviewButton.setText("Recensisci");
        } else {
            reviewButton.setText("Film gia' recensito");
            reviewButton.setEnabled(false);
        }

        cb.addActionListener(e -> reviewButton.setEnabled(cb.isSelected() && notReviewedYet));
        reviewButton.setEnabled(cb.isSelected() && notReviewedYet);
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final WriteReviewView writeFilmReviewView = new WriteFilmReviewView(currentSessionContext, detailedFilm);
                writeFilmReviewView.setVisible(true);
            }
        });

        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(cb, BorderLayout.WEST);
        bottomPanel.add(reviewButton, BorderLayout.CENTER);
        return bottomPanel;
    }

    private JCheckBox getViewedSelector() {
        final JCheckBox cb = new JCheckBox("Gia' visto!");
        cb.setHorizontalAlignment(SwingConstants.CENTER);
        cb.setSelected(this.uc.isFilmViewed(detailedFilm.getFilmId()));
        cb.addActionListener(e -> {
            final JCheckBox cbe = (JCheckBox) e.getSource();
            if (cb.isSelected()) {
                if (!this.uc.visualizeFilm(detailedFilm.getFilmId())) {
                    cbe.setSelected(false);
                }
            } else {
                if (!this.uc.forgetFilm(detailedFilm.getFilmId())) {
                    cbe.setSelected(true);
                }
            }
        });
        return cb;
    }
}
