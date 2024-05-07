package unibo.cineradar.model.review;

/**
 * A representation of a review section.
 *
 * @param multimediaId The id of the associated multimedia.
 * @param section      The section that contain name and detail.
 * @param score        The score given to the section.
 */
public record ReviewSection(int multimediaId, Section section, int score) {
}
