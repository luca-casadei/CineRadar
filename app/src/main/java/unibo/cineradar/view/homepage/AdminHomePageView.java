package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.admin.AdminRankingsView;
import unibo.cineradar.view.homepage.admin.AdminRequestsView;
import unibo.cineradar.view.homepage.user.UserFilmView;
import unibo.cineradar.view.homepage.user.UserProfileView;
import unibo.cineradar.view.homepage.user.UserSerieView;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * HomePage view of the Admin.
 */
public final class AdminHomePageView extends CineRadarViewFrameImpl {

    private final List<JPanel> jPanelList;
    private final JPanel contentPane = new JPanel(new BorderLayout());

    /**
     * Constructor of the admin homepage view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public AdminHomePageView(final ViewContext currentSessionContext) {
        super();

        this.getMainFrame().setTitle("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0));

        this.jPanelList = List.of(
                new UserFilmView(currentSessionContext),
                new UserProfileView(currentSessionContext),
                new AdminRequestsView(currentSessionContext),
                new AdminRankingsView(currentSessionContext),
                new UserSerieView(currentSessionContext)
        );

        this.setInternalComponents();
    }

    private void setInternalComponents() {
        this.getMainFrame().setContentPane(contentPane);
        // Navigation bar on top
        final FlowLayout navLayout = new FlowLayout();
        navLayout.setAlignment(FlowLayout.LEFT);
        final JPanel topNavigationPanel = new JPanel(navLayout);
        topNavigationPanel.setBackground(Color.GRAY);
        final Image logoImage = new ImageIcon(ViewUtilities.getResourceURL(
                ViewUtilities.DEFAULT_IMAGE_PATH + "/logo.png"))
                .getImage().getScaledInstance(300, 50, Image.SCALE_SMOOTH);
        final JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(logoImage));
        topNavigationPanel.add(imageLabel);
        final JButton filmButton = new JButton("FILM");
        topNavigationPanel.add(filmButton);
        filmButton.addActionListener(e -> displayHide(UserFilmView.class.getCanonicalName()));
        final JButton seriesButton = new JButton("SERIETV");
        topNavigationPanel.add(seriesButton);
        seriesButton.addActionListener(e -> displayHide(UserSerieView.class.getCanonicalName()));
        final JButton requestsButton = new JButton("RICHIESTE");
        topNavigationPanel.add(requestsButton);
        requestsButton.addActionListener(e -> displayHide(AdminRequestsView.class.getCanonicalName()));
        final JButton rankingsButton = new JButton("CLASSIFICHE");
        topNavigationPanel.add(rankingsButton);
        rankingsButton.addActionListener(e -> displayHide(AdminRankingsView.class.getCanonicalName()));
        final JButton profileButton = new JButton("PROFILO");
        topNavigationPanel.add(profileButton);
        profileButton.addActionListener(e -> displayHide(UserProfileView.class.getCanonicalName()));

        this.contentPane.add(topNavigationPanel, BorderLayout.NORTH);

        this.contentPane.add(this.jPanelList.get(0), BorderLayout.CENTER);
    }

    private void displayHide(final String toShow) {
        for (final JPanel jp : jPanelList) {
            if (!jp.getClass().getCanonicalName().equals(toShow)) {
                this.contentPane.remove(jp);
            } else {
                this.contentPane.add(jp, BorderLayout.CENTER);
            }
        }
        this.getMainFrame().revalidate();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }
}