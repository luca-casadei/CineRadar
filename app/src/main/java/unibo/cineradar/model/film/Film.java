package unibo.cineradar.model.film;

/**
 * The film class.
 */
public class Film {
    private final int id;
    private final String title;
    private final int ageLimit;
    private final String plot;
    private final int duration;
    private final int castId;

    /**
     * Constructs a film instance.
     *
     * @param id       The id of the film.
     * @param title    The title of the film.
     * @param ageLimit The age limit of the film.
     * @param plot     The plot of the film.
     * @param duration The duration of the film.
     * @param castId   The cast id that was in the film.
     */
    public Film(final int id, final String title, final int ageLimit, final String plot, final int duration, final int castId) {
        this.id = id;
        this.title = title;
        this.ageLimit = ageLimit;
        this.plot = plot;
        this.duration = duration;
        this.castId = castId;
    }

    /**
     * Gets the id of the film.
     *
     * @return An integer containing the id of the film.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets title of the film.
     *
     * @return A string containing title of the film.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the age limit of the film.
     *
     * @return An integer containing the age limit of the film.
     */
    public int getAgeLimit() {
        return this.ageLimit;
    }

    /**
     * Gets the plot of the film.
     *
     * @return A string containing the plot of the film.
     */
    public String getPlot() {
        return this.plot;
    }

    /**
     * Gets the duration of the film.
     *
     * @return An integer containing the duration of the film.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Gets the cast id of the film.
     *
     * @return An integer containing the cast id of the film.
     */
    public int getCastId() {
        return this.castId;
    }
}
