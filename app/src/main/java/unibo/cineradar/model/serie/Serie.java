package unibo.cineradar.model.serie;

import unibo.cineradar.model.multimedia.Multimedia;

import java.util.Objects;

/**
 * The TV serie class.
 */
public final class Serie extends Multimedia {

    private final int numEpisodes;
    private final int seriesId;

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
        super(title, ageLimit, plot, duration);
        this.numEpisodes = numEpisodes;
        this.seriesId = id;
    }

    /**
     * Gets the series' identifier.
     *
     * @return The unique ID of the series.
     */
    public int getSeriesId() {
        return seriesId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seriesId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Serie serie) {
            return serie.seriesId == this.seriesId;
        } else {
            return false;
        }
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
