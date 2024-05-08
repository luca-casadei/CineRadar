package unibo.cineradar.view.homepage.admin.details;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * A view to display detailed information about a film.
 */
public final class AdminFilmDetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = -5729493403413904557L;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final int VERTICAL_MARGIN = 5;

    private final transient ViewContext currentSessionContext;
    private final transient Film detailedFilm;
    private final transient Cast detailedFilmCast;

    /**
     * Constructs a new FilmDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param filmId                The id of the detailed film.
     */
    public AdminFilmDetailsView(final ViewContext currentSessionContext, final int filmId) {
        this.currentSessionContext = currentSessionContext;
        final Map<Film, Cast> detailedFilms =
                ((AdminSessionController) currentSessionContext.getController()).getDetailedFilms();
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
        setTitle(getWindowTitle());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createFilmDetailsPanel(), BorderLayout.NORTH);
        mainPanel.add(createCastPanel(), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private String getWindowTitle() {
        return detailedFilm.getTitle() + " - " + currentSessionContext.getController().getAccount().getName();
    }

    private JPanel createFilmDetailsPanel() {
        final JPanel filmDetailsPanel = new JPanel(new GridLayout(0, 2));
        filmDetailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli film"));

        addDetail(filmDetailsPanel, "Titolo:", detailedFilm.getTitle());
        addDetail(filmDetailsPanel, "Limite di eta':", String.valueOf(detailedFilm.getAgeLimit()));
        addDetail(filmDetailsPanel, "Trama:", detailedFilm.getPlot());
        addDetail(filmDetailsPanel, "Durata (min):", String.valueOf(detailedFilm.getDuration()));

        return filmDetailsPanel;
    }

    private JPanel createCastPanel() {
        final JPanel castPanel = new JPanel();
        castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));
        castPanel.setBorder(BorderFactory.createTitledBorder("Cast"));

        if (detailedFilmCast != null) {
            final List<CastMember> castMembers = detailedFilmCast.getCastMemberList();
            for (final CastMember castMember : castMembers) {
                addCastMember(castPanel, castMember);
            }
        }

        return castPanel;
    }

    private void addDetail(final JPanel panel, final String label, final String value) {
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
    }

    private void addCastMember(final JPanel panel, final CastMember castMember) {
        final JLabel castMemberLabel = new JLabel(castMember.getName()
                + " "
                + castMember.getLastName()
                + " - "
                + castMember.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel.add(castMemberLabel);
        panel.add(Box.createVerticalStrut(VERTICAL_MARGIN));
    }
}
