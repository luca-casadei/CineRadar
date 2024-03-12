package unibo.cineradar.view;

/**
 * A view component that provides the methods to create a JFrame and destroy it.
 */
public interface CineRadarViewComponent {
    /**
     * Shows the JFrame on screen.
     */
    void display();

    /**
     * Destroys the JFrame and frees the memory.
     */
    void destroy();
}
