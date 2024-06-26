package unibo.cineradar.controller.user;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.FullSeriesReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Serie;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The user session controller class.
 */
public final class UserSessionController extends SessionControllerImpl {

    private final UserContext userContext;

    /**
     * Creates the session controller of the user.
     *
     * @param ctr The existing generic controller.
     */
    public UserSessionController(final SessionController ctr) {
        super(ctr);
        this.userContext = (UserContext) getGenericContext();
    }

    /**
     * Gets the list of films that the user can view.
     *
     * @param age The limited age to be respected.
     * @return A list of Films.
     */
    public List<Film> getFilms(final int age) {
        return userContext.getFilms(age);
    }

    /**
     * Gets the list of series that the user can view.
     *
     * @param age The limited age to be respected.
     * @return A list of series.
     */
    public List<Serie> getSeries(final int age) {
        return userContext.getSeries(age);
    }

    /**
     * Gets the list of sections.
     *
     * @return The list of sections.
     */
    public List<Section> getSections() {
        return userContext.getSections();
    }

    /**
     * Gets detailed films.
     *
     * @return The list of all detailed films.
     */
    public Map<Film, Cast> getDetailedFilms() {
        return userContext.getDetailedFilms();
    }

    /**
     * Gets detailed series.
     *
     * @return The list of all detailed series.
     */
    public List<Serie> getDetailedSeries() {
        return userContext.getDetailedSeries();
    }

    /**
     * Get the film with a particular id.
     *
     * @param id The id of the film to retrieve.
     * @return The film to a specific id.
     */
    public Film getFilm(final int id) {
        return userContext.getFilm(id);
    }

    /**
     * Get the serie with a particular id.
     *
     * @param id The id of the serie to retrieve.
     * @return The serie to a specific id.
     */
    public Serie getSerie(final int id) {
        return userContext.getSerie(id);
    }

    /**
     * Visualizes a film.
     *
     * @param filmId The film to be visualized.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeFilm(final int filmId) {
        return this.userContext.visualizeFilm(filmId);
    }

    /**
     * Visualizes an episode of a particular season of a series.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeEpisode(final int seriesId, final int seasonId, final int episodeId) {
        return this.userContext.visualizeEpisode(seriesId, seasonId, episodeId);
    }


    /**
     * Removes all the preferences from the current user.
     */
    public void clearPreferences() {
        this.userContext.clearPreferences();
    }

    /**
     * Sets the preferences of the current user.
     *
     * @param preferences The preferences to set.
     */
    public void addPreference(final List<Genre> preferences) {
        this.userContext.addPreferences(preferences);
    }

    /**
     * Adds a content request to the database.
     *
     * @param type        The type of the content request (0 for movie, 1 for TV series).
     * @param title       The title of the content request.
     * @param releaseYear The release year of the content request.
     * @param description The description of the content request.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean addRequest(final boolean type, final String title, final LocalDate releaseYear, final String description) {
        return this.userContext.addRequest(type, title, releaseYear, description);
    }

    /**
     * Un-visualizes a film.
     *
     * @param filmId The film to be un-visualized.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetFilm(final int filmId) {
        return this.userContext.forgetFilm(filmId);
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
        return this.userContext.forgetEpisode(seriesId, seasonId, episodeId);
    }

    /**
     * Gets the full review of a film.
     *
     * @param filmId         The ID of the film.
     * @param authorUsername The username of the author (not own)
     * @return A full review.
     */
    public FullFilmReview getFullFilmReview(final int filmId, final String authorUsername) {
        return this.userContext.getFullFilmReview(filmId, authorUsername);
    }

    /**
     * Gets a list of reviews for a series.
     *
     * @param seriesId The ID of the series.
     * @return A list of reviews.
     */
    public List<Review> getSeriesReviews(final int seriesId) {
        return this.userContext.getSeriesReviews(seriesId);
    }

    /**
     * Gets the full review of a film.
     *
     * @param seriesId       The ID of the film.
     * @param usernameUtente The username of the author (not own)
     * @return A full review.
     */
    public FullSeriesReview getFullSeriesReview(final int seriesId, final String usernameUtente) {
        return this.userContext.getFullSeriesReview(seriesId, usernameUtente);
    }

    /**
     * Checks if the film has been viewed or not.
     *
     * @param filmId The ID of the film.
     * @return True if the film has been viewed, false otherwise.
     */
    public boolean isFilmViewed(final int filmId) {
        return this.userContext.isFilmViewed(filmId);
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
        return this.userContext.isEpisodeViewed(seriesId, seasonId, episodeId);
    }

