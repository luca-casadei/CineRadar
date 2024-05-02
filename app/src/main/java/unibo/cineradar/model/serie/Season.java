package unibo.cineradar.model.serie;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a season of a TV series.
 */
public class Season {
    private final int id;
    private final String summary;
    private final List<Episode> episodes;

    /**
     * Constructs a Season object.
     *
     * @param id       The code of the season.
     * @param summary  Summary of the season.
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
}
