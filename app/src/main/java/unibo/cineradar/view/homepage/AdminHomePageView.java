package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.admin.AdminCardView;
import unibo.cineradar.view.homepage.admin.AdminCastView;
import unibo.cineradar.view.homepage.admin.AdminProfileView;
import unibo.cineradar.view.homepage.admin.AdminPromoView;
import unibo.cineradar.view.homepage.admin.AdminRankingsView;
import unibo.cineradar.view.homepage.admin.AdminRegistratorView;
import unibo.cineradar.view.homepage.admin.AdminRequestsView;
import unibo.cineradar.view.homepage.admin.AdminSerieView;
import unibo.cineradar.view.homepage.admin.AdminFilmView;
import unibo.cineradar.view.homepage.admin.AdminUsersView;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

/**
 * HomePage view of the Admin.
 */
public final class AdminHomePageView extends CineRadarViewFrameImpl {

    private static final String FILM_NAME = "film";
    private static final String SERIES_NAME = "serie";
    private static final String REQUEST_NAME = "request";
    private static final String PROFILE_NAME = "profile";
    private static final String RANKING_NAME = "ranking";
    private static final String CAST_NAME = "cast";
    private static final String PROMO_NAME = "promo";
    private static final String CARD_NAME = "card";
    private static final String REGISTRATOR_NAME = "registrator";
    private static final String USERS_NAME = "user";

    private final ViewContext context;
    private final CardLayout cards = new CardLayout();
    private final JPanel cardPane = new JPanel(this.cards);

    /**
     * Constructor of the admin homepage view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public AdminHomePageView(final ViewContext currentSessionContext) {
        super();
        this.context = currentSessionContext;
        this.getMainFrame().setTitle("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0));
        this.setInternalComponents();
    }

    private void setInternalComponents() {
        final JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(getNavBar(), BorderLayout.NORTH);
        this.getMainFrame().setContentPane(contentPane);
        contentPane.add(this.cardPane, BorderLayout.CENTER);
        setCards();
    }

    private void setCards() {
        this.cardPane.add(FILM_NAME, new AdminFilmView(this.context));
        this.cardPane.add(SERIES_NAME, new AdminSerieView(this.context));
        this.cardPane.add(REQUEST_NAME, new AdminRequestsView(this.context));
        this.cardPane.add(PROFILE_NAME, new AdminProfileView(this.context));
        this.cardPane.add(RANKING_NAME, new AdminRankingsView(this.context));
        this.cardPane.add(CAST_NAME, new AdminCastView(this.context));
        this.cardPane.add(PROMO_NAME, new AdminPromoView(this.context));
        this.cardPane.add(CARD_NAME, new AdminCardView(this.context));
        this.cardPane.add(REGISTRATOR_NAME, new AdminRegistratorView(this.context));
        this.cardPane.add(USERS_NAME, new AdminUsersView(this.context));
    }

    private JPanel getNavBar() {
        final FlowLayout navLayout = new FlowLayout();
        navLayout.setAlignment(FlowLayout.LEFT);
        final JPanel navBar = new JPanel(navLayout);
        final JButton filmButton = new JButton("FILM");
        filmButton.addActionListener(e -> this.cards.show(this.cardPane, FILM_NAME));
        final JButton seriesButton = new JButton("SERIE");
        seriesButton.addActionListener(e -> this.cards.show(this.cardPane, SERIES_NAME));
        final JButton castButton = new JButton("CAST");
        castButton.addActionListener(e -> this.cards.show(this.cardPane, CAST_NAME));
        final JButton rankingButton = new JButton("CLASSIFICHE");
        rankingButton.addActionListener(e -> this.cards.show(this.cardPane, RANKING_NAME));
        final JButton requestButton = new JButton("RICHIESTE");
        requestButton.addActionListener(e -> this.cards.show(this.cardPane, REQUEST_NAME));
        final JButton profileButton = new JButton("PROFILO");
        profileButton.addActionListener(e -> this.cards.show(this.cardPane, PROFILE_NAME));
        final JButton promoButton = new JButton("PROMO");
        promoButton.addActionListener(e -> this.cards.show(this.cardPane, PROMO_NAME));
        final JButton cardsButton = new JButton("TESSERE");
        cardsButton.addActionListener(e -> this.cards.show(this.cardPane, CARD_NAME));
        final JButton registratorButton = new JButton("REGISTRATORI");
        registratorButton.addActionListener(e -> this.cards.show(this.cardPane, REGISTRATOR_NAME));
        final JButton usersButton = new JButton("UTENTI");
        usersButton.addActionListener(e -> this.cards.show(this.cardPane, USERS_NAME));
        navBar.add(filmButton);
        navBar.add(seriesButton);
        navBar.add(castButton);
        navBar.add(rankingButton);
        navBar.add(cardsButton);
        navBar.add(promoButton);
        navBar.add(requestButton);
        navBar.add(registratorButton);
        navBar.add(usersButton);
        navBar.add(profileButton);
        return navBar;
    }
}
