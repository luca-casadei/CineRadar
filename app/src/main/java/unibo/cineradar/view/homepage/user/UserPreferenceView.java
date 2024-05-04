package unibo.cineradar.view.homepage.user;

import unibo.cineradar.controller.GlobalController;
import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.view.CineRadarViewFrameImpl;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Preferences page view.
 */
// CHECKSTYLE: MagicNumber OFF
public final class UserPreferenceView extends CineRadarViewFrameImpl {

    private final List<Genre> toEnable;

    /**
     * Constructs an instance of UserPanel.
     *
     * @param parent                The parent component to disable.
     * @param currentSessionContext The session context of the user.
     */
    public UserPreferenceView(final CineRadarViewFrameImpl parent, final ViewContext currentSessionContext) {
        final UserSessionController uc = (UserSessionController) currentSessionContext.getController();
        toEnable = new ArrayList<>();
        this.getMainFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        final JPanel contentPane = new JPanel(new BorderLayout());
        this.getMainFrame().setContentPane(contentPane);
        contentPane.add(getTitleLabel(), BorderLayout.NORTH);
        this.getMainFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (JOptionPane.showConfirmDialog(getMainFrame(),
                        "Confermare le preferenze?",
                        "Conferma",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    uc.clearPreferences();
                    uc.addPreference(toEnable);
                    parent.enable();
                    destroy();
                }
            }
        });
        setInternalComponents(uc);
    }

    private JLabel getTitleLabel() {
        final JLabel title = new JLabel("SELEZIONA LE TUE PREFERENZE");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        return title;
    }

    private void setInternalComponents(final UserSessionController uc) {
        final JPanel chkP = new JPanel();
        chkP.setLayout(new BoxLayout(chkP, BoxLayout.Y_AXIS));
        this.getMainFrame().getContentPane().add(chkP, BorderLayout.CENTER);
        toEnable.addAll(uc.getUserPrefs());
        for (final Genre g : GlobalController.getGenreList()) {
            final JCheckBox gChk = new JCheckBox();
            gChk.setText(String.format("[%s]: %s", g.name(), g.description()));
            gChk.setSelected(toEnable.contains(g));
            gChk.addActionListener(e -> {
                final JCheckBox eChk = (JCheckBox) e.getSource();
                if (eChk.isSelected()) {
                    toEnable.add(g);
                } else {
                    toEnable.remove(g);
                }
            });
            chkP.add(gChk);
        }
    }
}
// CHECKSTYLE: MagicNumber ON
