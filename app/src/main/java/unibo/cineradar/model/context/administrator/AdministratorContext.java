package unibo.cineradar.model.context.administrator;

import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.context.SessionContextImpl;
import unibo.cineradar.model.db.AdminOps;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.promo.Promo;
import unibo.cineradar.model.ranking.CastRanking;
import unibo.cineradar.model.ranking.EvalType;
import unibo.cineradar.model.ranking.UserRanking;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The context of an administrator session.
 * This class manages the context specific to an administrator,
 * including operations related to films, TV series, and insertion requests.
 */
public final class AdministratorContext extends SessionContextImpl {
    private Map<Film, Cast> detailedFilms;
    private List<Serie> detailedSeries;

    /**
     * Constructs the context of an administrator.
     *
     * @param loggedAccount The currently logged account.
     */
    public AdministratorContext(final Account loggedAccount) {
        super(loggedAccount);
        try (AdminOps mgr = new AdminOps()) {
            this.detailedFilms = mgr.getFilmsDetails();
            this.detailedSeries = mgr.getDetailedSeries();
        }
    }

    /**
     * Deletes a film review.
     *
     * @param filmId         The ID of the reviewed film.
     * @param authorUsername The username of the author.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean delFilmReview(final int filmId, final String authorUsername) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.delFilmReview(filmId, authorUsername);
        }
    }

    /**
     * Deletes a series review.
     *
     * @param seriesId       The ID of the reviewed series.
     * @param authorUsername The username of the author.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean delSeriesReview(final int seriesId, final String authorUsername) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.delSeriesReview(seriesId, authorUsername);
        }
    }

    /**
     * Gets the list of insertion requests.
     *
     * @return A list containing insertion requests.
     */
    public List<Request> getInsertionsRequests() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getRequests();
        }
    }

    /**
     * Gets the list of all series.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getSeries();
        }
    }

    /**
     * Gets the list of all films.
     *
     * @return A list of Films.
     */
    public List<Film> getFilms() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getFilms();
        }
    }

    /**
     * Adds a new film to the database.
     *
     * @param film The film to be added.
     */
    public void addFilm(final Film film) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addFilm(film);
        }
    }

    /**
     * Adds a new TV series to the database.
     *
     * @param serie The TV series to be added.
     */
    public void addSerie(final Serie serie) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addSeries(serie);
        }
    }

    /**
     * Deletes a film from the database with the specified code.
     *
     * @param code The code of the film to be deleted.
     * @return True if the film was successfully deleted, false otherwise.
     */
    public boolean deleteFilm(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteFilm(code);
        }
    }

    /**
     * Deletes a TV series from the database with the specified code.
     *
     * @param code The code of the TV series to be deleted.
     * @return True if the TV series was successfully deleted, false otherwise.
     */
    public boolean deleteSeries(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteSeries(code);
        }
    }

    /**
     * Retrieves detailed information about films and their casts.
     *
     * @return An immutable map containing films and their associated casts.
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
     * Adds a new cast member.
     *
     * @param castMember The cast member to add.
     */
    public void addCastMember(final CastMember castMember) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addCastMember(castMember);
        }
    }

    /**
     * Deletes a cast member with the specified code.
     *
     * @param code The code of the cast member to be deleted.
     * @return True if the cast member was successfully deleted, false otherwise.
     */
    public boolean deleteCastMember(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteCastMember(code);
        }
    }

    /**
     * Adds a new season.
     *
     * @param season The season to add.
     */
    public void addSeason(final Season season) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addSeason(season);
        }
    }

    /**
     * Deletes a season of a TV series.
     *
     * @param seriesCode   The code of the TV series.
     * @param seasonNumber The season number to be deleted.
     * @return True if the season was successfully deleted, false otherwise.
     */
    public boolean deleteSeason(final int seriesCode, final int seasonNumber) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteSeason(seriesCode, seasonNumber);
        }
    }

    /**
     * Adds a new episode.
     *
     * @param episode The episode to add.
     */
    public void addEpisode(final Episode episode) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addEpisode(episode);
        }
    }

    /**
     * Deletes an episode from a TV series.
     *
     * @param seriesCode    The code of the TV series.
     * @param seasonNumber  The season number of the episode.
     * @param episodeNumber The episode number to be deleted.
     * @return True if the episode was successfully deleted, false otherwise.
     */
    public boolean deleteEpisode(final int seriesCode, final int seasonNumber, final int episodeNumber) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteEpisode(seriesCode, seasonNumber, episodeNumber);
        }
    }

    /**
     * Retrieves a list of user rankings based on the provided evaluation type.
     * The method utilizes an AdminOps instance within a try-with-resources block for resource management.
     *
     * @param evaluationType The type of evaluation for which rankings are requested.
     * @return A list of UserRanking objects representing the rankings for the specified evaluation type.
     */
    public List<UserRanking> getRankings(final String evaluationType) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getRankings(evaluationType);
        }
    }

    /**
     * Retrieves a list of cast rankings based on the provided evaluation type.
     * The method utilizes an AdminOps instance within a try-with-resources block for resource management.
     *
     * @param evaluationType The type of evaluation for which rankings are requested.
     * @return A list of CastRanking objects representing the rankings for the specified evaluation type.
     */
    public List<CastRanking> getCastRankings(final EvalType evaluationType) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getCastRankings(evaluationType);
        }
    }

    /**
     * Retrieves a list of cast members.
     * <p>
     * This method retrieves a list of cast members using the AdminOps class, which manages administrative operations
     * related to cast members. It automatically closes the AdminOps resource after execution using try-with-resources.
     *
     * @return A list of CastMember objects representing the cast members.
     * @throws RuntimeException If an error occurs while retrieving the cast members.
     */
    public List<CastMember> getCastMembers() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getCastMembers();
        }
    }

    /**
     * Retrieves a list of casting details.
     * <p>
     * This method retrieves a list of casting details using the AdminOps class, which manages administrative operations
     * related to casting details. It automatically closes the AdminOps resource after execution using try-with-resources.
     *
     * @return A list of Casting objects representing the casting details.
     * @throws RuntimeException If an error occurs while retrieving the casting details.
     */
    public List<Casting> getCasting() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getCasting();
        }
    }

    /**
     * Adds a new casting detail with the given name.
     * <p>
     * This method adds a new casting detail with the specified name using the AdminOps class, which manages
     * administrative operations related to casting details. It automatically closes the AdminOps resource
     * after execution using try-with-resources.
     *
     * @param name The name of the new casting detail to be added.
     * @throws RuntimeException If an error occurs while adding the casting detail.
     */
    public void addCast(final String name) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addCast(name);
        }
    }

    /**
     * Deletes a casting detail based on the provided ID.
     * <p>
     * This method deletes a casting detail with the specified ID using the AdminOps class, which manages
     * administrative operations related to casting details. It automatically closes the AdminOps resource
     * after execution using try-with-resources.
     *
     * @param id The ID of the casting detail to be deleted.
     * @return True if the casting detail was successfully deleted, false otherwise.
     * @throws RuntimeException If an error occurs while deleting the casting detail.
     */
    public boolean deleteCast(final int id) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteCast(id);
        }
    }

    /**
     * Updates detailed information about films in the system.
     */
    public void updateDetailedFilms() {
        try (AdminOps mgr = new AdminOps()) {
            this.detailedFilms = mgr.getFilmsDetails();
        }
    }

    /**
     * Updates detailed information about series in the system.
     */
    public void updateDetailedSeries() {
        try (AdminOps mgr = new AdminOps()) {
            this.detailedSeries = mgr.getDetailedSeries();
        }
    }

    /**
     * Retrieves the ID of the last added series.
     *
     * @return The ID of the last series added to the system.
     */
    public Integer getLastSeriesId() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getLastSeriesId();
        }
    }

    /**
     * Retrieves the ID of the last added season for a given series.
     *
     * @param seriesCode The code identifying the series.
     * @return The ID of the last season added to the specified series.
     */
    public Integer getLastSeasonId(final int seriesCode) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getLastSeasonId(seriesCode);
        }
    }

    /**
     * Marks a request as complete in the system.
     *
     * @param code The code of the request to be marked as complete.
     * @return True if the request was successfully marked as complete, false otherwise.
     */
    public boolean completeRequest(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.completeRequest(code);
        }
    }

    /**
     * Retrieves the list of promotional offers.
     * This method uses the {@link AdminOps} class to manage promotional operations.
     *
     * @return a list of {@link Promo} objects representing the current promotional offers.
     * @throws RuntimeException if an error occurs while retrieving the promotional offers.
     */
    public List<Promo> getPromos() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getPromos();
        }
    }

    /**
     * Adds a new promotional offer.
     * This method uses the {@link AdminOps} class to manage promotional operations.
     *
     * @param promo the {@link Promo} object representing the promotional offer to be added.
     * @throws RuntimeException if an error occurs while adding the promotional offer.
     */
    public void addPromo(final Promo promo) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addPromo(promo);
        }
    }

    /**
     * Deletes a promotional offer based on the provided code and expiration date.
     * This method uses the {@link AdminOps} class to manage promotional operations.
     *
     * @param code the unique code of the promotional offer to be deleted.
     * @param expiration the expiration date of the promotional offer to be deleted.
     * @return {@code true} if the promotional offer was successfully deleted, {@code false} otherwise.
     * @throws RuntimeException if an error occurs while deleting the promotional offer.
     */
    public boolean deletePromo(final int code, final LocalDate expiration) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deletePromo(code, expiration);
        }
    }

    /**
     * Retrieves a list of CardReg objects.
     *
     * @return A list of CardReg objects representing the cards.
     */
    public List<CardReg> getCards() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getCards();
        }
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
        try (AdminOps mgr = new AdminOps()) {
            mgr.assignPromo(promoCode, expiration, cinemaCode, username);
        }
    }
}
