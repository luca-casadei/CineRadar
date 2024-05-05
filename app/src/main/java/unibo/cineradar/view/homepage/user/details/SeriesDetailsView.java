package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

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
import java.awt.GridLayout;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * A view to display detailed information about a serie.
 */
public final class SeriesDetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1366976476336457844L;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final int VERTICAL_MARGIN = 5;
    private final transient UserSessionController uc;
    private JButton reviewButton;
    private int totalEpisodes;
    private int viewedEpisodes;

    /**
     * Constructs a new SeriesDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param serieId               The ID of the series to get the details of.
     */
    public SeriesDetailsView(final ViewContext currentSessionContext, final int serieId) {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.uc = (UserSessionController) currentSessionContext.getController();

        final Map<Serie, Map<Season, Cast>> detailedSeries =
                uc.getDetailedSeries();

        for (final Map.Entry<Serie, Map<Season, Cast>> serieEntry : detailedSeries.entrySet()) {
            if (serieEntry.getKey().getSeriesId() == serieId) {
                setTitle(serieEntry.getKey().getTitle() + " - "
                        + currentSessionContext.getController().getAccount().getName());
                initComponents(serieEntry.getKey(), serieEntry.getValue());
                return;
            }
        }

        throw new IllegalStateException("Detailed series not initialized for ID: " + serieId);
    }

    private void initComponents(final Serie serie, final Map<Season, Cast> seasonsMap) {
        final JPanel mainPanel = new JPanel(new BorderLayout());

        // Serie details panel
        final JPanel serieDetailsPanel = new JPanel(new GridLayout(0, 2));
        serieDetailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli serie"));
        serieDetailsPanel.add(new JLabel("Titolo:"));
        serieDetailsPanel.add(new JLabel(serie.getTitle()));
        serieDetailsPanel.add(new JLabel("Limite di età:"));
        serieDetailsPanel.add(new JLabel(String.valueOf(serie.getAgeLimit())));
        serieDetailsPanel.add(new JLabel("Trama:"));
        serieDetailsPanel.add(new JLabel(serie.getPlot()));
        serieDetailsPanel.add(new JLabel("Durata complessiva (min):"));
        serieDetailsPanel.add(new JLabel(String.valueOf(serie.getDuration())));
        serieDetailsPanel.add(new JLabel("Numero episodi:"));
        serieDetailsPanel.add(new JLabel(String.valueOf(serie.getNumberOfEpisodes())));

        mainPanel.add(serieDetailsPanel, BorderLayout.NORTH);

        // Seasons details panel
        final JPanel seasonsPanel = new JPanel();
        seasonsPanel.setLayout(new BoxLayout(seasonsPanel, BoxLayout.Y_AXIS));
        seasonsPanel.setBorder(BorderFactory.createTitledBorder("Stagioni"));

        for (final Map.Entry<Season, Cast> seasonEntry : seasonsMap.entrySet()) {
            final JPanel seasonPanel = new JPanel(new BorderLayout());
            seasonPanel.setBorder(BorderFactory.createTitledBorder("Stagione " + seasonEntry.getKey().getId()));

            final JPanel castPanel = new JPanel();
            castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));

            if (seasonEntry.getValue() != null) {
                final List<CastMember> castMembers = seasonEntry.getValue().getCastMemberList();
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

            seasonPanel.add(new JScrollPane(castPanel), BorderLayout.CENTER);

            final JPanel episodesPanel = new JPanel(new GridLayout(0, 3));
            episodesPanel.setBorder(BorderFactory.createTitledBorder("Episodi"));

            final List<Episode> viewed = uc.getViewedEpisodes(serie.getSeriesId(), seasonEntry.getKey().getId());
            for (final Episode episode : seasonEntry.getKey().getEpisodes()) {
                episodesPanel.add(new JLabel("Episodio " + episode.id()));
                episodesPanel.add(new JLabel("Durata: " + episode.duration()));
                final JCheckBox checkBox = createEpisodeCheckBox(episode, viewed);
                episodesPanel.add(checkBox);
            }

            seasonPanel.add(new JScrollPane(episodesPanel), BorderLayout.SOUTH);

            seasonsPanel.add(seasonPanel);
        }

        mainPanel.add(new JScrollPane(seasonsPanel), BorderLayout.CENTER);

        // Review button
        reviewButton = new JButton("Recensisci la serie");
        reviewButton.setEnabled(false); // Inizialmente disabilitato
        reviewButton.addActionListener(e -> {
            // TODO: Manca la divisione in sezioni nella GUI?
        });

        mainPanel.add(reviewButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Conta il numero totale di episodi
        totalEpisodes = 0;
        for (final Map.Entry<Season, Cast> seasonEntry : seasonsMap.entrySet()) {
            totalEpisodes += seasonEntry.getKey().getEpisodes().size();
        }

        // Conta gli episodi visti
        viewedEpisodes = 0;
    }

    //TODO: Brutto il conteggio lato client, va fatto tramite query!!
    private void updateReviewButtonState() {
        reviewButton.setEnabled(viewedEpisodes == totalEpisodes);
    }

    private JCheckBox createEpisodeCheckBox(final Episode ep, final List<Episode> viewed) {
        final JCheckBox checkBox = new JCheckBox("Visto");
        checkBox.setSelected(viewed.contains(ep));
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                viewedEpisodes++;
            } else {
                viewedEpisodes--;
            }
            updateReviewButtonState();
        });
        return checkBox;
    }
}

