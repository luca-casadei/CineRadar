package unibo.cineradar.model.multimedia;

/**
 * The multimedia class.
 */
public class Multimedia {
    private final int id;
    private final String title;
    private final int ageLimit;
    private final String plot;
    private final int duration;

    /**
     * Constructs a multimedia instance.
     *
     * @param id       The id of the film.
     * @param title    The title of the film.
     * @param ageLimit The age limit of the film.
     * @param plot     The plot of the film.
     * @param duration The duration of the film(in minutes).
     */
    public Multimedia(final int id, final String title, final int ageLimit, final String plot, final int duration) {
        this.id = id;
        this.title = title;
        this.ageLimit = ageLimit;
        this.plot = plot;
        this.duration = duration;
    }

    /**
     * Gets the id of the multimedia.
     *
     * @return An integer containing the id of the multimedia.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets title of the multimedia.
     *
     * @return A string containing title of the multimedia.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the age limit of the multimedia.
     *
     * @return An integer containing the age limit of the multimedia.
     */
    public int getAgeLimit() {
        return this.ageLimit;
    }

    /**
     * Gets the plot of the multimedia.
     *
     * @return A string containing the plot of the multimedia.
     */
    public String getPlot() {
        return this.plot;
    }

    /**
     * Gets the duration of the multimedia.
     *
     * @return An integer containing the duration of the multimedia.
     */
    public int getDuration() {
        return this.duration;
    }
}
