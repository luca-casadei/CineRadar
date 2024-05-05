package unibo.cineradar.model.serie;

import java.util.Objects;

/**
 * Represents an episode of a TV series.
 *
 * @param id           The episode number,
 * @param seriesCode   The code of the series.
 * @param seasonNumber The number of the season of this episode.
 * @param duration     The duration of the episode in minutes.
 */
public record Episode(int id, int seriesCode, int seasonNumber, int duration) {
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.seriesCode, this.seasonNumber);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Episode episode) {
            return episode.id == this.id
                    && episode.seriesCode == this.seriesCode
                    && episode.seasonNumber == this.seasonNumber;
        } else {
            return false;
        }
    }
}
