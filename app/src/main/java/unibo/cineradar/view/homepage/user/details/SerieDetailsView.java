package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * A view to display detailed information about a serie.
 */
public final class SerieDetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1366976476336457844L;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final int VERTICAL_MARGIN = 5;


    private final transient ViewContext currentSessionContext;

    /**
     * Constructs a new SerieDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param serieId               The ID of the series to get the details of.
     */
    public SerieDetailsView(final ViewContext currentSessionContext, final int serieId) {
        this.currentSessionContext = currentSessionContext;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final Map<Serie, Map<Season, Cast>> detailedSeries =
                ((UserSessionController) currentSessionContext.getController()).getDetailedSeries();

        for (final Map.Entry<Serie, Map<Season, Cast>> serieEntry : detailedSeries.entrySet()) {
            if (serieEntry.getKey().getSeriesId() == serieId) {
                setTitle(serieEntry.getKey().getTitle()
                        + " - "
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

            final JPanel episodesPanel = new JPanel(new GridLayout(0, 2));
            episodesPanel.setBorder(BorderFactory.createTitledBorder("Episodi"));

            for (final unibo.cineradar.model.serie.Episode episode : seasonEntry.getKey().getEpisodes()) {
                episodesPanel.add(new JLabel("Episodio " + episode.getId()));
                episodesPanel.add(new JLabel("Durata: " + episode.getDuration()));
            }

            seasonPanel.add(episodesPanel, BorderLayout.SOUTH);

            seasonsPanel.add(seasonPanel);
        }

        mainPanel.add(new JScrollPane(seasonsPanel), BorderLayout.CENTER);

        // Review button
        final JButton reviewButton = new JButton("Recensisci la serie");
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO: metodo per recensire una serie in particolare.
            }
        });
        mainPanel.add(reviewButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Retrieves the current session context.
     *
     * @return The current session context.
     */
    public ViewContext getCurrentSessionContext() {
        return currentSessionContext;
    }
}
