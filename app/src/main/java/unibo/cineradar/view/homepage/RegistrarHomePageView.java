package unibo.cineradar.view.homepage;

import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

/**
 * View of the registrar.
 */
public final class RegistrarHomePageView extends CineRadarViewFrameImpl {

    /**
     * Constructor of the registrar view.
     *
     * @param currentSessionContext The context of the current user.
     */
    public RegistrarHomePageView(final ViewContext currentSessionContext) {
        super();

        this.getMainFrame().setTitle("Registrazione di utenti tesserati - Benvenuto "
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
        final JButton cinemaBtn = new JButton("INFORMAZIONI CINEMA");
        topNavPanel.add(cinemaBtn);
        final JButton infoBtn = new JButton("INFORMAZIONI UTENTE");
        final JButton registrationBtn = new JButton("REGISTRA TESSERATI");
        topNavPanel.add(infoBtn);
        topNavPanel.add(registrationBtn);
        contentPane.add(topNavPanel, BorderLayout.NORTH);
    }
}
