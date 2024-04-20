package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * View of the user.
 */
public final class UserHomePageView extends CineRadarViewFrameImpl {

    /**
     * Constructor of the user view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserHomePageView(final ViewContext currentSessionContext) {
        super();

        this.getMainFrame().setTitle("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0));
        this.setInternalComponents();
    }

    private void setInternalComponents() {
        final JPanel contentPane = new JPanel(new BorderLayout());
        this.getMainFrame().setContentPane(contentPane);
        // Navigation bar on top
        final FlowLayout navLayout = new FlowLayout();
        navLayout.setAlignment(FlowLayout.LEFT);
        final JPanel topNavPanel = new JPanel(navLayout);
        topNavPanel.setBackground(Color.GRAY);
        final Image logoImage = new ImageIcon(ViewUtilities.getResourceURL(
                ViewUtilities.DEFAULT_IMAGE_PATH + "/logo.png"))
                .getImage().getScaledInstance(300, 50, Image.SCALE_SMOOTH);
        final JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(logoImage));
        topNavPanel.add(imageLabel);
        final JButton filmBtn = new JButton("FILM");
        topNavPanel.add(filmBtn);
        final JButton serieBtn = new JButton("SERIE TV");
        topNavPanel.add(serieBtn);
        final JButton reviewBtn = new JButton("LE MIE RECENSIONI");
        topNavPanel.add(reviewBtn);
        final JButton profileBtn = new JButton("PROFILO");
        topNavPanel.add(profileBtn);

        contentPane.add(topNavPanel, BorderLayout.NORTH);
    }
}
