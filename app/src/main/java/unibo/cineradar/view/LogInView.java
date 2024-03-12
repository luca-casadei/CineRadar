package unibo.cineradar.view;

import unibo.cineradar.utilities.Utilities;

import javax.swing.*;
import java.awt.*;

public final class LogInView implements CineRadarViewComponent {
    private final JFrame loginFrame;
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public LogInView() {
        final Toolkit tk = Toolkit.getDefaultToolkit();
        this.loginFrame = new JFrame();
        //Size setting
        this.loginFrame.setSize(tk.getScreenSize().width / 2, tk.getScreenSize().height / 2);
        //Title
        this.loginFrame.setTitle(Utilities.LOGIN_FRAME_TITLE);
        //Close on exit.
        this.loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setInternalComponents();
    }

    private void setInternalComponents() {
        // Main layout
        final JPanel contentPane = new JPanel(new BorderLayout());

        // Login form
        final JPanel loginForm = new JPanel();
        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));

        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JLabel usernameLabel = new JLabel("Username:");
        usernameField.setColumns(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        loginForm.add(usernamePanel);


        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JLabel passwordLabel = new JLabel("Password:");
        passwordField.setColumns(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        loginForm.add(passwordPanel);

        contentPane.add(loginForm, BorderLayout.CENTER);

        // Login button
        final JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.setBackground(Color.LIGHT_GRAY); 
        contentPane.add(loginButton, BorderLayout.SOUTH);
        this.loginFrame.setContentPane(contentPane);
    }

    public void display() {
        this.loginFrame.setVisible(true);
    }

    public void destroy() {
        this.loginFrame.dispose();
    }
}
