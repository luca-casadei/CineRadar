package unibo.cineradar.model.serie;

import java.util.Objects;

/**
 * Represents an episode of a TV series.
 */
public final class Episode {
    // TODO: mettere idSeason e idSerie
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

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Episode episode) {
            return episode.id == this.id;
        } else {
            return false;
        }
    }
}
