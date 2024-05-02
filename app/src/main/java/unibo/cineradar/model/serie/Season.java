package unibo.cineradar.model.serie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a season of a TV series.
 */
public class Season {
    // TODO: mettere idSerie
    private final int id;
    private final String summary;
    private final List<Episode> episodes;

    /**
     * Constructs a Season object.
     *
     * @param id      The code of the season.
     * @param summary Summary of the season.
     */
    public Season(final int id, final String summary) {
        this.id = id;
        this.summary = summary;
        this.episodes = new ArrayList<>();
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
     * Adds an episode to the list of the season.
     *
     * @param episode The episode to add to the list.
     */
    public void addEpisode(final Episode episode) {
        if(!this.episodes.contains(episode)){
            this.episodes.add(episode);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Season season) {
            return season.id == this.id;
        } else {
            return false;
        }
    }
}
