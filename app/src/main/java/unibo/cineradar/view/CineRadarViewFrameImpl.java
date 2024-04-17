package unibo.cineradar.view;

import javax.swing.JFrame;
import java.util.Arrays;

/**
 * A container of a frame with some shared methods inside.
 */
public class CineRadarViewFrameImpl implements CineRadarViewFrame {
    private final JFrame mainFrame;

    /**
     * Constructor that sets the main frame of the window.
     */
    protected CineRadarViewFrameImpl() {
        this.mainFrame = new JFrame();
    }

    /**
     * Shows the JFrame on screen.
     */
    @Override
    public void display() {
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
