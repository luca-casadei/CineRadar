package unibo.cineradar.model.serie;

import unibo.cineradar.model.multimedia.Multimedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The TV serie class.
 */
public final class Serie extends Multimedia {

    private final int seriesId;
    private final int numEpisodes;
    private final List<Season> seasons;

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
        this.seasons = new ArrayList<>();
    }

    /**
     * Gets the series' identifier.
     *
     * @return The unique ID of the series.
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * Adds a season to the list of the seasons.
     *
     * @param season The season to add to the list.
     */
    public void addSeason(final Season season) {
        if (!this.seasons.contains(season)) {
            this.seasons.add(season);
        }
    }

    /**
     * Gets the specific season.
     *
     * @param seasonToGet The specific season of a series.
     * @return The specific season in the series.
     */
    public Season getSeason(final Season seasonToGet) {
        return this.seasons.get(this.seasons.indexOf(seasonToGet));
    }

    /**
     * Gets the seasons of the series.
     *
     * @return The list of seasons.
     */
    public List<Season> getSeasons() {
        return List.copyOf(this.seasons);
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
