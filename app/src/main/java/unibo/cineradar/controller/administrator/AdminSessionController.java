package unibo.cineradar.controller.administrator;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.promo.Promo;
import unibo.cineradar.model.ranking.CastRanking;
import unibo.cineradar.model.ranking.EvalType;
import unibo.cineradar.model.ranking.UserRanking;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing administrator sessions.
 * This class extends SessionControllerImpl and provides additional functionality specific to administrator operations.
 */
public final class AdminSessionController extends SessionControllerImpl {

    private final AdministratorContext administratorContext;

    /**
     * Constructs an AdminSessionController with the given generic controller.
     *
     * @param ctr The existing generic controller.
     */
    public AdminSessionController(final SessionController ctr) {
        super(ctr);
        this.administratorContext = (AdministratorContext) getGenericContext();
    }

    /**
     * Retrieves a list of insertion requests.
     *
     * @return A list containing insertion requests.
     */
    public List<Request> getInsertionRequests() {
        return this.administratorContext.getInsertionsRequests();
    }

    /**
     * Retrieves a list of all films.
     *
     * @return A list of Film objects.
     */
    public List<Film> getFilms() {
        return this.administratorContext.getFilms();
    }

    /**
     * Retrieves a list of all series.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        return this.administratorContext.getSeries();
    }

    /**
     * Adds a new film with the specified details.
     *
     * @param title     The title of the film.
     * @param ageLimit  The age limit for the film.
     * @param plot      The plot summary of the film.
     * @param duration  The duration of the film in minutes.
     * @param idCast    The ID of the cast associated with the film.
     */
    public void addFilm(
            final String title, final int ageLimit, final String plot, final int duration, final int idCast) {
        this.administratorContext.addFilm(new Film(0, title, ageLimit, plot, duration, idCast));
    }

    /**
     * Adds a new TV series with the specified details.
     *
     * @param title           The title of the TV series.
     * @param ageLimit        The age limit for the TV series.
     * @param plot            The plot summary of the TV series.
     */
    public void addSeries(final String title, final int ageLimit, final String plot) {
        this.administratorContext.addSerie(new Serie(0, title, ageLimit, plot, 0, 0));
    }

    /**
     * Deletes a film with the specified code.
     *
     * @param code The code of the film to be deleted.
     * @return True if the film was successfully deleted, false otherwise.
     */
    public boolean deleteFilm(final int code) {
        return this.administratorContext.deleteFilm(code);
    }

    /**
     * Deletes a TV series with the specified code.
     *
     * @param code The code of the TV series to be deleted.
     * @return True if the TV series was successfully deleted, false otherwise.
     */
    public boolean deleteSeries(final int code) {
        return this.administratorContext.deleteSeries(code);
    }

    /**
     * Retrieves detailed information about films and their casts.
     *
     * @return A map containing films and their associated casts.
     */
    public Map<Film, Cast> getDetailedFilms() {
        return administratorContext.getDetailedFilms();
    }

    /**
     * Gets detailed series.
     *
     * @return The list of all detailed series.
     */
    public List<Serie> getDetailedSeries() {
        return administratorContext.getDetailedSeries();
    }

    /**
     * Adds a new cast member with the specified details.
     *
     * @param name            The name of the cast member.
     * @param surname         The surname of the cast member.
     * @param birthday        The birthday of the cast member.
     * @param isActor         Flag indicating whether the cast member is an actor (1 for true, 0 for false).
     * @param isDirector      Flag indicating whether the cast member is a director (1 for true, 0 for false).
     * @param dateDebutCareer The debut date of the cast member's career.
     * @param artName         The artistic name of the cast member.
     */
    public void addCastMember(
            final String name, final String surname, final LocalDate birthday,
            final boolean isActor, final boolean isDirector, final LocalDate dateDebutCareer, final String artName) {
        if (isActor && isDirector) {
            this.administratorContext.addCastMember(true, new CastMember(
                    0,
                    name,
                    surname,
                    birthday,
                    dateDebutCareer,
                    artName));
        } else {
            if (isActor) {
                this.administratorContext.addCastMember(false, new Actor(
                        0,
                        name,
                        surname,
                        birthday,
                        dateDebutCareer,
                        artName));
            } else {
                this.administratorContext.addCastMember(false, new Director(
                        0,
                        name,
                        surname,
                        birthday,
                        dateDebutCareer,
                        artName));
            }
        }
    }

    /**
     * Deletes a cast member with the specified code.
     *
     * @param code The code of the cast member to be deleted.
     * @return True if the cast member was successfully deleted, false otherwise.
     */
    public boolean deleteCastMember(final int code) {
        return this.administratorContext.deleteCastMember(code);
    }

    /**
     * Adds a new season for a TV series.
     *
     * @param seriesCode   The code of the TV series.
     * @param seasonNumber The season number.
     * @param summary      The summary of the season.
     * @param idCast       The id of Cast.
     */
    public void addSeason(
            final int seriesCode, final int seasonNumber, final String summary, final int idCast) {
        this.administratorContext.addSeason(new Season(
                seriesCode,
                seasonNumber,
                summary,
                idCast
        ));
    }

