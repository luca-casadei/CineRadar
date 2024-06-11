package unibo.cineradar.model.context.administrator;

import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.context.SessionContextImpl;
import unibo.cineradar.model.db.operations.admin.AdminOps;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.promo.GenrePromo;
import unibo.cineradar.model.promo.Promo;
import unibo.cineradar.model.promo.SinglePromo;
import unibo.cineradar.model.promo.TemplatePromo;
import unibo.cineradar.model.ranking.CastRanking;
import unibo.cineradar.model.ranking.EvalType;
import unibo.cineradar.model.ranking.UserRanking;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * @param actorAndDirector A boolean flag indicating
     *                         if the cast member is a Director and Actor.
     * @param castMember       The cast member to add.
     */
    public void addCastMember(final boolean actorAndDirector, final CastMember castMember) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addCastMember(actorAndDirector, castMember);
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
    public void addCast(final Optional<String> name) {
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
     * Retrieves the ID of the last added film.
     *
     * @return The ID of the last film added to the system.
     */
    public int getLastFilmId() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getLastFilmId();
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
     * Retrieves the ID of the last added cast.
     *
     * @return The ID of the last cast added to the system.
     */
    public Integer getLastCastId() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getLastCastId();
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
     * Adds multiple promotional items to the administrator context with a specified discount percentage.
     *
     * @param percentage an integer representing the percentage to be applied to the promotional items
     */
    public void addMultiplePromo(final int percentage) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addMultiplePromo(percentage);
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

    /**
     * Retrieves detailed information about the cast with the specified ID.
     *
     * @param castId The unique identifier of the cast.
     * @return A list of CastMember objects representing detailed information about the cast.
     */
    public List<CastMember> getDetailedCast(final int castId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getDetailedCast(castId);
        }
    }

    /**
     * Adds a cast member to the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be added.
     * @param castCode       The code representing the cast to which the member will be added.
     */
    public void addCastMemberToCast(final int castMemberCode, final int castCode) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addCastMemberToCast(castMemberCode, castCode);
        }
    }

    /**
     * Deletes a cast member from the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be deleted.
     * @param castCode       The code representing the cast from which the member will be deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    public boolean deleteCastMemberToCast(final int castMemberCode, final int castCode) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteCastMemberToCast(castMemberCode, castCode);
        }
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
        try (AdminOps mgr = new AdminOps()) {
            mgr.assignPromoBestFiveReviewers(
                    promoCode,
                    expiration,
                    bestNumberRatings
            );
        }
    }

    /**
     * Deletes a user from the system.
     *
     * @param username The username of the user to be deleted.
     * @return True if the user deletion was successful, false otherwise.
     */
    public boolean deleteUser(final String username) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteUser(username);
        }
    }

    /**
     * Adds a promotional offer for a specific genre.
     * This method utilizes an {@link AdminOps} instance to add a genre-specific promo.
     * The {@link Promo} object represents the promotional offer, and the genre specifies where to apply it.
     *
     * @param genre       the genre to which the promo is to be added.
     * @param multipleId  the id of multiple promo
     */
    public void addGenrePromo(final String genre, final int multipleId) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addGenrePromo(genre, multipleId);
        }
    }

    /**
     * Retrieves a list of all genres.
     * This method uses an {@link AdminOps} instance to fetch all available genres.
     *
     * @return a list of {@link Genre} objects representing all available genres.
     */
    public List<Genre> getGenres() {
        try (AdminOps mgr = new AdminOps()) {
            return List.copyOf(mgr.getGenres());
        }
    }

    /**
     * Adds a single promo using the specified template and multimedia.
     *
     * @param templateCode     an integer representing the code for the promotional template
     * @param multimediaType   a string specifying the type of multimedia (e.g., "image", "video")
     * @param multimediaCode   an integer representing the code for the multimedia item
     */
    public void addSinglePromo(
            final int templateCode, final String multimediaType, final int multimediaCode) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addSinglePromo(templateCode, multimediaType, multimediaCode);
        }
    }

    /**
     * Checks if a specific season of a series is available.
     *
     * @param seriesId The id of the series to check.
     * @param seasonId The id of the season to check.
     * @return true if the season is not available, false otherwise.
     */
    public boolean isSeasonAvailable(final int seriesId, final int seasonId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isSeasonAvailable(seriesId, seasonId);
        }
    }

    /**
     * Checks if a series is available.
     *
     * @param seriesId The id of the series to check.
     * @return true if the series is not available, false otherwise.
     */
    public boolean isSeriesAvailable(final int seriesId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isSeriesAvailable(seriesId);
        }
    }

    /**
     * Checks if a cast member is available.
     *
     * @param castMemberId The id of the cast member to check.
     * @return true if the cast member is not available, false otherwise.
     */
    public boolean isCastMemberAvailable(final int castMemberId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isCastMemberAvailable(castMemberId);
        }
    }

    /**
     * Checks whether a cast session is unavailable for the provided cast ID.
     *
     * @param castId The ID of the cast session to check availability for.
     * @return true if the cast session is unavailable, false otherwise.
     */
    public boolean isCastAvailable(final int castId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isCastAvailable(castId);
        }
    }

    /**
     * Checks if a film is available.
     *
     * @param filmId The id of the film to check.
     * @return true if the film is not available, false otherwise.
     */
    public boolean isFilmAvailable(final int filmId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isFilmAvailable(filmId);
        }
    }

    /**
     * Checks if a promo is available.
     *
     * @param promoId The id of the promo to check.
     * @return true if the promo is not available, false otherwise.
     */
    public boolean isPromoAvailable(final int promoId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isPromoAvailable(promoId);
        }
    }

    /**
     * Checks if a cinema is available.
     *
     * @param cinemaId The id of the cinema to check.
     * @return true if the cinema is not available, false otherwise.
     */
    public boolean isCinemaAvailable(final int cinemaId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isCinemaAvailable(cinemaId);
        }
    }

    /**
     * Checks if a user is available.
     *
     * @param username The username of the user to check.
     * @return true if the user is not available, false otherwise.
     */
    public boolean isUserAvailable(final String username) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isUserAvailable(username);
        }
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of User objects.
     */
    public List<User> getUsers() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getUsers();
        }
    }

    /**
     * Retrieves a list of all registrars.
     *
     * @return a list of Registrar objects.
     */
    public List<Registrar> getRegistrars() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getRegistrars();
        }
    }

    /**
     * Deletes a registrar identified by the provided username.
     *
     * @param username the username of the registrar to be deleted.
     * @return true if the registrar was successfully deleted, false otherwise.
     */
    public boolean deleteRegistrar(final String username) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteRegistrar(username);
        }
    }

    /**
     * Adds a new registrar with the provided password and details.
     *
     * @param password the password for the new registrar.
     * @param registrar the Registrar object containing the new registrar's details.
     */
    public void addRegistrar(final String password, final Registrar registrar) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addRegistrar(password, registrar);
        }
    }

    /**
     * Adds a new cinema with the provided details.
     *
     * @param cinema the Cinema object containing the new cinema's details.
     */
    public void addCinema(final Cinema cinema) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addCinema(cinema);
        }
    }

    /**
     * Deletes a cinema identified by the provided code.
     *
     * @param code the code of the cinema to be deleted.
     * @return true if the cinema was successfully deleted, false otherwise.
     */
    public boolean deleteCinema(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteCinema(code);
        }
    }

    /**
     * Retrieves a list of all cinemas.
     *
     * @return a list of Cinema objects.
     */
    public List<Cinema> getCinemas() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getCinemas();
        }
    }

    /**
     * Checks if a cast with the specified ID exists.
     *
     * @param castId The ID of the cast to be checked.
     * @return {@code true} if a cast with the specified ID exists, {@code false} otherwise.
     */
    public boolean isCast(final int castId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isCast(castId);
        }
    }

    /**
     * Checks if the cast with the specified ID is empty.
     *
     * @param castId the ID of the cast to check.
     * @return {@code true} if the cast is empty, {@code false} otherwise.
     */
    public boolean isEmptyCast(final int castId) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isEmptyCast(castId);
        }
    }

    /**
     * Checks if the film genres with the specified ID is empty.
     *
     * @param filmCode the ID of the film to check.
     * @return {@code true} if the film is genre empty, {@code false} otherwise.
     */
    public boolean isEmptyGenreFilm(final int filmCode) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isEmptyGenreFilm(filmCode);
        }
    }

    /**
     * Checks if the series genres with the specified ID is empty.
     *
     * @param seriesCode the ID of the series to check.
     * @return {@code true} if the series is genre empty, {@code false} otherwise.
     */
    public boolean isEmptyGenreSeries(final int seriesCode) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isEmptyGenreSeries(seriesCode);
        }
    }

    /**
     * Checks if the series with the specified ID is empty.
     *
     * @param seriesCode the ID of the series to check.
     * @return {@code true} if the series is empty, {@code false} otherwise.
     */
    public boolean isEmptySeries(final int seriesCode) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isEmptySeries(seriesCode);
        }
    }

    /**
     * Checks if the season of a series with the specified ID is empty.
     *
     * @param seriesCode    the ID of the series to check.
     * @param seasonNumber  the ID of the season to check.
     * @return {@code true} if the series is empty, {@code false} otherwise.
     */
    public boolean isEmptySeason(final int seriesCode, final int seasonNumber) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isEmptySeason(seriesCode, seasonNumber);
        }
    }

    /**
     * Deletes the multimedia content associated with the specified cast ID.
     *
     * @param castId the ID of the cast whose multimedia content is to be deleted.
     */
    public void deleteMultimediaCast(final int castId) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.deleteMultimediaCast(castId);
        }
    }

    /**
     * Retrieves a list of cast member codes that are linked to a specified cast member.
     * This method uses the {@code AdminOps} class to fetch the linked cast members,
     * ensuring the resources are properly managed with a try-with-resources statement.
     *
     * @param castMemberCode the unique code of the cast member whose linked cast members are to be retrieved
     * @return a list of integers representing the codes of the cast members linked to the specified cast member
     */
    public List<Integer> getCastLinked(final int castMemberCode) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getCastLinked(castMemberCode);
        }
    }

    /**
     * Retrieves a list of multiples using the AdminOps manager.
     *
     * @return a list of integers representing the multiples.
     */
    public List<Integer> getMultiples() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getMultiples();
        }
    }

    /**
     * Adds a template promotion with the specified percentage using the AdminOps manager.
     *
     * @param percentage the percentage of the template promotion to add.
     */
    public void addTemplatePromo(final int percentage) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addTemplatePromo(percentage);
        }
    }

    /**
     * Retrieves a list of template promotions using the AdminOps manager.
     *
     * @return a list of TemplatePromo objects.
     */
    public List<TemplatePromo> getTemplatePromos() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getTemplatePromos();
        }
    }

    /**
     * Retrieves a list of single promotions using the AdminOps manager.
     *
     * @return a list of SinglePromo objects.
     */
    public List<SinglePromo> getSinglePromos() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getSinglePromos();
        }
    }

    /**
     * Retrieves a list of genre promotions using the AdminOps manager.
     *
     * @return a list of GenrePromo objects.
     */
    public List<GenrePromo> getGenrePromos() {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.getGenrePromos();
        }
    }

    /**
     * Adds a promotion with the specified code and expiration date using the AdminOps manager.
     *
     * @param code the code of the promotion to add.
     * @param expiration the expiration date of the promotion.
     */
    public void addPromo(final int code, final LocalDate expiration) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addPromo(code, expiration);
        }
    }

    /**
     * Checks if a template promotion with the specified code is available using the AdminOps manager.
     *
     * @param codePromo the code of the template promotion to check.
     * @return {@code true} if the template promotion is available, {@code false} otherwise.
     */
    public boolean isTemplatePromoAvailable(final int codePromo) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isTemplatePromoAvailable(codePromo);
        }
    }

    /**
     * Checks if a multiple is available for the specified genre promotion using the AdminOps manager.
     *
     * @param genrePromo the genre promotion to check.
     * @return {@code true} if the multiple is available, {@code false} otherwise.
     */
    public boolean isMultipleAvailable(final int genrePromo) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.isMultipleAvailable(genrePromo);
        }
    }

    /**
     * Deletes a template promotion with the specified code using the AdminOps manager.
     *
     * @param code the code of the template promotion to delete.
     * @return {@code true} if the template promotion was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteTemplatePromo(final int code) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteTemplatePromo(code);
        }
    }

    /**
     * Adds a new genre with the specified description to the system.
     *
     * @param genre The name of the genre to add.
     * @param description A description of the genre.
     */
    public void addGenre(final String genre, final String description) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addGenre(genre, description);
        }
    }

    /**
     * Deletes a genre from the system.
     *
     * @param genre The name of the genre to delete.
     * @return {@code true} if the genre was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteGenre(final String genre) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteGenre(genre);
        }
    }

    /**
     * Associates a genre with a film.
     *
     * @param filmId The ID of the film to which the genre will be added.
     * @param genre The name of the genre to add to the film.
     */
    public void addGenreToFilm(final int filmId, final String genre) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addGenreToFilm(filmId, genre);
        }
    }

    /**
     * Removes a genre association from a film.
     *
     * @param filmCode The ID of the film from which the genre will be removed.
     * @param genre The name of the genre to remove from the film.
     * @return {@code true} if the genre was successfully removed from the film, {@code false} otherwise.
     */
    public boolean deleteGenreToFilm(final int filmCode, final String genre) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteGenreToFilm(filmCode, genre);
        }
    }

    /**
     * Associates a genre with a series.
     *
     * @param seriesId The ID of the series to which the genre will be added.
     * @param genre The name of the genre to add to the series.
     */
    public void addGenreToSeries(final int seriesId, final String genre) {
        try (AdminOps mgr = new AdminOps()) {
            mgr.addGenreToSeries(seriesId, genre);
        }
    }

    /**
     * Removes a genre association from a series.
     *
     * @param seriesCode The ID of the series from which the genre will be removed.
     * @param genre The name of the genre to remove from the series.
     * @return {@code true} if the genre was successfully removed from the series, {@code false} otherwise.
     */
    public boolean deleteGenreToSeries(final int seriesCode, final String genre) {
        try (AdminOps mgr = new AdminOps()) {
            return mgr.deleteGenreToSeries(seriesCode, genre);
        }
    }
}
