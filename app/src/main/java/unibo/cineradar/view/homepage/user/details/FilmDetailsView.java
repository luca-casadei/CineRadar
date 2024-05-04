package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * A view to display detailed information about a film.
 */
public final class FilmDetailsView extends JFrame {
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
                final JLabel castMemberLabel = new JLabel(castMember.getName()
                        + " "
                        + castMember.getLastName()
                        + " - "
                        + castMember.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                castPanel.add(castMemberLabel);
                castPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN));
            }
        }

        mainPanel.add(castPanel, BorderLayout.CENTER);

        final JCheckBox cb = getViewedSelector();
        mainPanel.add(cb, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JCheckBox getViewedSelector() {
        final UserSessionController ctr = (UserSessionController) currentSessionContext.getController();
        final JCheckBox cb = new JCheckBox("Gia' visto!");
        cb.setHorizontalAlignment(SwingConstants.CENTER);
        cb.setSelected(ctr.isFilmViewed(detailedFilm.getFilmId()));
        cb.addActionListener(e -> {
            final JCheckBox cbe = (JCheckBox) e.getSource();
            if (cb.isSelected()) {
                if (!ctr.visualizeFilm(detailedFilm.getFilmId())) {
                    cbe.setSelected(false);
                }
            } else {
                if (!ctr.forgetFilm(detailedFilm.getFilmId())) {
                    cbe.setSelected(true);
                }
            }
        });
        return cb;
    }
}
