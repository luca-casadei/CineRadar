package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.UserFilmView;
import unibo.cineradar.view.homepage.user.UserProfileView;
import unibo.cineradar.view.homepage.user.UserReviewView;
import unibo.cineradar.view.homepage.user.UserSerieView;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * HomePage view of the user.
 */
public final class UserHomePageView extends CineRadarViewFrameImpl {

    private static final String FILM_NAME = "film";
    private static final String SERIE_NAME = "serie";
    private static final String REVIEW_NAME = "review";
    private static final String PROFILE_NAME = "profile";

    private final List<JPanel> jPanelList;
    private final ViewContext context;
    private CardLayout cards;
    private JPanel cardPane;

    /**
     * Constructor of the user homepage view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public UserHomePageView(final ViewContext currentSessionContext) {
        super();

        this.context = currentSessionContext;
        this.getMainFrame().setTitle("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0));

        this.jPanelList = List.of(
                new UserFilmView(currentSessionContext),
                new UserProfileView(currentSessionContext),
                new UserReviewView(currentSessionContext),
                new UserSerieView(currentSessionContext)
        );

        this.setInternalComponents();
    }

    private void setInternalComponents(){
        final JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(getNavBar(), BorderLayout.NORTH);
        this.getMainFrame().setContentPane(contentPane);

        this.cards = new CardLayout();
        this.cardPane = new JPanel(cards);
        contentPane.add(cardPane, BorderLayout.CENTER);

        //Home panel
        cardPane.add(FILM_NAME, new UserFilmView(this.context));
        cardPane.add(SERIE_NAME, new UserSerieView(this.context));
        cardPane.add(REVIEW_NAME, new UserReviewView(this.context));
        cardPane.add(PROFILE_NAME, new UserProfileView(this.context));
    }

    private JPanel getNavBar(){
        final FlowLayout navLayout = new FlowLayout();
        navLayout.setAlignment(FlowLayout.LEFT);
        final JPanel navBar = new JPanel(navLayout);
        final JButton filmButton = getGotoFilmButton();
        final JButton serieButton = getGotoSerieButton();
        final JButton reviewButton = getGotoReviewButton();
        final JButton profileButton = getGotoProfileButton();
        navBar.add(filmButton);
        navBar.add(serieButton);
        navBar.add(reviewButton);
        navBar.add(profileButton);
        return navBar;
    }

    private JButton getGotoFilmButton(){
        final JButton filmButton = new JButton("FILM");
        filmButton.addActionListener(e -> cards.show(cardPane, FILM_NAME));
        return filmButton;
    }

    private JButton getGotoSerieButton(){
        final JButton serieButton = new JButton("SERIE");
        serieButton.addActionListener(e -> cards.show(cardPane, SERIE_NAME));
        return serieButton;
    }

    private JButton getGotoReviewButton(){
        final JButton reviewButton = new JButton("LE MIE RECENSIONI");
        reviewButton.addActionListener(e -> cards.show(cardPane, REVIEW_NAME));
        return reviewButton;
    }

    private JButton getGotoProfileButton(){
        final JButton profileButton = new JButton("PROFILO");
        profileButton.addActionListener(e -> cards.show(cardPane, PROFILE_NAME));
        return profileButton;
    }

    /*private void setInternalComponents() {
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
        filmBtn.addActionListener(e -> displayHide(UserFilmView.class.getCanonicalName()));
        final JButton serieBtn = new JButton("SERIE TV");
        topNavPanel.add(serieBtn);
        serieBtn.addActionListener(e -> displayHide(UserSerieView.class.getCanonicalName()));
        final JButton reviewBtn = new JButton("LE MIE RECENSIONI");
        topNavPanel.add(reviewBtn);
        reviewBtn.addActionListener(e -> displayHide(UserReviewView.class.getCanonicalName()));
        final JButton profileBtn = new JButton("PROFILO");
        topNavPanel.add(profileBtn);
        profileBtn.addActionListener(e -> displayHide(UserProfileView.class.getCanonicalName()));

        this.contentPane.add(topNavPanel, BorderLayout.NORTH);

        this.contentPane.add(this.jPanelList.get(0), BorderLayout.CENTER);
    }

    private void displayHide(final String toShow) {
        for (final JPanel jp : jPanelList) {
            final boolean visibilize = jp.getClass().getCanonicalName().equals(toShow);
            if (!visibilize) {
                this.contentPane.remove(jp);
            } else {
                this.contentPane.add(jp, BorderLayout.CENTER);
            }
        }
        this.getMainFrame().revalidate();
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }*/
}