    /**
     * Deletes a season of a TV series.
     *
     * @param seriesCode    The code of the TV series.
     * @param seasonNumber  The season number to be deleted.
     * @return True if the season was successfully deleted, false otherwise.
     */
    public boolean deleteSeason(final int seriesCode, final int seasonNumber) {
        return this.administratorContext.deleteSeason(seriesCode, seasonNumber);
    }

    /**
     * Adds a new episode to a TV series.
     *
     * @param seriesCode      The code of the TV series.
     * @param seasonNumber    The season number of the episode.
     * @param episodeNumber   The episode number.
     * @param duration        The duration of the episode in minutes.
     */
    public void addEpisode(
            final int seriesCode, final int seasonNumber, final int episodeNumber, final int duration) {
        this.administratorContext.addEpisode(new Episode(
                seriesCode,
                seasonNumber,
                episodeNumber,
                duration
        ));
    }

    /**
     * Deletes an episode from a TV series.
     *
     * @param seriesCode      The code of the TV series.
     * @param seasonNumber    The season number of the episode.
     * @param episodeNumber   The episode number to be deleted.
     * @return True if the episode was successfully deleted, false otherwise.
     */
    public boolean deleteEpisode(final int seriesCode, final int seasonNumber, final int episodeNumber) {
        return this.administratorContext.deleteEpisode(seriesCode, seasonNumber, episodeNumber);
    }

    /**
     * Retrieves a list of user rankings based on the provided evaluation type.
     *
     * @param evaluationType The type of evaluation for which rankings are requested.
     * @return A list of UserRanking objects representing the rankings for the specified evaluation type.
     */
    public List<UserRanking> getRankings(final String evaluationType) {
        return this.administratorContext.getRankings(evaluationType);
    }

    /**
     * Retrieves a list of cast rankings based on the provided evaluation type.
     *
     * @param evaluationType The type of evaluation for which rankings are requested.
     * @return A list of CastRanking objects representing the rankings for the specified evaluation type.
     */
    public List<CastRanking> getCastRankings(final EvalType evaluationType) {
        return this.administratorContext.getCastRankings(evaluationType);
    }

    /**
     * Retrieves a list of cast members.
     *
     * @return A list of CastMember objects representing the cast members.
     */
    public List<CastMember> getCastMembers() {
        return this.administratorContext.getCastMembers();
    }

    /**
     * Retrieves a list of castings.
     *
     * @return A list of Casting objects representing the casting details.
     */
    public List<Casting> getCasting() {
        return this.administratorContext.getCasting();
    }

    /**
     * Adds a new cast member with the given name.
     *
     * @param name The name of the new cast member to be added.
     */
    public void addCast(final String name) {
        this.administratorContext.addCast(name);
    }

    /**
     * Deletes a cast member based on the provided ID.
     *
     * @param id The ID of the cast member to be deleted.
     * @return True if the cast member was successfully deleted, false otherwise.
     */
    public boolean deleteCast(final int id) {
        return this.administratorContext.deleteCast(id);
    }

    /**
     * Updates detailed information about films in the system.
     */
    public void updateDetailedFilms() {
        this.administratorContext.updateDetailedFilms();
    }

    /**
     * Updates detailed information about series in the system.
     */
    public void updateDetailedSeries() {
        this.administratorContext.updateDetailedSeries();
    }

    /**
     * Retrieves the ID of the last added series.
     *
     * @return The ID of the last series added to the system.
     */
    public Integer getLastSeriesId() {
        return this.administratorContext.getLastSeriesId();
    }

    /**
     * Retrieves the ID of the last added season for a given series.
     *
     * @param seriesCode The code identifying the series.
     * @return The ID of the last season added to the specified series.
     */
    public Integer getLastSeasonId(final int seriesCode) {
        return this.administratorContext.getLastSeasonId(seriesCode);
    }

    /**
     * Marks a request as complete in the system.
     *
     * @param code The code of the request to be marked as complete.
     * @return True if the request was successfully marked as complete, false otherwise.
     */
    public boolean completeRequest(final int code) {
        return this.administratorContext.completeRequest(code);
    }

    /**
     * Retrieves the list of promotional offers.
     *
     * @return a list of {@link Promo} objects representing the current promotional offers.
     */
    public List<Promo> getPromos() {
        return this.administratorContext.getPromos();
    }

    /**
     * Adds a new promotional offer.
     *
     * @param percentage the discount percentage of the promotional offer.
     * @param expiration the expiration date of the promotional offer.
     */
    public void addMultiplePromo(final int percentage, final LocalDate expiration) {
        this.administratorContext.addMultiplePromo(new Promo(
                0,
                percentage,
                expiration
        ));
    }

    /**
     * Deletes a promotional offer based on the provided code and expiration date.
     *
     * @param code the unique code of the promotional offer to be deleted.
     * @param expiration the expiration date of the promotional offer to be deleted.
     * @return {@code true} if the promotional offer was successfully deleted, {@code false} otherwise.
     */
    public boolean deletePromo(final int code, final LocalDate expiration) {
        return this.administratorContext.deletePromo(code, expiration);
    }

