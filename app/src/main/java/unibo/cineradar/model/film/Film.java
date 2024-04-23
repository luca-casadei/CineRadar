package unibo.cineradar.model.film;

import unibo.cineradar.model.multimedia.Multimedia;

/**
 * The film class.
 */
public class Film extends Multimedia {
    private final int castId;

    /**
     * Constructs a film instance.
     *
     * @param id       The id of the film.
     * @param title    The title of the film.
     * @param ageLimit The age limit of the film.
     * @param plot     The plot of the film.
     * @param duration The duration of the film(in minutes).
     * @param castId   The cast id that was in the film.
     */
    public Film(final int id,
                final String title,
                final int ageLimit,
                final String plot,
                final int duration,
                final int castId) {
        super(id, title, ageLimit, plot, duration);
        this.castId = castId;
    }

    /**
     * Gets the cast id of the film.
     *
     * @return An integer containing the cast id of the film.
     */
    public int getCastId() {
        return this.castId;
    }
}
