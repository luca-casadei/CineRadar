package unibo.cineradar.model.review;

/**
 * A representation of a review section.
 *
 * @param reviewId The id of the associated review.
 * @param section  The section that contain name and detail.
 * @param score    The score given to the section.
 */
public record ReviewSection(int reviewId, Section section, int score) {
}
