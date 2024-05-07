package unibo.cineradar.view.homepage.user.review;

import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.view.ViewContext;

import java.io.Serial;
import java.util.List;

/**
 * This class represents the view for writing a review for a film.
 */
public class WriteFilmReviewView extends WriteReviewView {
    @Serial
    private static final long serialVersionUID = -5729493403037904557L;

    /**
     * Constructs a new WriteFilmReviewView with the given user session context and film.
     *
     * @param userSessionContext the user session context
     * @param film               the film for which the review is being written
     */
    public WriteFilmReviewView(final ViewContext userSessionContext, final Film film) {
        super(userSessionContext, film);
    }

    /**
     * Inserts a film review into the database.
     *
     * @param multimediaId the ID of the film
     * @param title        the title of the review
     * @param desc         the description of the review
     * @param sections     the list of review sections
     */
    @Override
    public void insertReview(final int multimediaId,
                             final String title,
                             final String desc,
                             final List<ReviewSection> sections) {
        super.getUserSessionContext().reviewFilm(multimediaId, title, desc, sections);
    }
}
