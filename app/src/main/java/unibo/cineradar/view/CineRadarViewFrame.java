package unibo.cineradar.view;

/**
 * A view component that provides the methods to create a JFrame and destroy it.
 */
public interface CineRadarViewFrame {
    /**
     * Shows the JFrame on screen.
     *
     * @param extend Set to true if the frame needs to be fullscreen.
     */
    void display(boolean extend);

    /**
     * Destroys the JFrame and frees the memory.
     */
    void destroy();

    /**
     * Disables every internal component of the main frame.
     */
    void disableEveryInternalComponent();

    /**
     * Enables every internal component of the main frame.
     */
    void enableEveryInternalComponent();

    /**
     * Enables the frame.
     */
    void enable();

    /**
     * Disables the frame.
     */
    void disable();
}
