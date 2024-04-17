package unibo.cineradar.view;

import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

/**
 * The view managing the sign-in page to the application.
 */
public final class SignInView extends CineRadarViewFrameImpl {
    private static final int SCREEN_AUGMENT_FACTOR_FOR_MINIMUM = 500;
    private static final int TOP_DOWN_MARGIN = ViewUtilities.TEN_PTS * ViewUtilities.FIVE_PTS;
    private final JTextField usernameField = new JTextField();
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField confirmPasswordField = new JPasswordField();

    /**
     * Constructs the main frame of this view component.
     */
    public SignInView() {
        super();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Size setting
        this.getMainFrame().setSize(screenSize.width / ViewUtilities.FRAME_SIZE_FACTOR,
                screenSize.height / ViewUtilities.FRAME_SIZE_FACTOR);
        this.getMainFrame().setMinimumSize(
                new Dimension((screenSize.width + SCREEN_AUGMENT_FACTOR_FOR_MINIMUM)
                        / (ViewUtilities.FRAME_SIZE_FACTOR * 2),
                        (screenSize.height + SCREEN_AUGMENT_FACTOR_FOR_MINIMUM)
                                / (ViewUtilities.FRAME_SIZE_FACTOR * 2)));
        // Title
        this.getMainFrame().setTitle(ViewUtilities.SIGNIN_FRAME_TITLE);
        // Close on exit.
        this.getMainFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setInternalComponents();
    }

    private void setInternalComponents() {
        // Main panel
        final JPanel contentPane = new JPanel(new GridBagLayout());

        // Main Signin form
        final GridBagConstraints gridbagConst = new GridBagConstraints();

        // Row 0 - Col 0 - Logo
        ViewUtilities.setGridBagConstraints(gridbagConst, 0, 0, 2, 1,
                new Insets(TOP_DOWN_MARGIN, 0, TOP_DOWN_MARGIN, 10));
        final Image logoImage = new ImageIcon(
                ViewUtilities.getResourceURL(
                        ViewUtilities.DEFAULT_IMAGE_PATH + "/logo.png"))
                .getImage()
                .getScaledInstance(TOP_DOWN_MARGIN * 6, TOP_DOWN_MARGIN, Image.SCALE_SMOOTH);
        final JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(logoImage));
        contentPane.add(imageLabel, gridbagConst);

        // Row 1 - Col 0 - First name label
        ViewUtilities.setGridBagConstraints(gridbagConst, 0, 1, 1, 1,
                new Insets(0, 0, 10, 10));
        contentPane.add(new JLabel("First name:"), gridbagConst);

        // Row 1 - Col 1 - First name field
        gridbagConst.ipadx = ViewUtilities.SIGNIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gridbagConst, 1, 1, 1, 1,
                new Insets(ViewUtilities.NO_PTS, ViewUtilities.NO_PTS, ViewUtilities.TEN_PTS, ViewUtilities.NO_PTS));
        contentPane.add(firstNameField, gridbagConst);

        // Row 2 - Col 0 - Last name label
        gridbagConst.ipadx = ViewUtilities.NO_PTS;
        ViewUtilities.setGridBagConstraints(gridbagConst, ViewUtilities.NO_PTS, 2, 1, 1,
                new Insets(ViewUtilities.NO_PTS, ViewUtilities.NO_PTS, 10, 10));
        contentPane.add(new JLabel("Last name:"), gridbagConst);

        // Row 2 - Col 1 - Last name field
        gridbagConst.ipadx = ViewUtilities.SIGNIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gridbagConst, 1, 2, 1, 1,
                new Insets(ViewUtilities.NO_PTS, ViewUtilities.NO_PTS, ViewUtilities.TEN_PTS, ViewUtilities.NO_PTS));
        contentPane.add(lastNameField, gridbagConst);

        // Row 3 - Col 0 - Username label
        gridbagConst.ipadx = 0;
        ViewUtilities.setGridBagConstraints(gridbagConst, 0, 3, 1, 1,
                new Insets(0, 0, ViewUtilities.TEN_PTS, ViewUtilities.TEN_PTS));
        contentPane.add(new JLabel("Username:"), gridbagConst);

        // Row 3 - Col 1 - Username field
        gridbagConst.ipadx = ViewUtilities.SIGNIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gridbagConst, 1, 3, 1, 1,
                new Insets(0, 0, ViewUtilities.TEN_PTS, 0));
        contentPane.add(usernameField, gridbagConst);

        // Row 4 - Col 0 - Password label
        gridbagConst.ipadx = 0;
        ViewUtilities.setGridBagConstraints(gridbagConst, 0, 4, 1, 1,
                new Insets(0, 0, ViewUtilities.TEN_PTS, ViewUtilities.TEN_PTS));
        contentPane.add(new JLabel("Password:"), gridbagConst);

        // Row 4 - Col 1 - Password field
        gridbagConst.ipadx = ViewUtilities.SIGNIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gridbagConst, 1, 4, 1, 1,
                new Insets(0, 0, 10, 0));
        contentPane.add(passwordField, gridbagConst);

        final int confirmPasswordY = ViewUtilities.FIVE_PTS;
        // Row 5 - Col 0 - Confirm password label
        gridbagConst.ipadx = ViewUtilities.FIVE_PTS * ViewUtilities.TEN_PTS;
        ViewUtilities.setGridBagConstraints(gridbagConst, 0, confirmPasswordY, 1, 1,
                new Insets(0, 0, 10, 10));
        contentPane.add(new JLabel("Confirm Password:"), gridbagConst);

        // Row 5 - Col 1 -  Confirm password field
        gridbagConst.ipadx = ViewUtilities.SIGNIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gridbagConst, 1, confirmPasswordY, 1, 1,
                new Insets(0, 0, 10, 0));
        contentPane.add(confirmPasswordField, gridbagConst);

        final int signInButtonY = 6;
        // Row 6 - Col 0-1 - SignIn Button
        gridbagConst.ipadx = 100;
        ViewUtilities.setGridBagConstraints(gridbagConst, 0, signInButtonY, 2, 1,
                new Insets(10 * 2, 0, TOP_DOWN_MARGIN, 0));
        final JButton signinButton = new JButton("Signin");
        contentPane.add(signinButton, gridbagConst);

        this.getMainFrame().setContentPane(contentPane);
        this.getMainFrame().pack();
    }
}
