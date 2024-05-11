package unibo.cineradar.model.review;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a full review, with sections.
 */
public final class FullSeriesReview extends SeriesReview {

    private final List<ReviewSection> sections;

    /**
     * Constructs a Serie review.
     *
     * @param idSeries      The id of the series that has been reviewed.
     * @param serieTitle    The title of the series that has been reviewed.
     * @param username      The username of the user that has written the review.
     * @param title         The title of the review.
     * @param description   The description of the review.
     * @param overallRating The overall rating of the review.
     */
    public FullSeriesReview(final int idSeries,
                            final String serieTitle,
                            final String username,
                            final String title,
                            final String description,
                            final int overallRating) {
        super(idSeries, serieTitle, username, title, description, overallRating);
        sections = new ArrayList<>();
    }

    /**
     * Gets the reviewed sections.
     *
     * @return A list of reviewed sections.
     */
    public List<ReviewSection> getSections() {
        return List.copyOf(sections);
    }

    /**
     * Adds a section.
     *
     * @param section The section to add.
     */
    public void addSection(final ReviewSection section) {
        sections.add(section);
    }
}
