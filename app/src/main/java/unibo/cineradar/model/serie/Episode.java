package unibo.cineradar.model.serie;

/**
 * Represents an episode of a TV series.
 */
public class Episode {
    private final int id;
    private final int duration;

    /**
     * Constructs an Episode object.
     *
     * @param id The ID of the episode.
     * @param duration The duration of the episode.
     */
    public Episode(final int id, final int duration) {
        this.id = id;
        this.duration = duration;
    }

    /**
     * Gets the ID of the episode.
     *
     * @return The ID of the episode.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the duration of the episode.
     *
     * @return The duration of the episode.
     */
    public int getDuration() {
        return duration;
    }
}
