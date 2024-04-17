package unibo.cineradar.view;

/**
 * A view component that provides the methods to create a JFrame and destroy it.
 */
public interface CineRadarViewFrame {
    /**
     * Shows the JFrame on screen.
     */
    void display();

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
}
