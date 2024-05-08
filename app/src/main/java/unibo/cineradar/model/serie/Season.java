package unibo.cineradar.model.serie;

import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a season of a TV series.
 */
public final class Season {
    private final int seriesId;
    private final int id;
    private final String summary;
    private final List<Episode> episodes;
    private final Cast castSeries;

    /**
     * Constructs a Season object.
     *
     * @param id       The code of the season.
     * @param summary  Summary of the season.
     * @param seriesId The code of the series.
     */
    public Season(final int seriesId, final int id, final String summary) {
        this.id = id;
        this.summary = summary;
        this.seriesId = seriesId;
        this.episodes = new ArrayList<>();
        this.castSeries = new Cast();
    }

    /**
     * Gets the code of the series linked to.
     *
     * @return The code of the series linked to.
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * Gets the code of the season.
     *
     * @return The code of the season.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the summary of the season.
     *
     * @return The summary of the season.
     */
    public String getSummary() {
        return this.summary;
    }

    /**
     * Gets the list of episodes in the season.
     *
     * @return The list of episodes in the season.
     */
    public List<Episode> getEpisodes() {
        return List.copyOf(this.episodes);
    }

    /**
     * Gets the cast of the season.
     *
     * @return The cast of the season.
     */
    public Cast getCast() {
        return new Cast(this.castSeries.getCastMemberList());
    }

    /**
     * Adds an episode to the list of the episodes.
     *
     * @param episode The episode to add to the list.
     */
    public void addEpisode(final Episode episode) {
        if (!this.episodes.contains(episode)) {
            this.episodes.add(episode);
        }
    }

    /**
     * Adds a cast member to the cast.
     *
     * @param castMember The cast member to add to the cast.
     */
    public void addCastMember(final CastMember castMember) {
        this.castSeries.addCastMember(castMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.seriesId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Season season) {
            return season.id == this.id
                    && season.seriesId == this.seriesId;
        } else {
            return false;
        }
    }
}
