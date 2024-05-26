package unibo.cineradar.model.multimedia;

import java.util.ArrayList;
import java.util.List;

/**
 * The multimedia class.
 */
public class Multimedia {
    private final String title;
    private final int ageLimit;
    private final String plot;
    private final int duration;
    private final List<Genre> genres;

    /**
     * Constructs a multimedia instance.
     *
     * @param title    The title of the film.
     * @param ageLimit The age limit of the film.
     * @param plot     The plot of the film.
     * @param duration The duration of the film(in minutes).
     */
    protected Multimedia(final String title, final int ageLimit, final String plot, final int duration) {
        this.title = title;
        this.ageLimit = ageLimit;
        this.plot = plot;
        this.duration = duration;
        this.genres = new ArrayList<>();
    }

    /**
     * Gets title of the multimedia.
     *
     * @return A string containing title of the multimedia.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the age limit of the multimedia.
     *
     * @return An integer containing the age limit of the multimedia.
     */
    public int getAgeLimit() {
        return this.ageLimit;
    }

    /**
     * Gets the plot of the multimedia.
     *
     * @return A string containing the plot of the multimedia.
     */
    public String getPlot() {
        return this.plot;
    }

    /**
     * Gets the duration of the multimedia.
     *
     * @return An integer containing the duration of the multimedia.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Adds new genre to genres list.
     *
     * @param genre The genre to add to the list.
     */
    public void addGenre(final Genre genre) {
        this.genres.add(genre);
    }

    /**
     * Gets the genres of the multimedia.
     *
     * @return The list of genres.
     */
    public List<Genre> getGenres() {
        return List.copyOf(this.genres);
    }
}
