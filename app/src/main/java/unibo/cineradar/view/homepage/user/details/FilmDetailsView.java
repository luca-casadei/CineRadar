package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A view to display detailed information about a film.
 */
public final class FilmDetailsView extends JFrame {
    private static final long serialVersionUID = 1L;
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
            if (entry.getKey().getId() == filmId) {
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
        filmDetailsPanel.setBorder(BorderFactory.createTitledBorder("Film Details"));
        filmDetailsPanel.add(new JLabel("Title:"));
        filmDetailsPanel.add(new JLabel(detailedFilm.getTitle()));
        filmDetailsPanel.add(new JLabel("Age Limit:"));
        filmDetailsPanel.add(new JLabel(String.valueOf(detailedFilm.getAgeLimit())));
        filmDetailsPanel.add(new JLabel("Plot:"));
        filmDetailsPanel.add(new JLabel(detailedFilm.getPlot()));
        filmDetailsPanel.add(new JLabel("Duration (minutes):"));
        filmDetailsPanel.add(new JLabel(String.valueOf(detailedFilm.getDuration())));

        mainPanel.add(filmDetailsPanel, BorderLayout.NORTH);

        // Cast details panel
        final JPanel castPanel = new JPanel(new BorderLayout());
        castPanel.setBorder(BorderFactory.createTitledBorder("Cast"));

        final DefaultListModel<String> castListModel = new DefaultListModel<>();
        final JList<String> castList = new JList<>(castListModel);
        final JScrollPane scrollPane = new JScrollPane(castList);
        castPanel.add(scrollPane, BorderLayout.CENTER);

        if (detailedFilmCast != null) {
            final List<CastMember> castMembers = detailedFilmCast.getCastMemberList();
            for (final CastMember castMember : castMembers) {
                final String castMemberInfo = castMember.getName()
                        + " "
                        + castMember.getLastName()
                        + " - "
                        + new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(castMember.getBirthDate());
                castListModel.addElement(castMemberInfo);
            }
        }

        mainPanel.add(castPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
