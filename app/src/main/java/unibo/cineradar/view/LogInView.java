package unibo.cineradar.view;

import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.view.homepage.AdminHomePageView;
import unibo.cineradar.view.homepage.RegistrarHomePageView;
import unibo.cineradar.view.homepage.UserHomePageView;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

// CHECKSTYLE: MagicNumber OFF
// Magic numbers are pixels used for view purposes.

/**
 * The view managing the login page to the application.
 */
public final class LogInView extends CineRadarViewFrameImpl {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JLabel statusLabel = new JLabel();
    private LoginType currentlySelectedLoginType = LoginType.USER;
    private ViewContext context;

    /**
     * Constructs the main frame of this view component.
     */
    public LogInView() {
        super();
        //Title
        this.getMainFrame().setTitle(ViewUtilities.LOGIN_FRAME_TITLE);
        //Close on exit.
        setInternalComponents();
    }

    @Override
    public void display(final boolean extend) {
        super.display(extend);
        this.testDBConnection();
    }

    private void testDBConnection() {
        this.disableEveryInternalComponent();
        try (DBManager mgr = new DBManager()) {
            if (!mgr.hasConnectionSucceeded()) {
                JOptionPane.showMessageDialog(this.getMainFrame(),
                        "Connessione al database non riuscita, l'applicazione verra' chiusa.",
                        "ERRORE DI CONNESSIONE AL DB",
                        JOptionPane.ERROR_MESSAGE);
                this.destroy();
            } else {
                this.enableEveryInternalComponent();
            }
        }
    }

    private void setInternalComponents() {
        //Main panel
        final JPanel contentPane = new JPanel(new GridBagLayout());
        //Main login form
        final GridBagConstraints gbc = new GridBagConstraints();

        //Row 0 - Col 0 - Logo
        ViewUtilities.setGridBagConstraints(gbc, 0, 0, 2, 1,
                new Insets(50, 0, 50, 10));
        final Image logoImage = new ImageIcon(ViewUtilities.getResourceURL(
                ViewUtilities.DEFAULT_IMAGE_PATH + "/logo.png"))
                .getImage().getScaledInstance(300, 50, Image.SCALE_SMOOTH);
        final JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(logoImage));
        contentPane.add(imageLabel, gbc);

        //LABELS

        gbc.fill = 1;

        //Row 1 - Col 0 - Username label
        ViewUtilities.setGridBagConstraints(gbc, 0, 1, 1, 1,
                new Insets(0, 0, 20, 10));
        contentPane.add(new JLabel("Username:"), gbc);

        //Row 2 - Col 0 - Password label
        ViewUtilities.setGridBagConstraints(gbc, 0, 2, 1, 1,
                new Insets(0, 0, 20, 10));
        contentPane.add(new JLabel("Password:"), gbc);

        //Row 5 - Col 0-1 Status
        ViewUtilities.setGridBagConstraints(gbc, 0, 4, 2, 1,
                new Insets(0, 0, 20, 0));
        statusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(statusLabel, gbc);

        //FIELDS
        gbc.ipady = 5;
        //Row 1 - Col 1 - Username field
        ViewUtilities.setGridBagConstraints(gbc, 1, 1, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(usernameField, gbc);
        //Row 2 - Col 1 - Password field
        ViewUtilities.setGridBagConstraints(gbc, 1, 2, 1, 1,
                new Insets(0, 0, 20, 0));
        contentPane.add(passwordField, gbc);

        //Row 3 Col 1 - Radios
        ViewUtilities.setGridBagConstraints(gbc, 0, 3, 2, 1,
                new Insets(0, 0, 20, 0));
        final JPanel radPanel = getRadioPanel();
        contentPane.add(radPanel, gbc);

        //Row 4 - Col 0-1 - Login Button
        ViewUtilities.setGridBagConstraints(gbc, 0, 5, 2, 1,
                new Insets(0, 0, 20, 0));
        final JButton loginButton = createLoginButton();
        contentPane.add(loginButton, gbc);

        //Row 5 - Col 0-1 Registration
        ViewUtilities.setGridBagConstraints(gbc, 0, 6, 2, 1,
                new Insets(0, 0, 50, 0));
        final JLabel registrationLabel = new JLabel("Non hai un account? Registrati!");
        registrationLabel.setForeground(Color.BLUE);
        registrationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registrationLabel.setVerticalAlignment(SwingConstants.CENTER);
        registrationLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registrationLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                new SignInView().display(false);
                destroy();
            }
        });
        contentPane.add(registrationLabel, gbc);

        this.getMainFrame().setContentPane(contentPane);
    }

    private JPanel getRadioPanel() {
        final JRadioButton radUserLogin = new JRadioButton("Accedi come utente");
        radUserLogin.addActionListener(e -> this.currentlySelectedLoginType = LoginType.USER);
        final JRadioButton radAdmLogin = new JRadioButton("Accedi come amministratore");
        radAdmLogin.addActionListener(e -> this.currentlySelectedLoginType = LoginType.ADMINISTRATION);
        final JRadioButton radRegLogin = new JRadioButton("Accedi come registratore");
        radRegLogin.addActionListener(e -> this.currentlySelectedLoginType = LoginType.REGISTRATION);
        final JPanel radPanel = new JPanel();
        radPanel.setLayout(new BoxLayout(radPanel, BoxLayout.Y_AXIS));
        radPanel.setBorder(new LineBorder(Color.black));
        final ButtonGroup loginGroup = new ButtonGroup();
        radUserLogin.setSelected(true);
        loginGroup.add(radUserLogin);
        loginGroup.add(radAdmLogin);
        loginGroup.add(radRegLogin);
        radPanel.add(radUserLogin);
        radPanel.add(radAdmLogin);
        radPanel.add(radRegLogin);
        return radPanel;
    }

    private JButton createLoginButton() {
        final JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            final char[] pw = passwordField.getPassword();
            this.context = new ViewContext(usernameField.getText(),
                    pw,
                    this.currentlySelectedLoginType);
            Arrays.fill(pw, '0');
            if (context.getController().sessionStatus()) {
                this.statusLabel.setForeground(Color.BLUE);
                this.statusLabel.setText("AUTORIZZATO");
                switch (this.context.getController().getUserType()) {
                    case ADMINISTRATION -> {
                        new AdminHomePageView(context).display(true);
                    }
                    case REGISTRATION -> {
                        new RegistrarHomePageView(context).display(true);
                    }
                    case USER -> {
                        new UserHomePageView(context).display(true);
                    }
                    default -> {
                        throw new IllegalStateException("Unknown User Type");
                    }
                }
                this.destroy();
            } else {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("NON AUTORIZZATO");
                usernameField.setText("");
            }
            passwordField.setText("");
        });
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
            }

            @Override
            public void keyPressed(final KeyEvent e) {
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
        return loginButton;
    }
}
// CHECKSTYLE: MagicNumber ON
