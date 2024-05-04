package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.view.ViewContext;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * A view to display detailed information about a film.
 */
public final class FilmDetailsView extends JFrame {
    private static final long serialVersionUID = -5729493403413904557L;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    private final transient ViewContext currentSessionContext;
    private final transient Film detailedFilm;
    private final transient Cast detailedFilmCast;

    /**
     * Constructs a new FilmDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param filmId                The id of the detailed film.
     */
    public FilmDetailsView(final ViewContext currentSessionContext, final int filmId) {
        this.currentSessionContext = currentSessionContext;
        final Map<Film, Cast> detailedFilms =
                ((UserSessionController) currentSessionContext.getController()).getDetailedFilms();
        for (final Map.Entry<Film, Cast> entry : detailedFilms.entrySet()) {
            if (entry.getKey().getFilmId() == filmId) {
                this.detailedFilm = entry.getKey();
                this.detailedFilmCast = entry.getValue();
                initComponents();
                return;
            }
        }

        throw new IllegalStateException("Detailed film or detailed film cast not initialized");
    }

    private void initComponents() {
        setTitle(detailedFilm.getTitle() + currentSessionContext.getController().getAccount().getName());
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

        mainPanel.add(filmDetailsPanel, BorderLayout.NORTH);

        // Cast details panel
        final JPanel castPanel = new JPanel();
        castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));
        castPanel.setBorder(BorderFactory.createTitledBorder("Cast"));

        if (detailedFilmCast != null) {
            final List<CastMember> castMembers = detailedFilmCast.getCastMemberList();
            for (final CastMember castMember : castMembers) {
                final JLabel castMemberLabel = new JLabel(castMember.getName() + " " +
                        castMember.getLastName() + " - " +
                        castMember.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                castPanel.add(castMemberLabel);
                castPanel.add(Box.createVerticalStrut(5));
            }
        }

        mainPanel.add(new JScrollPane(castPanel), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
