package unibo.cineradar.view.homepage.admin.details;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.view.ViewContext;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A view to display detailed information about a cast.
 */
public class AdminCastDetailsView extends JFrame {
    @Serial
    private static final long serialVersionUID = -5705499583413927457L;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final int VERTICAL_MARGIN = 5;
    private final transient ViewContext currentSessionContext;
    private final int castId;

    /**
     * Constructs a new FilmDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param castId                The id of the detailed cast.
     */
    public AdminCastDetailsView(final ViewContext currentSessionContext, final int castId) {
        this.currentSessionContext = currentSessionContext;
        this.castId = castId;
        initComponents();
    }

    private void initComponents() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createCastPanel(), BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createCastPanel() {
        final JPanel castPanel = new JPanel();
        castPanel.setLayout(new BoxLayout(castPanel, BoxLayout.Y_AXIS));
        castPanel.setBorder(BorderFactory.createTitledBorder("Cast"));
        final List<CastMember> castMembers =
                ((AdminSessionController) currentSessionContext.getController())
                        .getDetailedCast(this.castId);
        for (final CastMember castMember : castMembers) {
            addCastMember(castPanel, castMember);
        }
        return castPanel;
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
