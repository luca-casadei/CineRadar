package unibo.cineradar.model.review;

import java.util.ArrayList;
import java.util.List;

/**
 * The full film review, with sections.
 */
public final class FullFilmReview extends FilmReview {

    private final List<ReviewSection> sections;

    /**
     * Constructs a Serie review.
     *
     * @param idFilm        The id of the film that has been reviewed.
     * @param filmTitle     The title of the film that has been reviewed.
     * @param username      The username of the user that has written the review.
     * @param title         The title of the review.
     * @param description   The description of the review.
     * @param overallRating The overall rating of the review.
     */
    public FullFilmReview(final int idFilm,
                          final String filmTitle,
                          final String username,
                          final String title,
                          final String description,
                          final int overallRating) {
        super(idFilm, filmTitle, username, title, description, overallRating);
        this.sections = new ArrayList<>();
    }

    /**
     * Gets the list of sections.
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
        this.sections.add(section);
    }
}
