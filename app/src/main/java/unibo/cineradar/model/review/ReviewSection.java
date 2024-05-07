package unibo.cineradar.model.review;

/**
 * A representation of a review section.
 *
 * @param reviewId    The id of the associated review.
 * @param sectName    The name of the reviewed section.
 * @param description The full description of the section.
 * @param score       The score given to the section.
 */
public record ReviewSection(int reviewId, String sectName, String description, int score) {
}
