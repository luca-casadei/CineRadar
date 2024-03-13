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
