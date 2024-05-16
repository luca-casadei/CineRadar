package unibo.cineradar.model.context.user;

import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.context.SessionContextImpl;

import unibo.cineradar.model.db.UserOps;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.FullSeriesReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.User;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The context of a user session.
 */
public final class UserContext extends SessionContextImpl {
    private final User user;
    private final Map<Film, Cast> detailedFilms;
    private final List<Serie> detailedSeries;

    /**
     * Constructs the context of a user.
     *
     * @param loggedAccount The currently logged account.
     */
    public UserContext(final Account loggedAccount) {
        super(loggedAccount);
        try (UserOps mgr = new UserOps()) {
            this.user = mgr.getUserDetails(super.getUsername()).orElse(null);
            this.detailedFilms = mgr.getDetailedFilms();
            this.detailedSeries = mgr.getDetailedSeries();
        }
    }

    private int getUserAge() {
        return Year.now().getValue() - (user.getBirthDate().getYear());
    }

    /**
     * Gets the films.
     *
     * @return The list of all films.
     */
    public List<Film> getFilms() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFilms(
                    getUserAge()
            );
        }
    }

    /**
     * Gets the series that this user can watch.
     *
     * @return A list of series that the logged user can watch.
     */
    public List<Serie> getSeries() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSeries(getUserAge());
        }
    }

    /**
     * Gets the list of sections.
     *
     * @return The list of sections.
     */
    public List<Section> getSections() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSections();
        }
    }

    /**
     * Gets detailed films.
     *
     * @return The list of all detailed films.
     */
    public Map<Film, Cast> getDetailedFilms() {
        return Map.copyOf(detailedFilms);
    }

    /**
     * Gets detailed series.
     *
     * @return The list of all detailed series.
     */
    public List<Serie> getDetailedSeries() {
        return List.copyOf(detailedSeries);
    }

    /**
     * Retrieves a film by its ID.
     *
     * @param id The ID of the film to retrieve.
     * @return The film corresponding to the given ID.
     * @throws NoSuchElementException if no film is found with the specified ID.
     */
    public Film getFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFilm(id)
                    .orElseThrow(() -> new NoSuchElementException("Film non trovato con id: " + id));
        }
    }

    /**
     * Retrieves a series by its ID.
     *
     * @param id The ID of the series to retrieve.
     * @return The series corresponding to the given ID.
     * @throws NoSuchElementException if no series is found with the specified ID.
     */
    public Serie getSerie(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSerie(id)
                    .orElseThrow(() -> new NoSuchElementException("Serie non trovata con id: " + id));
        }
    }

    /**
     * Visualizes a film.
     *
     * @param id The film's id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.visualizeFilm(id, this.user.getUsername());
        }
    }

    /**
     * Visualizes an episode of a series.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeEpisode(final int seriesId, final int seasonId, final int episodeId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.visualizeEpisode(seriesId, seasonId, episodeId, this.user.getUsername());
        }
    }

    /**
     * Clears the current user preferences.
     */
    public void clearPreferences() {
        try (UserOps mgr = new UserOps()) {
            mgr.clearPreferences(this.user.getUsername());
        }
    }

    /**
     * Gets the current user preferences.
     *
     * @return A list of preferences.
     */
    public List<Genre> getUserPrefs() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getUserPrefs(this.user.getUsername());
        }
    }

    /**
     * Cheks if an episode has been viewed.
     *
     * @param seriesCode   The series code.
     * @param seasonNumber The season number.
     * @return A list of viewed episodes.
     */
    public List<Episode> getViewedEpisodesOfSeries(final int seriesCode, final int seasonNumber) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getViewedEpisodes(seriesCode, seasonNumber, this.user.getUsername());
        }
    }

    /**
     * Adds every preference in the list for the user.
     *
     * @param preferencesToAdd The list of genres to add to the preferences of the user.
     */
    public void addPreferences(final List<Genre> preferencesToAdd) {
        try (UserOps mgr = new UserOps()) {
            preferencesToAdd.forEach(
                    pref -> {
                        mgr.addPreference(pref.name(), this.user.getUsername());
                    }
            );
        }
    }

    /**
     * Removes film's visualization.
     *
     * @param id The film's id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetFilm(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.forgetFilm(id, this.user.getUsername());
        }
    }

    /**
     * Removes episode's visualization.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetEpisode(final int seriesId, final int seasonId, final int episodeId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.forgetEpisode(seriesId, seasonId, episodeId, this.user.getUsername());
        }
    }

    /**
     * Checks if the film has been viewed or not.
     *
     * @param id The ID of the film.
     * @return True if the film has been viewed, false otherwise.
     */
    public boolean isFilmViewed(final int id) {
        try (UserOps mgr = new UserOps()) {
            return mgr.isFilmViewed(id, this.user.getUsername());
        }
    }

    /**
     * Checks if the episode has been viewed or not.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @return True if the episode has been viewed, false otherwise.
     */
    public boolean isEpisodeViewed(final int seriesId, final int seasonId, final int episodeId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.isEpisodeViewed(seriesId, seasonId, episodeId, this.user.getUsername());
        }
    }

    /**
     * Retrieves the reviews associated with the user.
     *
     * @return The list of reviews associated with the user.
     */
    public List<Review> getReviews() {
        try (UserOps mgr = new UserOps()) {
            return mgr.getReviews(super.getUsername());
        }
    }

    /**
     * Retrieves reviews of a particular film.
     *
     * @param filmId The id of the specific film.
     * @return A list of reviews of the given film.
     */
    public List<Review> getFilmReviews(final int filmId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFilmReviews(filmId);
        }
    }

    /**
     * Adds a film review.
     *
     * @param filmId         The id of the film to review.
     * @param title          The title of the review.
     * @param desc           The description of the review.
     * @param reviewSections The review sections.
     * @return The status of the operation (true, false).
     */
    public boolean reviewFilm(final int filmId,
                              final String title,
                              final String desc,
                              final List<ReviewSection> reviewSections) {
        try (UserOps mgr = new UserOps()) {
            final boolean op1 = mgr.reviewFilm(filmId, this.user.getUsername(), title, desc);
            final AtomicBoolean op2 = new AtomicBoolean(true);
            reviewSections.forEach(
                    reviewSection -> {
                        if (!mgr.addFilmReviewSections(reviewSection.section().getName(),
                                this.user.getUsername(),
                                reviewSection.multimediaId(),
                                reviewSection.score())) {
                            op2.set(false);
                        }
                    }
            );
            final boolean op3 = mgr.averageSectionFilmVote(filmId, this.user.getUsername());
            return op1 && op2.get() && op3;
        }
    }

    /**
     * Gets the full review of a film.
     *
     * @param filmId         The ID of the film.
     * @param authorUsername The username of the author (not own)
     * @return A full review.
     */
    public FullFilmReview getFullFilmReview(final int filmId, final String authorUsername) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFullFilmReview(filmId, authorUsername).orElse(null);
        }
    }

    /**
     * Gets full reviews of a series.
     *
     * @param seriesId       The ID of the series.
     * @param authorUsername The author of the username.
     * @return A full series review.
     */
    public FullSeriesReview getFullSeriesReview(final int seriesId, final String authorUsername) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getFullSeriesReview(seriesId, authorUsername).orElse(null);
        }
    }

    /**
     * Gets a list of reviews for a series.
     *
     * @param seriesId The ID of the series.
     * @return A list of reviews.
     */
    public List<Review> getSeriesReviews(final int seriesId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.getSeriesReviews(seriesId);
        }
    }

    /**
     * Adds a series review.
     *
     * @param seriesId       The id of the series to review.
     * @param title          The title of the review.
     * @param desc           The description of the review.
     * @param reviewSections The review sections.
     * @return The status of the operation (true, false).
     */
    public boolean reviewSeries(final int seriesId,
                                final String title,
                                final String desc,
                                final List<ReviewSection> reviewSections) {
        try (UserOps mgr = new UserOps()) {
            final boolean op1 = mgr.reviewSeries(seriesId, this.user.getUsername(), title, desc);
            final AtomicBoolean op2 = new AtomicBoolean(true);
            reviewSections.forEach(
                    reviewSection -> {
                        if (!mgr.addSeriesReviewSection(reviewSection.section().getName(),
                                this.user.getUsername(),
                                reviewSection.multimediaId(),
                                reviewSection.score())) {
                            op2.set(false);
                        }
                    }
            );
            final boolean op3 = mgr.averageSectionSeriesVote(seriesId, this.user.getUsername());
            return op1 && op2.get() && op3;
        }
    }

    /**
     * Adds a review evaluation.
     *
     * @param recUsername The username of the reviewed user.
     * @param username    The username of the reviewer.
     * @param filmRecId   The ID of the review.
     * @param positive    If the review is positive or negative.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean evaluateFilmRec(final String recUsername,
                                   final String username,
                                   final int filmRecId,
                                   final boolean positive) {
        try (UserOps mgr = new UserOps()) {
            return mgr.evaluateFilmRec(recUsername, username, filmRecId, positive);
        }
    }

    /**
     * Adds a review evaluation.
     *
     * @param recUsername The username of the reviewed user.
     * @param username    The username of the reviewer.
     * @param serieRecId  The ID of the review.
     * @param positive    If the review is positive or negative.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean evaluateSerieRec(final String recUsername,
                                    final String username,
                                    final int serieRecId,
                                    final boolean positive) {
        try (UserOps mgr = new UserOps()) {
            return mgr.evaluateSerieRec(recUsername, username, serieRecId, positive);
        }
    }

    /**
     * Checks if the review has been evaluated or not.
     *
     * @param recUsername The username of the reviewed user.
     * @param username    The username of the reviewer.
     * @param serieRecId  The ID of the review.
     * @return An Optional containing true if the review has been evaluated positively,
     *         false if it has been evaluated negatively,
     *         and an empty Optional if the evaluation does not exist.
     */
    public Optional<Boolean> findSerieRecEvaluated(final String recUsername,
                                                 final String username,
                                                 final int serieRecId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.findSerieRecEvaluated(recUsername, username, serieRecId);
        }
    }

    /**
     * Checks if the review has been evaluated or not.
     *
     * @param recUsername The username of the reviewed user.
     * @param username    The username of the reviewer.
     * @param filmRecId   The ID of the review.
     * @return An Optional containing true if the review has been evaluated positively,
     *         false if it has been evaluated negatively,
     *         and an empty Optional if the evaluation does not exist.
     */
    public Optional<Boolean> findFilmRecEvaluated(final String recUsername,
                                                final String username,
                                                final int filmRecId) {
        try (UserOps mgr = new UserOps()) {
            return mgr.findFilmRecEvaluated(recUsername, username, filmRecId);
        }
    }
}
