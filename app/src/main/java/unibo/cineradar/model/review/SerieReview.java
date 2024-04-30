package unibo.cineradar.model.review;

/**
 * Represents a review for a series.
 */
public class SerieReview extends Review {
    private final int idSerie;
    private final String serieTitle;
    /**
     * Constructs a Serie review.
     *
     * @param idSerie       The id of the serie that has been reviewed.
     * @param serieTitle    The title of the serie that has been reviewed.
     * @param username      The username of the user that has written the review.
     * @param title         The title of the review.
     * @param description   The description of the review.
     * @param overallRating The overall rating of the review.
     */
    public SerieReview(final int idSerie, final String serieTitle, final String username, final String title, final String description, final int overallRating) {
        super(username, title, description, overallRating);
        this.idSerie = idSerie;
        this.serieTitle = serieTitle;
    }

    /**
     * Gets the code of the series.
     *
     * @return The code of the series.
     */
    public int getIdSerie() {
        return idSerie;
    }

    /**
     * Gets the title of the serie.
     *
     * @return The title of the serie.
     */
    public String getSerieTitle() {
        return serieTitle;
    }
}
