package unibo.cineradar.model.review;

/**
 * Represents a review for a multimedia item (e.g., film, series).
 */
public abstract class Review {
    protected final String username;
    protected final String title;
    protected final String description;
    protected final int overallRating;

    /**
     * Constructs a review for a multimedia item.
     *
     * @param username      The username of the user that has written the review.
     * @param title         The title of the review.
     * @param description   The description of the review.
     * @param overallRating The overall rating of the review.
     */
    public Review(final String username, final String title, final String description, final int overallRating) {
        this.username = username;
        this.title = title;
        this.description = description;
        this.overallRating = overallRating;
    }

    /**
     * Gets the code of the multimedia item.
     *
     * @return The code of the multimedia item.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the title of the review.
     *
     * @return The title of the review.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the review.
     *
     * @return The description of the review.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the overall rating of the review.
     *
     * @return The overall rating of the review.
     */
    public int getOverallRating() {
        return overallRating;
    }
}
