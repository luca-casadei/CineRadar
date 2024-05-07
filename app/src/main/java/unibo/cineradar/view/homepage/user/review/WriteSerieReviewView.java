package unibo.cineradar.view.homepage.user.review;

import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import java.io.Serial;
import java.util.List;

/**
 * This class represents the view for writing a review for a TV series.
 */
public class WriteSerieReviewView extends WriteReviewView {
    @Serial
    private static final long serialVersionUID = -5735493403413904557L;

    /**
     * Constructs a new WriteSerieReviewView with the given user session context and TV series.
     *
     * @param userSessionContext the user session context
     * @param series             the TV series for which the review is being written
     */
    public WriteSerieReviewView(final ViewContext userSessionContext, final Serie series) {
        super(userSessionContext, series);
    }

    /**
     * Inserts a TV series review into the database.
     *
     * @param multimediaId the ID of the TV series
     * @param title        the title of the review
     * @param desc         the description of the review
     * @param sections     the list of review sections
     */
    @Override
    public void insertReview(final int multimediaId,
                             final String title,
                             final String desc,
                             final List<ReviewSection> sections) {
        super.getUserSessionContext().reviewSeries(multimediaId, title, desc, sections);
    }
}
