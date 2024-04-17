package unibo.cineradar.view.utilities;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.URL;

/**
 * A utility class that provides some constants for the view.
 */
public final class ViewUtilities {
    /**
     * The title of the login frame.
     */
    public static final String LOGIN_FRAME_TITLE = "CineRadar - Login";
    /**
     * The width of the fields contained in the login form.
     */
    public static final int LOGIN_FRAME_FIELD_WIDTH = 200;
    /**
     * The title of the signin frame.
     */
    public static final String SIGNIN_FRAME_TITLE = "CineRadar - Signin";
    /**
     * The width of the fields contained in the login form.
     */
    public static final int SIGNIN_FRAME_FIELD_WIDTH = 200;
    /**
     * The portion of the screen that equals the minimum size of the window.
     */
    public static final int FRAME_SIZE_FACTOR = 2;
    /**
     * The default path of images.
     */
    public static final String DEFAULT_IMAGE_PATH = "unibo/cineradar/images";
    /**
     * Five graphical points.
     */
    public static final int FIVE_PTS = 5;
    /**
     * Ten graphical points.
     */
    public static final int TEN_PTS = 10;
    /**
     * Twenty graphical points.
     */
    public static final int TWENTY_PTS = 20;
    /**
     * Zero graphical points.
     */
    public static final int NO_PTS = 0;

    private ViewUtilities() {

    }

    /**
     * Gets the URL of the specified resource.
     *
     * @param path The path of the resource.
     * @return A URL representing the resource in the FS.
     */
    public static URL getResourceURL(final String path) {
        return ClassLoader.getSystemClassLoader().getResource(path);
    }

    /**
     * Sets every constraint of a GridBagLayout.
     *
     * @param gbc       The GridBagConstraints to set.
     * @param gridX     The column where to place the element.
     * @param gridY     The row where to place the element.
     * @param colSpan   The span of the element between columns.
     * @param rowSpan   The span of the element between rows.
     * @param newInsets The insets (margins) of the element.
     */
    public static void setGridBagConstraints(
            final GridBagConstraints gbc,
            final int gridX,
            final int gridY,
            final int colSpan,
            final int rowSpan,
            final Insets newInsets) {
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = colSpan;
        gbc.gridheight = rowSpan;
        gbc.insets = newInsets;
    }
}
