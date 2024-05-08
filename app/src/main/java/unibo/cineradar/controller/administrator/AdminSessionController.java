package unibo.cineradar.controller.administrator;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.ranking.CastRanking;
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
     * @param duration        The total duration of the TV series in minutes.
     * @param episodesNumber  The number of episodes in the TV series.
     */
    public void addSeries(
            final String title, final int ageLimit, final String plot, final int duration, final int episodesNumber) {
        this.administratorContext.addSerie(new Serie(0, title, ageLimit, plot, duration, episodesNumber));
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
            final int isActor, final int isDirector, final LocalDate dateDebutCareer, final String artName) {
        if (isActor == 1) {
            this.administratorContext.addCastMember(new Actor(
                    0,
                    name,
                    surname,
                    birthday,
                    dateDebutCareer,
                    artName));
        } else {
            this.administratorContext.addCastMember(new Director(
                    0,
                    name,
                    surname,
                    birthday,
                    dateDebutCareer,
                    artName));
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
     * @param seriesCode    The code of the TV series.
     * @param seasonNumber  The season number.
     * @param summary       The summary of the season.
     */
    public void addSeason(
            final int seriesCode, final int seasonNumber, final String summary) {
        this.administratorContext.addSeason(new Season(
                seriesCode,
                seasonNumber,
                summary
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
    public List<CastRanking> getCastRankings(final String evaluationType) {
        return this.administratorContext.getCastRankings(evaluationType);
    }

}