    /**
     * Cheks if an episode has been viewed.
     *
     * @param seriesCode   The series code.
     * @param seasonNumber The season number.
     * @return A list of viewed episodes.
     */
    public List<Episode> getViewedEpisodes(final int seriesCode, final int seasonNumber) {
        return userContext.getViewedEpisodesOfSeries(seriesCode, seasonNumber);
    }

    /**
     * Gets the list of current preferences.
     *
     * @return A list of genres.
     */
    public List<Genre> getUserPrefs() {
        return userContext.getUserPrefs();
    }

    /**
     * Gets the list of reviews that the user has written.
     *
     * @return A list of Reviews.
     */
    public List<Review> getReviews() {
        return userContext.getReviews();
    }

    /**
     * Retrieves reviews of a particular film.
     *
     * @param filmId The id of the specific film.
     * @return A list of reviews of the given film.
     */
    public List<Review> getFilmReviews(final int filmId) {
        return userContext.getFilmReviews(filmId);
    }

    /**
     * Adds a film review.
     *
     * @param filmId   The id of the film to review.
     * @param title    The title of the review.
     * @param desc     The description of the review.
     * @param sections The review sections.
     * @return The status of the operation (true, false).
     */
    public boolean reviewFilm(final int filmId,
                              final String title,
                              final String desc,
                              final List<ReviewSection> sections) {
        return this.userContext.reviewFilm(filmId, title, desc, sections);
    }

    /**
     * Adds a series review.
     *
     * @param seriesId The id of the series to review.
     * @param title    The title of the review.
     * @param desc     The description of the review.
     * @param sections The review sections.
     * @return The status of the operation (true, false).
     */
    public boolean reviewSeries(final int seriesId,
                                final String title,
                                final String desc,
                                final List<ReviewSection> sections) {
        return this.userContext.reviewSeries(seriesId, title, desc, sections);
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
        return this.userContext.evaluateFilmRec(recUsername, username, filmRecId, positive);
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
        return this.userContext.evaluateSerieRec(recUsername, username, serieRecId, positive);
    }

    /**
     * Checks if the review has been evaluated or not.
     *
     * @param recUsername The username of the reviewed user.
     * @param username    The username of the reviewer.
     * @param serieRecId  The ID of the review.
     * @return An Optional containing true if the review has been evaluated positively,
     * false if it has been evaluated negatively,
     * and an empty Optional if the evaluation does not exist.
     */
    public Optional<Boolean> findSerieRecEvaluated(final String recUsername,
                                                   final String username,
                                                   final int serieRecId) {
        return this.userContext.findSerieRecEvaluated(recUsername, username, serieRecId);
    }

    /**
     * Checks if the review has been evaluated or not.
     *
     * @param recUsername The username of the reviewed user.
     * @param username    The username of the reviewer.
     * @param filmRecId   The ID of the review.
     * @return An Optional containing true if the review has been evaluated positively,
     * false if it has been evaluated negatively,
     * and an empty Optional if the evaluation does not exist.
     */
    public Optional<Boolean> findFilmRecEvaluated(final String recUsername,
                                                  final String username,
                                                  final int filmRecId) {
        return this.userContext.findFilmRecEvaluated(recUsername, username, filmRecId);
    }

    /**
     * Removes a review evaluation.
     *
     * @param usernameOwnerReview The username of the reviewed user.
     * @param username            The username of the reviewer.
     * @param idFilm              The ID of the review.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean removeFilmRecEvaluation(final String usernameOwnerReview, final String username, final int idFilm) {
        return this.userContext.removeFilmRecEvaluation(usernameOwnerReview, username, idFilm);
    }

    /**
     * Removes a review evaluation.
     *
     * @param usernameOwnerReview The username of the reviewed user.
     * @param username            The username of the reviewer.
     * @param idSerie             The ID of the review.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean removeSerieRecEvaluation(final String usernameOwnerReview, final String username, final int idSerie) {
        return this.userContext.removeSerieRecEvaluation(usernameOwnerReview, username, idSerie);
    }

    /**
     * Retrieves a list of film genres sorted by the number of views,
     * including all available information about the genres.
     *
     * @return A list of Genre objects containing film genres sorted by the number of views,
     * including all available genre details.
     */
    public List<Genre> getFilmGenresRanking() {
        return this.userContext.getFilmGenresRanking();
    }

    /**
     * Retrieves a list of series genres sorted by the number of views,
     * including all available information about the genres.
     *
     * @return A list of Genre objects containing series genres sorted by the number of views,
     * including all available genre details.
     */
    public List<Genre> getSeriesGenresRanking() {
        return this.userContext.getSeriesGenresRanking();
    }
}
