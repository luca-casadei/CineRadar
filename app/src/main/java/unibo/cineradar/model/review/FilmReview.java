package unibo.cineradar.model.review;

/**
 * Represents a review for a film.
 */
public class FilmReview extends Review {
    private final int idFilm;
    private final String filmTitle;

    /**
     * Constructs a Serie review.
     *
     * @param idFilm       The id of the film that has been reviewed.
     * @param filmTitle    The title of the film that has been reviewed.
     * @param username      The username of the user that has written the review.
     * @param title         The title of the review.
     * @param description   The description of the review.
     * @param overallRating The overall rating of the review.
     */
    public FilmReview(final int idFilm, final String filmTitle, final String username, final String title, final String description, final int overallRating) {
        super(username, title, description, overallRating);
        this.idFilm = idFilm;
        this.filmTitle = filmTitle;
    }

    /**
     * Gets the code of the film.
     *
     * @return The code of the film.
     */
    public int getIdFilm() {
        return idFilm;
    }

    /**
     * Gets the title of the film.
     *
     * @return The title of the film.
     */
    public String getFilmTitle() {
        return filmTitle;
    }
}