    /**
     * Retrieves a list of CardReg objects.
     *
     * @return A list of CardReg objects representing the cards.
     */
    public List<CardReg> getCards() {
        return this.administratorContext.getCards();
    }

    /**
     * Assigns a promotional code to a user for a specific cinema.
     *
     * @param promoCode The promotional code to assign.
     * @param expiration The expiration date of the promotional code.
     * @param cinemaCode The code representing the cinema.
     * @param username The username of the user to whom the promotional code will be assigned.
     */
    public void assignPromo(
            final int promoCode, final LocalDate expiration, final int cinemaCode, final String username) {
        this.administratorContext.assignPromo(promoCode, expiration, cinemaCode, username);
    }

    /**
     * Retrieves detailed information about the cast with the specified ID.
     *
     * @param castId The unique identifier of the cast.
     * @return A list of CastMember objects representing detailed information about the cast.
     */
    public List<CastMember> getDetailedCast(final int castId) {
        return this.administratorContext.getDetailedCast(castId);
    }

    /**
     * Adds a cast member to the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be added.
     * @param castCode       The code representing the cast to which the member will be added.
     */
    public void addCastMemberToCast(final int castMemberCode, final int castCode) {
        this.administratorContext.addCastMemberToCast(castMemberCode, castCode);
    }

    /**
     * Deletes a cast member from the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be deleted.
     * @param castCode       The code representing the cast from which the member will be deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    public boolean deleteCastMemberToCast(final int castMemberCode, final int castCode) {
        return this.administratorContext.deleteCastMemberToCast(castMemberCode, castCode);
    }

    /**
     * Assigns the best five reviewers to a promotional event.
     *
     * @param promoCode         The unique identifier of the promotional event.
     * @param expiration        The expiration date of the promotional event.
     * @param bestNumberRatings A list of UserRanking objects representing the best reviewers.
     */
    public void assignPromoBestFiveReviewers(
            final int promoCode, final LocalDate expiration, final List<UserRanking> bestNumberRatings) {
        this.administratorContext.assignPromoBestFiveReviewers(
                promoCode,
                expiration,
                bestNumberRatings
        );
    }

    /**
     * Deletes a user from the system.
     *
     * @param username The username of the user to be deleted.
     * @return True if the user deletion was successful, false otherwise.
     */
    public boolean deleteUser(final String username) {
        return this.administratorContext.deleteUser(username);
    }

    /**
     * Adds a promotional offer for a specific genre.
     * This method creates a new {@link Promo} object with the specified percentage discount and expiration date,
     * and adds it to the specified genre.
     *
     * @param percentage the discount percentage of the promo.
     * @param expiration the expiration date of the promo.
     * @param genre the genre to which the promo is to be added.
     */
    public void addGenrePromo(final int percentage, final LocalDate expiration, final String genre) {
        this.administratorContext.addGenrePromo(
                new Promo(0, percentage, expiration),
                genre
        );
    }

    /**
     * Retrieves a list of all genres.
     *
     * @return a list of {@link Genre} objects representing all available genres.
     */
    public List<Genre> getGenres() {
        return this.administratorContext.getGenres();
    }

    /**
     * Adds a promotional offer for a specific multimedia type.
     * This method creates a new {@link Promo} object with the specified percentage discount and expiration date,
     * and adds it to the specified multimedia type (either "Serie" or "Film") and multimedia code.
     *
     * @param percentage the discount percentage of the promo.
     * @param expiration the expiration date of the promo.
     * @param multimediaType the type of multimedia (either "Serie" or "Film").
     * @param multimediaCode the code of the multimedia item to which the promo is to be added.
     */
    public void addSinglePromo(
            final int percentage, final LocalDate expiration, final String multimediaType, final int multimediaCode) {
        this.administratorContext.addSinglePromo(
                new Promo(0, percentage, expiration),
                multimediaType,
                multimediaCode);
    }


    /**
     * Checks if a specific season of a series is available.
     *
     * @param seriesId The id of the series to check.
     * @param seasonId The id of the season to check.
     * @return true if the season is not available, false otherwise.
     */
    public boolean isSeasonAvailable(final int seriesId, final int seasonId) {
        return !this.administratorContext.isSeasonAvailable(seriesId, seasonId);
    }

    /**
     * Checks if a series is available.
     *
     * @param seriesId The id of the series to check.
     * @return true if the series is not available, false otherwise.
     */
    public boolean isSeriesAvailable(final int seriesId) {
        return !this.administratorContext.isSeriesAvailable(seriesId);
    }


    /**
     * Checks if a cast member is available.
     *
     * @param castMemberId The id of the cast member to check.
     * @return true if the cast member is not available, false otherwise.
     */
    public boolean isCastMemberAvailable(final int castMemberId) {
        return !this.administratorContext.isCastMemberAvailable(castMemberId);
    }

    /**
     * Checks whether a cast session is unavailable for the provided cast ID.
     *
     * @param castId The ID of the cast session to check availability for.
     * @return true if the cast session is unavailable, false otherwise.
     */
    public boolean isCastAvailable(final int castId) {
        return !this.administratorContext.isCastAvailable(castId);
    }
}
