package unibo.cineradar.view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;

/**
 * A container of a frame with some shared methods inside.
 */
public class CineRadarViewFrameImpl implements CineRadarViewFrame {
    private static final int SCREEN_FRACTION_TO_ADD = 12;
    private final JFrame mainFrame;

    /**
     * Constructor that sets the main frame of the window.
     */
    protected CineRadarViewFrameImpl() {
        this.mainFrame = new JFrame();
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Shows the JFrame on screen.
     */
    @Override
    public void display(final boolean extend) {
        this.mainFrame.pack();
        this.mainFrame.setLocationRelativeTo(null);
        final Dimension currentSize = this.mainFrame.getSize();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.mainFrame.setSize(currentSize.width + screenSize.width / SCREEN_FRACTION_TO_ADD,
                currentSize.height + screenSize.height / SCREEN_FRACTION_TO_ADD);
        if (extend) {
            this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        this.mainFrame.setMinimumSize(this.mainFrame.getSize());
        this.mainFrame.setVisible(true);
    }

    @Override
    public final void destroy() {
        this.mainFrame.dispose();
    }

    @Override
    public final void disableEveryInternalComponent() {
        Arrays.stream(this.mainFrame.getContentPane().getComponents())
                .forEach(content -> content.setEnabled(false));
    }

    @Override
    public final void enableEveryInternalComponent() {
        Arrays.stream(this.mainFrame.getContentPane().getComponents())
                .forEach(component -> component.setEnabled(true));
    }

    /**
     * Gets the main frame of the view.
     *
     * @return A JFrame with its content pane inside.
     */
    protected JFrame getMainFrame() {
        return this.mainFrame;
    }
}
