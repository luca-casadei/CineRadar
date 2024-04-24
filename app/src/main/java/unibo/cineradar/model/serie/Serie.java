package unibo.cineradar.model.serie;

import unibo.cineradar.model.multimedia.Multimedia;

/**
 * The TV serie class.
 */
public class Serie extends Multimedia {
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
        super(id, title, ageLimit, plot, duration);
        this.numEpisodes = numEpisodes;
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
