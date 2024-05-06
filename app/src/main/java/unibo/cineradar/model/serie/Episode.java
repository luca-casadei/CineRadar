package unibo.cineradar.model.serie;

import java.util.Objects;

/**
 * Represents an episode of a TV series.
 *
 * @param seriesId     The id of the series.
 * @param seasonId The number of the season of this episode.
 * @param id           The episode number.
 * @param duration     The duration of the episode in minutes.
 */
public record Episode(int seriesId, int seasonId, int id, int duration) {
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.seriesId, this.seasonId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Episode episode) {
            return episode.id == this.id
                    && episode.seriesId == this.seriesId
                    && episode.seasonId == this.seasonId;
        } else {
            return false;
        }
    }
}
