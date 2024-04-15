package unibo.cineradar.view;

import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

/**
 * The view managing the login page to the application.
 */
public final class LogInView implements CineRadarViewComponent {
    private static final int SCREEN_AUGMENT_FACTOR_FOR_MINIMUM = 500;
    private static final int TOP_DOWN_MARGIN = 50;
    private final JFrame loginFrame;
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JLabel statusLabel = new JLabel();
    private ViewContext context;

    /**
     * Constructs the main frame of this view component.
     */
    public LogInView() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.loginFrame = new JFrame();

        //Size setting
        this.loginFrame.setSize(screenSize.width / ViewUtilities.FRAME_SIZE_FACTOR, screenSize.height
                / ViewUtilities.FRAME_SIZE_FACTOR);
        this.loginFrame.setMinimumSize(new Dimension((screenSize.width + SCREEN_AUGMENT_FACTOR_FOR_MINIMUM)
                / (ViewUtilities.FRAME_SIZE_FACTOR * 2), (screenSize.height + SCREEN_AUGMENT_FACTOR_FOR_MINIMUM)
                / (ViewUtilities.FRAME_SIZE_FACTOR * 2)));

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

        //Row 0 - Col 0 - Logo
        ViewUtilities.setGridBagConstraints(gbc, 0, 0, 2, 1,
                new Insets(TOP_DOWN_MARGIN, 0, TOP_DOWN_MARGIN, 10));
        final Image logoImage = new ImageIcon(ViewUtilities.getResourceURL(
                ViewUtilities.DEFAULT_IMAGE_PATH + "/logo.png"))
                .getImage().getScaledInstance(TOP_DOWN_MARGIN * 6, TOP_DOWN_MARGIN, Image.SCALE_SMOOTH);
        final JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(logoImage));
        contentPane.add(imageLabel, gbc);

        //Row 1 - Col 0 - Username label
        ViewUtilities.setGridBagConstraints(gbc, 0, 1, 1, 1,
                new Insets(0, 0, 10, 10));
        contentPane.add(new JLabel("Username:"), gbc);

        //Row 1 - Col 1 - Username field
        gbc.ipadx = ViewUtilities.LOGIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gbc, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));
        contentPane.add(usernameField, gbc);

        //Row 2 - Col 0 - Password label
        gbc.ipadx = 0;
        ViewUtilities.setGridBagConstraints(gbc, 0, 2, 1, 1,
                new Insets(0, 0, 10, 10));
        contentPane.add(new JLabel("Password:"), gbc);

        //Row 2 - Col 1 - Password field
        gbc.ipadx = ViewUtilities.LOGIN_FRAME_FIELD_WIDTH;
        ViewUtilities.setGridBagConstraints(gbc, 1, 2, 1, 1,
                new Insets(0, 0, 10, 0));
        contentPane.add(passwordField, gbc);

        //Row 3 - Col 0-1 - Login Button
        gbc.ipadx = 100;
        ViewUtilities.setGridBagConstraints(gbc, 0, 3, 2, 1,
                new Insets(10 * 2, 0, 0, 0));
        final JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            this.context = new ViewContext(usernameField.getText(), new String(passwordField.getPassword()));
            if (context.getController().sessionStatus()) {
                this.statusLabel.setForeground(Color.BLUE);
                this.statusLabel.setText("AUTHORIZED");
            } else {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("UNAUTHORIZED");
                passwordField.setText("");
                usernameField.setText("");
            }
        });
        contentPane.add(loginButton, gbc);

        //Row 4 - Col 0-1 Status
        ViewUtilities.setGridBagConstraints(gbc, 0, 4, 2, 1,
                new Insets(TOP_DOWN_MARGIN, 0, TOP_DOWN_MARGIN, 0));
        statusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(statusLabel, gbc);

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
