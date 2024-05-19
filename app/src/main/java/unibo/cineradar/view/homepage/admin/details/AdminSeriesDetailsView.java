package unibo.cineradar.view.homepage.admin.details;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;

// CHECKSTYLE: MagicNumber OFF

/**
 * A view to display detailed information about a serie.
 */
public final class AdminSeriesDetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = 6530405035909369718L;

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final int VERTICAL_MARGIN = 5;

    /**
     * Constructs a new SeriesDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param serieId               The ID of the series to get the details of.
     */
    public AdminSeriesDetailsView(final ViewContext currentSessionContext, final int serieId) {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final AdminSessionController adminSessionController =
                (AdminSessionController) currentSessionContext.getController();

        final List<Serie> detailedSeries = adminSessionController.getDetailedSeries();

        for (final Serie actualSerie : detailedSeries) {
            if (actualSerie.getSeriesId() == serieId) {
                setTitle(actualSerie.getTitle() + " - "
                        + currentSessionContext.getController().getAccount().getName());
                initComponents(actualSerie);
                return;
            }
        }

        throw new IllegalStateException("Detailed series not initialized for ID: " + serieId);
    }

    private void initComponents(final Serie serie) {
        final JPanel mainPanel = createMainPanel(serie);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createMainPanel(final Serie serie) {
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createSerieDetailsPanel(serie), BorderLayout.NORTH);
        mainPanel.add(createSeasonsPanel(serie), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createSerieDetailsPanel(final Serie serie) {
        final JPanel serieDetailsPanel = new JPanel(new GridLayout(0, 2));
        serieDetailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli serie"));

        addSerieDetail(serieDetailsPanel, "Titolo:", serie.getTitle());
        addSerieDetail(serieDetailsPanel, "Limite di eta':", String.valueOf(serie.getAgeLimit()));
        addSerieDetail(serieDetailsPanel, "Trama:", serie.getPlot());
        addSerieDetail(serieDetailsPanel, "Durata complessiva (min):", String.valueOf(serie.getDuration()));
        addSerieDetail(serieDetailsPanel, "Numero episodi:", String.valueOf(serie.getNumberOfEpisodes()));

        return serieDetailsPanel;
    }

    private void addSerieDetail(final JPanel panel, final String label, final String value) {
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
    }

    private JScrollPane createSeasonsPanel(final Serie serie) {
        final JPanel seasonsPanel = new JPanel();
        seasonsPanel.setLayout(new BoxLayout(seasonsPanel, BoxLayout.Y_AXIS));
        seasonsPanel.setBorder(BorderFactory.createTitledBorder("Stagioni"));

        for (final Season actualSeason : serie.getSeasons()) {
            final JPanel seasonPanel = createSeasonPanel(actualSeason);
            seasonsPanel.add(seasonPanel);
        }

        return new JScrollPane(seasonsPanel);
    }

    private JPanel createSeasonPanel(final Season actualSeason) {
        final JPanel seasonPanel = new JPanel(new BorderLayout());
        seasonPanel.setBorder(BorderFactory.createTitledBorder("Stagione " + actualSeason.getId()));

        final JLabel summaryLabel = new JLabel(actualSeason.getSummary());
        summaryLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        seasonPanel.add(summaryLabel, BorderLayout.NORTH);

        final JPanel castAndEpisodesPanel = new JPanel(new BorderLayout());
        castAndEpisodesPanel.add(createCastPanel(actualSeason), BorderLayout.CENTER);
        castAndEpisodesPanel.add(createEpisodesPanel(actualSeason), BorderLayout.SOUTH);

        seasonPanel.add(castAndEpisodesPanel, BorderLayout.CENTER);

        return seasonPanel;
    }

    private JScrollPane createCastPanel(final Season actualSeason) {
        final JPanel castPanel = new JPanel();
        castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));

        for (final CastMember castMember : actualSeason.getCast().getCastMemberList()) {
            final JLabel castMemberLabel = createCastMemberLabel(castMember);
            castPanel.add(castMemberLabel);
            castPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        }

        return new JScrollPane(castPanel);
    }

    private JLabel createCastMemberLabel(final CastMember castMember) {
        return new JLabel(
                castMember.getName()
                        + " "
                        + castMember.getLastName()
                        + " - "
                        + castMember.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private JScrollPane createEpisodesPanel(final Season actualSeason) {
        final JPanel episodesPanel = new JPanel(new GridLayout(0, 2));
        episodesPanel.setBorder(BorderFactory.createTitledBorder("Episodi"));

        for (final Episode episode : actualSeason.getEpisodes()) {
            episodesPanel.add(new JLabel("Episodio " + episode.id()));
            episodesPanel.add(new JLabel("Durata: " + episode.duration()));
        }

        return new JScrollPane(episodesPanel);
    }

}

// CHECKSTYLE: MagicNumber ON
