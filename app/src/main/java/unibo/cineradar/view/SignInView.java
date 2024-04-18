package unibo.cineradar.view;

import unibo.cineradar.model.login.Logger;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

/**
 * The view managing the sign-in page to the application.
 */
public final class SignInView extends CineRadarViewFrameImpl {
    private static final int SCREEN_AUGMENT_FACTOR_FOR_MINIMUM = 500;
    private final JTextField usernameField = new JTextField();
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField confirmPasswordField = new JPasswordField();
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

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

        // Main Sign-In form
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // Row 0 - Col 0 - Logo
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 0, 2, 1,
                new Insets(50, 0, 50, 10));
        final Image logoImage = new ImageIcon(
                ViewUtilities.getResourceURL(
                        ViewUtilities.DEFAULT_IMAGE_PATH + "/logo.png"))
                .getImage()
                .getScaledInstance(300, 50, Image.SCALE_SMOOTH);
        final JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(logoImage));
        contentPane.add(imageLabel, gridBagConstraints);

        //LABELS
        // Row 1 - Col 0 - First name label
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 1, 1, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(new JLabel("Nome:", SwingConstants.LEFT), gridBagConstraints);

        // Row 2 - Col 0 - Last name label
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 2, 1, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(new JLabel("Cognome:", SwingConstants.LEFT), gridBagConstraints);

        // Row 3 - Col 0 - Username label
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 3, 1, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(new JLabel("Username:", SwingConstants.LEFT), gridBagConstraints);

        // Row 4 - Col 0 - Password label
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 4, 1, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(new JLabel("Password:", SwingConstants.LEFT), gridBagConstraints);

        // Row 5 - Col 0 - Confirm password label
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 5, 1, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(new JLabel("Password:", SwingConstants.LEFT), gridBagConstraints);

        //Row 6 - Col 0 - BD Label
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 6, 2, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(new JLabel("Data di nascita:", SwingConstants.LEFT), gridBagConstraints);

        //Row 8 - Col 0-1 - Status
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 8, 2, 1,
                new Insets(0, 0, 20, 20));
        contentPane.add(statusLabel, gridBagConstraints);

        //FIELDS

        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 10;

        // Row 1 - Col 1 - First name field
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 1, 1, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(firstNameField, gridBagConstraints);

        // Row 2 - Col 1 - Last name field
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 1, 2, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(lastNameField, gridBagConstraints);

        // Row 3 - Col 1 - Username field
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 1, 3, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(usernameField, gridBagConstraints);

        // Row 4 - Col 1 - Password field
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 1, 4, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(passwordField, gridBagConstraints);

        // Row 5 - Col 1 -  Confirm password field
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 1, 5, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(confirmPasswordField, gridBagConstraints);

        // Row 7 - Col 1 - birthdate
        gridBagConstraints.ipadx = 0;
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 7, 2, 1,
                new Insets(0, 0, 20, 0));
        //contentPane.add((Component) date, gridBagConstraints);

        // Row 9 - Col 0-1 - SignIn Button
        gridBagConstraints.ipadx = 100;
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 9, 2, 1,
                new Insets(0, 0, 20, 0));
        final JButton signinButton = getSignInButton();
        contentPane.add(signinButton, gridBagConstraints);

        //Row 10 - COl 0-1 - Back to Log in
        ViewUtilities.setGridBagConstraints(gridBagConstraints, 0, 10, 2, 1,
                new Insets(0, 0, 50, 0));
        final JLabel goBackLabel = new JLabel("Hai gia' un account? Torna al Login");
        goBackLabel.setForeground(Color.BLUE);
        goBackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goBackLabel.setVerticalAlignment(SwingConstants.CENTER);
        goBackLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        goBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                new LogInView().display();
                destroy();
            }
        });
        contentPane.add(goBackLabel, gridBagConstraints);

        this.getMainFrame().setContentPane(contentPane);
        this.getMainFrame().pack();
    }

    private JButton getSignInButton() {
        final JButton signInButton = new JButton("REGISTRATI");
        signInButton.addActionListener(e -> {
            final String username = this.usernameField.getText();
            final String password = new String(this.passwordField.getPassword());
            final String firstName = this.firstNameField.getText();
            final String lastName = this.lastNameField.getText();
            //TODO
            final Date date = new Date(1);
            if (username.isBlank() || password.isBlank() || firstName.isBlank() || lastName.isBlank()) {
                statusLabel.setText("Compila tutti i campi");
                statusLabel.setForeground(Color.ORANGE);
            } else {
                if (Logger.signIn(username,
                        password,
                        firstName,
                        lastName,
                        date)) {
                    statusLabel.setForeground(Color.BLUE);
                    statusLabel.setText("Registrazione avvenuta con successo.");
                } else {
                    statusLabel.setForeground(Color.red);
                    statusLabel.setText("Registrazione non avvenuta, si sono verificati degli errori.");
                }
                new LogInView().display();
                destroy();
            }
        });
        return signInButton;
    }
}
