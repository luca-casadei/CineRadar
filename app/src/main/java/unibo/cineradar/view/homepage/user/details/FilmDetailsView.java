package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.review.WriteFilmReviewView;
import unibo.cineradar.view.homepage.user.review.WriteReviewView;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setTitle(detailedFilm.getTitle()
                + " - "
                + this.uc.getAccount().getName());
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

        final JPanel bottomPanel = getBottomPanel(currentSessionContext);
        final JPanel reviewsPanel = getReviewsPanel();

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(reviewsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel getBottomPanel(final ViewContext currentSessionContext) {
        final JCheckBox cb = getViewedSelector();
        final JButton reviewButton = new JButton("Recensisci");
        cb.addActionListener(e -> reviewButton.setEnabled(cb.isSelected()));
        reviewButton.setEnabled(cb.isSelected());
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

    private JPanel getReviewsPanel() {
        final JPanel reviewsPanel = new JPanel(new BorderLayout());
        reviewsPanel.setBorder(BorderFactory.createTitledBorder("Recensioni"));

        final List<Review> filmReviews = this.uc.getFilmReviews(detailedFilm.getFilmId());

        final String[] columnNames = {"Username", "Titolo Recensione", "Voto complessivo", ""};
        final Object[][] data = new Object[filmReviews.size()][4];

        for (int i = 0; i < filmReviews.size(); i++) {
            final Review review = filmReviews.get(i);
            data[i][0] = review.getUsername();
            data[i][1] = review.getTitle();
            data[i][2] = review.getOverallRating();
            data[i][3] = review;
        }

        final DefaultTableModel model = new DefaultTableModel(data, columnNames);
        final JTable table = new JTable(model);
        table.getColumnModel().getColumn(1).setPreferredWidth(FRAME_WIDTH / 3);
        table.setDefaultEditor(Object.class, null);

        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(3).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            final JButton button = new JButton("Vedi dettagli");
            button.addActionListener(e -> {
                final Review review = (Review) value;
                // TODO: Visualizza i dettagli della recensione
                JOptionPane.showMessageDialog(null,
                        review.getDescription(),
                        "Dettagli recensione",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            return button;
        });

        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(
                    final JTable table, final Object value, final boolean isSelected, final int row, final int column
            ) {
                return table.getCellRenderer(row, column).getTableCellRendererComponent(
                        table, value, isSelected, true, row, column
                );
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }
        });

        final JScrollPane scrollPane = new JScrollPane(table);
        reviewsPanel.add(scrollPane, BorderLayout.CENTER);

        return reviewsPanel;
    }
}

