package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.registrar.CardedCinemaPanel;
import unibo.cineradar.view.homepage.registrar.RegInfoPanel;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

/**
 * View of the registrar.
 */
public final class RegistrarHomePageView extends CineRadarViewFrameImpl {

    private static final String REG_NAME = "inf";
    private static final String CINE_NAME = "cin";
    private final ViewContext context;
    private CardLayout cards;
    private JPanel cardPane;

    /**
     * Constructor of the registrar view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public RegistrarHomePageView(final ViewContext currentSessionContext) {
        super();
        this.context = currentSessionContext;
        this.getMainFrame().setTitle("Registrazione di utenti tesserati - Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0));
        this.setInternalComponents();
    }

    private void setInternalComponents() {
        final JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(getNavBar(), BorderLayout.NORTH);
        this.getMainFrame().setContentPane(contentPane);

        this.cards = new CardLayout();
        this.cardPane = new JPanel(cards);
        contentPane.add(cardPane, BorderLayout.CENTER);

        //Home panel
        cardPane.add(REG_NAME, new RegInfoPanel(context));
        cardPane.add(CINE_NAME, new CardedCinemaPanel(context));

        cards.first(cardPane);
    }

    private JPanel getNavBar() {
        final FlowLayout navLayout = new FlowLayout();
        navLayout.setAlignment(FlowLayout.LEFT);
        final JPanel navBar = new JPanel(navLayout);
        final JButton registerButton = getGotoRegButton();
        final JButton cinemaButton = getGotoCineButton();
        navBar.add(registerButton);
        navBar.add(cinemaButton);
        return navBar;
    }

    private JButton getGotoRegButton() {
        final JButton regButton = new JButton("INFORMAZIONI UTENTE");
        regButton.addActionListener(e -> cards.show(cardPane, REG_NAME));
        return regButton;
    }

    private JButton getGotoCineButton() {
        final JButton cineButton = new JButton("INFO CINEMA E REGISTRAZIONE TESSERATI");
        cineButton.addActionListener(e -> cards.show(cardPane, CINE_NAME));
        return cineButton;
    }
}
