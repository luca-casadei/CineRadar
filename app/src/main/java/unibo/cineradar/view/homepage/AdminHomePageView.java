package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.admin.AdminCardView;
import unibo.cineradar.view.homepage.admin.AdminCastView;
import unibo.cineradar.view.homepage.admin.AdminPanel;
import unibo.cineradar.view.homepage.admin.AdminProfileView;
import unibo.cineradar.view.homepage.admin.AdminPromoView;
import unibo.cineradar.view.homepage.admin.AdminRankingsView;
import unibo.cineradar.view.homepage.admin.AdminRegistratorView;
import unibo.cineradar.view.homepage.admin.AdminRequestsView;
import unibo.cineradar.view.homepage.admin.AdminSerieView;
import unibo.cineradar.view.homepage.admin.AdminFilmView;
import unibo.cineradar.view.homepage.admin.AdminUsersView;
import unibo.cineradar.view.homepage.admin.AdminWelcomeView;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * HomePage view of the Admin.
 */
public final class AdminHomePageView extends CineRadarViewFrameImpl {

    private static final String WELCOME_NAME = "welcome";
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
    private final Map<String, JPanel> panels = new HashMap<>();

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
        panels.put(WELCOME_NAME, new AdminWelcomeView());
        panels.put(FILM_NAME, new AdminFilmView(this.context));
        panels.put(SERIES_NAME, new AdminSerieView(this.context));
        panels.put(REQUEST_NAME, new AdminRequestsView(this.context));
        panels.put(PROFILE_NAME, new AdminProfileView(this.context));
        panels.put(RANKING_NAME, new AdminRankingsView(this.context));
        panels.put(CAST_NAME, new AdminCastView(this.context));
        panels.put(PROMO_NAME, new AdminPromoView(this.context));
        panels.put(CARD_NAME, new AdminCardView(this.context));
        panels.put(REGISTRATOR_NAME, new AdminRegistratorView(this.context));
        panels.put(USERS_NAME, new AdminUsersView(this.context));

        for (final Map.Entry<String, JPanel> entry : panels.entrySet()) {
            this.cardPane.add(entry.getKey(), entry.getValue());
        }
        this.cards.show(this.cardPane, WELCOME_NAME);
    }

    private void updateAndShowCard(final String cardName) {
        final JPanel panel = panels.get(cardName);
        if (panel instanceof AdminPanel) {
            ((AdminPanel) panel).updatePanel();
        }
        this.cards.show(this.cardPane, cardName);
    }

    private JPanel getNavBar() {
        final FlowLayout navLayout = new FlowLayout();
        navLayout.setAlignment(FlowLayout.LEFT);
        final JPanel navBar = new JPanel(navLayout);
        final JButton filmButton = new JButton("FILM");
        filmButton.addActionListener(e -> updateAndShowCard(FILM_NAME));
        final JButton seriesButton = new JButton("SERIE");
        seriesButton.addActionListener(e -> updateAndShowCard(SERIES_NAME));
        final JButton castButton = new JButton("CAST");
        castButton.addActionListener(e -> updateAndShowCard(CAST_NAME));
        final JButton rankingButton = new JButton("CLASSIFICHE");
        rankingButton.addActionListener(e -> updateAndShowCard(RANKING_NAME));
        final JButton requestButton = new JButton("RICHIESTE");
        requestButton.addActionListener(e -> updateAndShowCard(REQUEST_NAME));
        final JButton profileButton = new JButton("PROFILO");
        profileButton.addActionListener(e -> updateAndShowCard(PROFILE_NAME));
        final JButton promoButton = new JButton("PROMO");
        promoButton.addActionListener(e -> updateAndShowCard(PROMO_NAME));
        final JButton cardsButton = new JButton("TESSERE");
        cardsButton.addActionListener(e -> updateAndShowCard(CARD_NAME));
        final JButton registrarButton = new JButton("REGISTRATORI");
        registrarButton.addActionListener(e -> updateAndShowCard(REGISTRATOR_NAME));
        final JButton usersButton = new JButton("UTENTI");
        usersButton.addActionListener(e -> updateAndShowCard(USERS_NAME));
        navBar.add(filmButton);
        navBar.add(seriesButton);
        navBar.add(castButton);
        navBar.add(rankingButton);
        navBar.add(cardsButton);
        navBar.add(promoButton);
        navBar.add(requestButton);
        navBar.add(registrarButton);
        navBar.add(usersButton);
        navBar.add(profileButton);
        return navBar;
    }
}
