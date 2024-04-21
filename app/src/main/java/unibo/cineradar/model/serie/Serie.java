package unibo.cineradar.model.serie;

/**
 * The TV serie class.
 */
public class Serie {
    private final int id;
    private final String title;
    private final int ageLimit;
    private final String plot;
    private final int duration;
    private final int numEpisodes;

    /**
     * Constructs a tv serie instance.
     *
     * @param id          The id of the serie.
     * @param title       The title of the serie.
     * @param ageLimit    The age limite of the serie.
     * @param plot        The plot of the serie.
     * @param duration    The overall duration of the serie(in minutes).
     * @param numEpisodes The total number of episodes in the serie.
     */
    public Serie(final int id,
                 final String title,
                 final int ageLimit,
                 final String plot,
                 final int duration,
                 final int numEpisodes) {
        this.id = id;
        this.title = title;
        this.ageLimit = ageLimit;
        this.plot = plot;
        this.duration = duration;
        this.numEpisodes = numEpisodes;
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
     * Gets the number of episodes of the serie.
     *
     * @return An integer containing the number of episodes of the serie.
     */
    public int getNumberOfEpisodes() {
        return this.numEpisodes;
    }
}
