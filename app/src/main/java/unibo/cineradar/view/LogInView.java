package unibo.cineradar.view;

import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * The view managing the login page to the application.
 */
public final class LogInView implements CineRadarViewComponent {
    private final JFrame loginFrame;
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    /**
     * Constructs the main frame of this view component.
     */
    public LogInView() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.loginFrame = new JFrame();
        //Size setting
        this.loginFrame.setSize(screenSize.width / ViewUtilities.FRAME_SIZE_FACTOR,
                screenSize.height / ViewUtilities.FRAME_SIZE_FACTOR);
        this.loginFrame.setMinimumSize(
                new Dimension(screenSize.width / (ViewUtilities.FRAME_SIZE_FACTOR * 2),
                        screenSize.height / (ViewUtilities.FRAME_SIZE_FACTOR * 2)));
        //Title
        this.loginFrame.setTitle(ViewUtilities.LOGIN_FRAME_TITLE);
        //Close on exit.
        this.loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setInternalComponents();
    }

    private void setInternalComponents() {
        //Main panel
        final JPanel contentPane = new JPanel(new GridBagLayout());

        //Main login form
        final GridBagConstraints gbc = new GridBagConstraints();

        //Row 0 - Col 0
        gbc.insets = new Insets(0, 0, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(new JLabel("Username:"), gbc);

        //Row 0 - Col 1
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.gridx = 1;
        gbc.ipadx = ViewUtilities.LOGIN_FRAME_FIELD_WIDTH;
        contentPane.add(usernameField, gbc);

        //Row 1 - Col 0
        gbc.insets = new Insets(0, 0, 10, 10);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        contentPane.add(new JLabel("Password:"), gbc);

        //Row 1 - Col 1
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.gridx = 1;
        gbc.ipadx = ViewUtilities.LOGIN_FRAME_FIELD_WIDTH;
        contentPane.add(passwordField, gbc);

        //Row 2 - Col 0-1 Login Button
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.ipadx = 100;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10 * 2, 0, 0, 0);
        final JButton loginButton = new JButton("Login");
        contentPane.add(loginButton, gbc);

        this.loginFrame.setContentPane(contentPane);
    }

    @Override
    public void display() {
        this.loginFrame.pack();
        this.loginFrame.setVisible(true);
    }

    @Override
    public void destroy() {
        this.loginFrame.dispose();
    }
}
