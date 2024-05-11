package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.review.WriteReviewView;
import unibo.cineradar.view.homepage.user.review.WriteSerieReviewView;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A view to display detailed information about a serie.
 */
public final class SeriesDetailsView extends DetailsView {
    @Serial
    private static final long serialVersionUID = 6530405035909369718L;

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 400;
    private static final int MARGIN = 5;

    private final transient UserSessionController uc;
    private JButton reviewButton;

    /**
     * Constructs a new SeriesDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param serieId               The ID of the series to get the details of.
     */
    public SeriesDetailsView(final ViewContext currentSessionContext, final int serieId) {
        super(currentSessionContext);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.uc = (UserSessionController) currentSessionContext.getController();

        final List<Serie> detailedSeries = uc.getDetailedSeries();

        for (final Serie actualSerie : detailedSeries) {
            if (actualSerie.getSeriesId() == serieId) {
                setTitle(actualSerie.getTitle());
                initComponents(currentSessionContext, actualSerie);
                return;
            }
        }

        throw new IllegalStateException("Detailed series not initialized for ID: " + serieId);
    }

    private void initComponents(final ViewContext currentSessionContext, final Serie serie) {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Serie details panel
        final JPanel serieDetailsPanel = new JPanel(new GridLayout(0, 2));
        serieDetailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli serie"));
        serieDetailsPanel.add(new JLabel("Titolo:"));
        serieDetailsPanel.add(new JLabel(serie.getTitle()));
        serieDetailsPanel.add(new JLabel("Limite di eta':"));
        serieDetailsPanel.add(new JLabel(String.valueOf(serie.getAgeLimit())));
        serieDetailsPanel.add(new JLabel("Trama:"));
        serieDetailsPanel.add(new JLabel(serie.getPlot()));
        serieDetailsPanel.add(new JLabel("Durata complessiva (min):"));
        serieDetailsPanel.add(new JLabel(String.valueOf(serie.getDuration())));
        serieDetailsPanel.add(new JLabel("Numero episodi:"));
        serieDetailsPanel.add(new JLabel(String.valueOf(serie.getNumberOfEpisodes())));

        mainPanel.add(serieDetailsPanel);

        // Seasons details panel
        final JPanel seasonsPanel = new JPanel();
        seasonsPanel.setLayout(new BoxLayout(seasonsPanel, BoxLayout.Y_AXIS));
        seasonsPanel.setBorder(BorderFactory.createTitledBorder("Stagioni"));

        for (final Season actualSeason : serie.getSeasons()) {
            final JPanel seasonPanel = new JPanel(new BorderLayout());
            seasonPanel.setBorder(BorderFactory.createTitledBorder("Stagione " + actualSeason.getId()));

            // Riassunto della stagione
            final JLabel summaryLabel = new JLabel(actualSeason.getSummary());
            summaryLabel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
            seasonPanel.add(summaryLabel, BorderLayout.NORTH);

            // Panel per membri del cast e episodi
            final JPanel castAndEpisodesPanel = new JPanel(new BorderLayout());

            // Cast
            final JPanel castPanel = new JPanel();
            castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));

            actualSeason.getCast();
            final List<CastMember> castMembers = actualSeason.getCast().getCastMemberList();
            for (final CastMember castMember : castMembers) {
                final JLabel castMemberLabel = new JLabel(castMember.getName()
                        + " "
                        + castMember.getLastName()
                        + " - "
                        + castMember.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                castPanel.add(castMemberLabel);
                castPanel.add(Box.createVerticalStrut(MARGIN));
            }

            castAndEpisodesPanel.add(new JScrollPane(castPanel), BorderLayout.CENTER);

            // Episodi
            final JPanel episodesPanel = new JPanel(new GridLayout(0, 3));
            episodesPanel.setBorder(BorderFactory.createTitledBorder("Episodi"));

            final List<Episode> viewedEpisodes = uc.getViewedEpisodes(serie.getSeriesId(), actualSeason.getId());
            for (final Episode episode : actualSeason.getEpisodes()) {
                episodesPanel.add(new JLabel("Episodio " + episode.id()));
                episodesPanel.add(new JLabel("Durata: " + episode.duration()));
                final JCheckBox checkBox = createEpisodeCheckBox(episode, viewedEpisodes);
                episodesPanel.add(checkBox);
            }

            castAndEpisodesPanel.add(new JScrollPane(episodesPanel), BorderLayout.SOUTH);

            seasonPanel.add(castAndEpisodesPanel, BorderLayout.CENTER);

            seasonsPanel.add(seasonPanel);
        }

        mainPanel.add(seasonsPanel);

        // Review panel
        final JPanel reviewsPanel = super.getReviewsPanel(this.uc.getSeriesReviews(serie.getSeriesId()));
        reviewsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(reviewsPanel);

        // Review button
        reviewButton = new JButton("Recensisci");
        reviewButton.setEnabled(false);
        reviewButton.addActionListener(e -> {
            final WriteReviewView writeSerieReviewView = new WriteSerieReviewView(currentSessionContext, serie);
            writeSerieReviewView.setVisible(true);
        });
        reviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(reviewButton);

        add(new JScrollPane(mainPanel));
        setVisible(true);

    }

    private JCheckBox createEpisodeCheckBox(final Episode ep, final List<Episode> viewed) {
        final JCheckBox checkBox = new JCheckBox("Visto");
        checkBox.setSelected(viewed.contains(ep));
        checkBox.setSelected(uc.isEpisodeViewed(ep.seriesId(), ep.seasonId(), ep.id()));
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                if (!uc.visualizeEpisode(ep.seriesId(), ep.seasonId(), ep.id())) {
                    checkBox.setSelected(false);
                }
            } else {
                if (!uc.forgetEpisode(ep.seriesId(), ep.seasonId(), ep.id())) {
                    checkBox.setSelected(true);
                }
            }
            updateReviewButtonState(viewed.size(), uc.getSerie(ep.seriesId()).getNumberOfEpisodes());
        });
        return checkBox;
    }

    private void updateReviewButtonState(final int viewedEpisodes, final int totalEpisodes) {
        reviewButton.setEnabled(viewedEpisodes == totalEpisodes);
    }

}
