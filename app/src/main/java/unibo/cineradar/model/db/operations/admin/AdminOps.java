package unibo.cineradar.model.db.operations.admin;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.film.Film;
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
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Database operations that the Admin can perform.
 */
@SuppressFBWarnings(
        value = {
                "OBL_UNSATISFIED_OBLIGATION"
        },
        justification = "The parent class satisfies the obligation."
)
public final class AdminOps extends DBManager {

    private static final int PARAMETER_INDEX = 5;
    private static final int DEBUT_DATE = 6;
    private static final int STAGE_NAME = 7;

    /**
     * Gets the details of an administrator given its username.
     *
     * @param username The username of the administrator to fetch.
     * @return A list of details of the retrieved account.
     */
    public Optional<Administrator> getAdministrationDetails(final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT amministratore.Username, Nome, Cognome, NumeroTelefono "
                    + "FROM amministratore JOIN account "
                    + "ON amministratore.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                return Optional.of(new Administrator(
                        this.getResultSet().getString("Username"),
                        this.getResultSet().getString("Nome"),
                        this.getResultSet().getString("Cognome"),
                        this.getResultSet().getString("NumeroTelefono")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of all requests.
     *
     * @return The list of all requests.
     */
    public List<Request> getRequests() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getRequests();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of all films.
     *
     * @return The list of all films.
     */
    public List<Film> getFilms() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getFilms();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of all the series.
     *
     * @return The list of all the series.
     */
    public List<Serie> getSeries() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getSeries();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a series from the database based on its unique identifier (Codice).
     *
     * @param code The unique identifier of the series to delete.
     * @return true if the series was successfully deleted, false otherwise.
     */
    public boolean deleteSeries(final int code) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM serie WHERE Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, code);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting series: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a film from the database based on its unique identifier (Codice).
     *
     * @param code The unique identifier of the film to delete.
     * @return true if the film was successfully deleted, false otherwise.
     */
    public boolean deleteFilm(final int code) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM film WHERE Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, code);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting film: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new film to the database.
     *
     * @param film The film object to add to the database.
     */
    public void addFilm(final Film film) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT "
                    + "INTO film (Titolo, EtaLimite, Trama, Durata, CodiceCast) "
                    + "VALUES (?, ?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, film.getTitle());
            getPreparedStatement().setInt(2, film.getAgeLimit());
            getPreparedStatement().setString(3, film.getPlot());
            getPreparedStatement().setInt(4, film.getDuration());
            getPreparedStatement().setInt(PARAMETER_INDEX, film.getCastId());
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding film: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new series to the database.
     *
     * @param serie The series object to add to the database.
     */
    public void addSeries(final Serie serie) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT "
                    + "INTO serie (Titolo, EtaLimite, Trama, DurataComplessiva, NumeroEpisodi) "
                    + "VALUES (?, ?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, serie.getTitle());
            getPreparedStatement().setInt(2, serie.getAgeLimit());
            getPreparedStatement().setString(3, serie.getPlot());
            getPreparedStatement().setInt(4, serie.getDuration());
            getPreparedStatement().setInt(PARAMETER_INDEX, serie.getNumberOfEpisodes());
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding series: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves details of films including their cast from the database.
     *
     * @return A map containing films as keys and their corresponding cast as values.
     */
    public Map<Film, Cast> getFilmsDetails() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getFilmsDetails();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves detailed information about series, including seasons, episodes, and cast members.
     *
     * @return A list of Series objects containing detailed information about series, seasons, episodes, and cast members.
     */
    public List<Serie> getDetailedSeries() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getDetailedSeries();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new cast member to the database.
     *
     * @param actorAndDirector A boolean flag indicating
     *                         if the cast member is a Director and Actor.
     * @param castMember The cast member object to add to the database.
     */
    public void addCastMember(final boolean actorAndDirector, final CastMember castMember) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT"
                    + " INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, castMember.getName());
            getPreparedStatement().setString(2, castMember.getLastName());
            getPreparedStatement().setDate(3, Date.valueOf(castMember.getBirthDate()));
            if (actorAndDirector) {
                getPreparedStatement().setBoolean(4, true);
                getPreparedStatement().setBoolean(PARAMETER_INDEX, true);
            } else {
                getPreparedStatement().setBoolean(4, castMember instanceof Actor);
                getPreparedStatement().setBoolean(PARAMETER_INDEX, castMember instanceof Director);
            }
            if (!Objects.isNull(castMember.getCareerDebutDate())) {
                getPreparedStatement().setDate(DEBUT_DATE, Date.valueOf(castMember.getCareerDebutDate()));
            } else {
                getPreparedStatement().setNull(DEBUT_DATE, java.sql.Types.DATE);
            }
            if (!Objects.isNull(castMember.getStageName())) {
                getPreparedStatement().setString(STAGE_NAME, castMember.getStageName());
            } else {
                getPreparedStatement().setNull(STAGE_NAME, java.sql.Types.VARCHAR);
            }
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding cast member: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a cast member from the database based on its unique identifier (Codice).
     *
     * @param code The unique identifier of the cast member to delete.
     * @return true if the cast member was successfully deleted, false otherwise.
     */
    public boolean deleteCastMember(final int code) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM membrocast WHERE Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, code);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting cast member: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new season to the database.
     *
     * @param season The season object to add to the database.
     */
    public void addSeason(final Season season) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO "
                    + "stagione (CodiceSerie, NumeroStagione, Sunto, CodiceCast) VALUES (?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, season.getSeriesId());
            getPreparedStatement().setInt(2, season.getId());
            getPreparedStatement().setString(3, season.getSummary());
            getPreparedStatement().setInt(4, season.getIdCast());
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding season: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a season from the database based on the series code and season number.
     *
     * @param seriesCode   The unique identifier of the series to delete the season from.
     * @param seasonNumber The season number to delete.
     * @return true if the season was successfully deleted, false otherwise.
     */
    public boolean deleteSeason(final int seriesCode, final int seasonNumber) {
        Objects.requireNonNull(getConnection());
        try {
            final String retrieveEpisodesInfoQuery = "SELECT DurataMin FROM episodio "
                    + "WHERE CodiceSerie = ? AND NumeroStagione = ?";
            final String seasonQuery = "DELETE FROM stagione WHERE CodiceSerie = ? AND NumeroStagione = ?";
            setPreparedStatement(getConnection().prepareStatement(retrieveEpisodesInfoQuery));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            setResultSet(getPreparedStatement().executeQuery());
            while (getResultSet().next()) {
                final int duration = getResultSet().getInt("DurataMin");
                updateSeries(seriesCode, duration, false);
            }
            setPreparedStatement(getConnection().prepareStatement(seasonQuery));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting season: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new episode to the database.
     *
     * @param episode The episode object to add to the database.
     */
    public void addEpisode(final Episode episode) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT"
                    + " INTO episodio (NumeroEpisodio, CodiceSerie, NumeroStagione, DurataMin) "
                    + "VALUES (?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, episode.id());
            getPreparedStatement().setInt(2, episode.seriesId());
            getPreparedStatement().setInt(3, episode.seasonId());
            getPreparedStatement().setInt(4, episode.duration());
            getPreparedStatement().executeUpdate();
            updateSeries(episode.seriesId(), episode.duration(), true);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding episode: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes an episode from the database based on the series code, season number, and episode number.
     *
     * @param seriesCode    The unique identifier of the series.
     * @param seasonNumber  The season number.
     * @param episodeNumber The episode number.
     * @return true if the episode was successfully deleted, false otherwise.
     */
    public boolean deleteEpisode(final int seriesCode, final int seasonNumber, final int episodeNumber) {
        Objects.requireNonNull(getConnection());
        try {
            final String retrieveEpisodeInfoQuery = "SELECT DurataMin FROM episodio "
                    + "WHERE CodiceSerie = ? AND NumeroStagione = ? AND NumeroEpisodio = ?";
            setPreparedStatement(getConnection().prepareStatement(retrieveEpisodeInfoQuery));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            getPreparedStatement().setInt(3, episodeNumber);
            setResultSet(getPreparedStatement().executeQuery());
            if (getResultSet().next()) {
                final int duration = getResultSet().getInt("DurataMin");
                updateSeries(seriesCode, duration, false);
            } else {
                throw new SQLException("Episode not found");
            }
            final String episodeQuery = "DELETE FROM episodio "
                    + "WHERE CodiceSerie = ? AND NumeroStagione = ? AND NumeroEpisodio = ?";
            setPreparedStatement(getConnection().prepareStatement(episodeQuery));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            getPreparedStatement().setInt(3, episodeNumber);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting episode: " + ex.getMessage(), ex);
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
        Objects.requireNonNull(getConnection());
        try {
            final String secQuery = "DELETE FROM sezionamento_film WHERE CodiceRecFilm = ? AND UsernameUtente = ?";
            setPreparedStatement(getConnection().prepareStatement(secQuery));
            getPreparedStatement().setInt(1, filmId);
            getPreparedStatement().setString(2, authorUsername);
            getPreparedStatement().executeUpdate();
            final String query = "DELETE FROM recfilm WHERE recfilm.CodiceFilm = ? AND recfilm.UsernameUtente = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, filmId);
            getPreparedStatement().setString(2, authorUsername);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected >= 0;
        } catch (SQLException ex) {
            return false;
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
        Objects.requireNonNull(getConnection());
        try {
            final String secQuery = "DELETE FROM sezionamento_serie WHERE CodiceRecSerie = ? AND UsernameUtente = ?";
            setPreparedStatement(getConnection().prepareStatement(secQuery));
            getPreparedStatement().setInt(1, seriesId);
            getPreparedStatement().setString(2, authorUsername);
            getPreparedStatement().executeUpdate();
            final String query = "DELETE FROM recserie WHERE recserie.CodiceSerie = ? AND recserie.UsernameUtente = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesId);
            getPreparedStatement().setString(2, authorUsername);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected >= 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Retrieves a list of user rankings based on the provided evaluation type.
     *
     * @param evaluationType The type of evaluation for which rankings are requested.
     *                       Possible values include:
     *                       - "MigliorNumeroValutazioni": Retrieves top users based on the highest number of reviews given.
     *                       - "PeggiorMediaUtilità": Retrieves users with the lowest average usefulness rating for reviews.
     *                       - "MigliorMediaUtilità": Retrieves users with the highest average usefulness rating for reviews.
     * @return A list of UserRanking objects representing the rankings for the specified evaluation type.
     */
    public List<UserRanking> getRankings(final String evaluationType) {
        return switch (evaluationType) {
            case "MigliorNumeroValutazioni" -> getBestReviewersRanking();
            case "PeggiorMediaUtilità" -> getWorstUtilityReviewersRanking();
            case "MigliorMediaUtilità" -> getBestUtilityReviewersRanking();
            default -> throw new IllegalArgumentException("Invalid evaluation type: " + evaluationType);
        };
    }

    /**
     * Retrieves a list of cast rankings based on the provided evaluation type.
     *
     * @param evaluationType The type of evaluation for which rankings are requested.
     *                       Possible value is "BestDirectors",
     *                       which retrieves top directors based on the number of appearances in cast.
     * @return A list of CastRanking objects representing the rankings for the specified evaluation type.
     */
    public List<CastRanking> getCastRankings(final EvalType evaluationType) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = switch (evaluationType) {
                case BEST_DIRECTORS -> "SELECT Nome, Cognome, COUNT(*) AS NumeroPresenze "
                        + "FROM partecipazione_cast "
                        + "JOIN membrocast ON membrocast.Codice = partecipazione_cast.CodiceMembro "
                        + "WHERE membrocast.TipoRegista "
                        + "GROUP BY Nome, Cognome "
                        + "ORDER BY NumeroPresenze"
                        + " LIMIT 5";
                default -> throw new IllegalArgumentException("Invalid evaluation type: " + evaluationType);
            };
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<CastRanking> rankings = new ArrayList<>();
            final ResultSet resultSet = getResultSet();
            while (resultSet.next()) {
                rankings.add(new CastRanking(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)));
            }
            return rankings;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving cast rankings: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of cast members from the database.
     *
     * @return A list of CastMember objects representing the cast members.
     */
    public List<CastMember> getCastMembers() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getCastMembers();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of casting details from the database.
     *
     * @return A list of Casting objects representing the casting details.
     */
    public List<Casting> getCasting() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getCasting();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new casting detail with the given name to the database.
     *
     * @param name The name of the casting detail to be added.
     */
    public void addCast(final Optional<String> name) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT"
                    + " INTO casting (Nome)"
                    + " VALUES (?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            if (name.isPresent()) {
                getPreparedStatement().setString(1, name.get());
            } else {
                getPreparedStatement().setNull(1, java.sql.Types.VARCHAR);
            }
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding casting: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a casting detail from the database based on the provided ID.
     *
     * @param id The ID of the casting detail to be deleted.
     * @return True if the casting detail was successfully deleted, false otherwise.
     */
    public boolean deleteCast(final int id) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM casting WHERE Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, id);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting casting: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the last added series from the database.
     *
     * @return The ID of the last series added to the system.
     */
    public Integer getLastSeriesId() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getLastSeriesId();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the last added film from the database.
     *
     * @return The ID of the last film added to the system.
     */
    public int getLastFilmId() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getLastFilmId();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the last added season for a given series from the database.
     *
     * @param seriesCode The code identifying the series.
     * @return The ID of the last season added to the specified series.
     */
    public Integer getLastSeasonId(final int seriesCode) {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getLastSeasonId(seriesCode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Marks a request as complete in the database.
     *
     * @param code The code of the request to be marked as complete.
     * @return True if the request was successfully marked as complete, false otherwise.
     */
    public boolean completeRequest(final int code) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "UPDATE richiesta"
                    + " SET richiesta.Chiusa = 1"
                    + " WHERE richiesta.Numero = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, code);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error updating request status: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of promotional offers from the database.
     * This method executes an SQL query to fetch promo details by joining the 'templatepromo' and 'promo' tables.
     *
     * @return a list of {@link Promo} objects representing the current promotional offers.
     */
    public List<Promo> getPromos() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getPromos();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a multiple promotion with the specified percentage to the database.
     *
     * @param percentage the percentage of the multiple promotion to add.
     */
    public void addMultiplePromo(final int percentage) {
        Objects.requireNonNull(getConnection());
        final String multipleQuery = "INSERT INTO MULTIPLO (CodiceTemplatePromo) VALUES (?)";
        try {
            setPreparedStatement(getConnection().prepareStatement(multipleQuery));
            getPreparedStatement().setInt(1, percentage);
            getPreparedStatement().executeUpdate();
            getConnection().commit();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting multiple promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new promotional entry for a genre-specific promotion.
     * Inserts the promotional details into the PROMO_GENERE table and then into the PROMO table.
     *
     * @param genre      the genre to which the promotion applies.
     * @param multipleId the Promo id of multiple
     */
    public void addGenrePromo(final String genre, final int multipleId) {
        Objects.requireNonNull(getConnection());
        final String genreQuery = "INSERT INTO PROMO_GENERE (NomeGenere, CodiceTemplateMultiplo) VALUES (?,?)";
        try {
            setPreparedStatement(getConnection().prepareStatement(genreQuery));
            getPreparedStatement().setString(1, genre);
            getPreparedStatement().setInt(2, multipleId);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding genre promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a single promotion with the specified template code, multimedia type, and multimedia code to the database.
     *
     * @param templateCode the code of the template promotion to add.
     * @param multimediaType the type of multimedia, either "Serie" or another type (assumed to be "Film").
     * @param multimediaCode the code of the multimedia, either a series code or a film code depending on the type.
     */
    public void addSinglePromo(final int templateCode, final String multimediaType, final int multimediaCode) {
        Objects.requireNonNull(getConnection());
        final String singleQuery = "INSERT INTO SINGOLO (CodiceTemplatePromo, CodiceSerie, CodiceFilm) VALUES (?,?,?)";
        try {
            setPreparedStatement(getConnection().prepareStatement(singleQuery));
            getPreparedStatement().setInt(1, templateCode);
            if ("Serie".equals(multimediaType)) {
                getPreparedStatement().setInt(2, multimediaCode);
                getPreparedStatement().setNull(3, java.sql.Types.INTEGER);
            } else {
                getPreparedStatement().setNull(2, java.sql.Types.INTEGER);
                getPreparedStatement().setInt(3, multimediaCode);
            }
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting single promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a promotional offer from the database based on the provided code and expiration date.
     * This method executes an SQL query to delete the promo from the 'promo' table.
     *
     * @param code       the unique code of the promotional offer to be deleted.
     * @param expiration the expiration date of the promotional offer to be deleted.
     * @return {@code true} if the promotional offer was successfully deleted, {@code false} otherwise.
     */
    public boolean deletePromo(final int code, final LocalDate expiration) {
        Objects.requireNonNull(getConnection());
        try {
            final String deletePromoQuery = "DELETE FROM PROMO "
                    + "WHERE CodiceTemplatePromo = ? "
                    + "AND Scadenza = ?";
            setPreparedStatement(getConnection().prepareStatement(deletePromoQuery));
            getPreparedStatement().setInt(1, code);
            getPreparedStatement().setDate(2, Date.valueOf(expiration));
            final int rowsAffectedPromo = getPreparedStatement().executeUpdate();
            return rowsAffectedPromo > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of cards from the database.
     * This method executes an SQL query to fetch cards details.
     *
     * @return a list of {@link CardReg} objects representing the cards.
     */
    public List<CardReg> getCards() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getCards();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Assigns a promotional code to a user for a specific cinema.
     *
     * @param promoCode  The promotional code to assign.
     * @param expiration The expiration date of the promotional code.
     * @param cinemaCode The code representing the cinema.
     * @param username   The username of the user to whom the promotional code will be assigned.
     */
    public void assignPromo(
            final int promoCode, final LocalDate expiration, final int cinemaCode, final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO"
                    + " premi_tessera(CodicePromoPromo, Scadenza, CodiceCinema, UsernameUtente)"
                    + " VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE CodicePromoPromo = CodicePromoPromo";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, promoCode);
            getPreparedStatement().setDate(2, Date.valueOf(expiration));
            getPreparedStatement().setInt(3, cinemaCode);
            getPreparedStatement().setString(4, username);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding casting: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves detailed information about the cast with the specified ID.
     *
     * @param castId The unique identifier of the cast.
     * @return A list of CastMember objects representing detailed information about the cast.
     */
    public List<CastMember> getDetailedCast(final int castId) {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getDetailedCast(castId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a cast member to the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be added.
     * @param castCode       The code representing the cast to which the member will be added.
     */
    public void addCastMemberToCast(final int castMemberCode, final int castCode) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO "
                    + "partecipazione_cast(CodiceMembro, CodiceCast)"
                    + " VALUES (?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castMemberCode);
            getPreparedStatement().setInt(2, castCode);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding castmember to cast: " + ex.getMessage(), ex);
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
        Objects.requireNonNull(getConnection());
        try {
            final String deletePromoQuery = "DELETE FROM partecipazione_cast "
                    + "WHERE CodiceMembro = ? "
                    + "AND CodiceCast = ?";
            setPreparedStatement(getConnection().prepareStatement(deletePromoQuery));
            getPreparedStatement().setInt(1, castMemberCode);
            getPreparedStatement().setInt(2, castCode);
            final int rowsAffectedPromo = getPreparedStatement().executeUpdate();
            return rowsAffectedPromo > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting castmember from cast: " + ex.getMessage(), ex);
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
        for (final UserRanking userRanking : bestNumberRatings) {
            final Optional<Integer> cinemaCode = getCinemaCode(userRanking.username());
            cinemaCode.ifPresent(integer -> {
                assignPromo(promoCode, expiration, integer, userRanking.username());
                assignPrizeTag(userRanking.username());
            });
        }
    }

    /**
     * Deletes a user from the system.
     *
     * @param username The username of the user to be deleted.
     * @return True if the user deletion was successful, false otherwise.
     */
    public boolean deleteUser(final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String deletePromoQuery = "DELETE FROM account "
                    + "WHERE Username = ? ";
            setPreparedStatement(getConnection().prepareStatement(deletePromoQuery));
            getPreparedStatement().setString(1, username);
            final int rowsAffectedPromo = getPreparedStatement().executeUpdate();
            return rowsAffectedPromo > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting user: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a registrar from the system.
     *
     * @param username The username of the registrar to be deleted.
     * @return True if the registrar deletion was successful, false otherwise.
     */
    public boolean deleteRegistrar(final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String deletePromoQuery = "DELETE FROM account "
                    + "WHERE Username = ? ";
            setPreparedStatement(getConnection().prepareStatement(deletePromoQuery));
            getPreparedStatement().setString(1, username);
            final int rowsAffectedPromo = getPreparedStatement().executeUpdate();
            return rowsAffectedPromo > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting registrar: " + ex.getMessage(), ex);
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
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isSeasonAvailable(seriesId, seasonId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a series is available.
     *
     * @param seriesId The id of the series to check.
     * @return true if the series is not available, false otherwise.
     */
    public boolean isSeriesAvailable(final int seriesId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isSeriesAvailable(seriesId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a film is available.
     *
     * @param filmId The id of the film to check.
     * @return true if the film is not available, false otherwise.
     */
    public boolean isFilmAvailable(final int filmId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isFilmAvailable(filmId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a cast member is available.
     *
     * @param castMemberId The id of the cast member to check.
     * @return true if the cast member is not available, false otherwise.
     */
    public boolean isCastMemberAvailable(final int castMemberId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isCastMemberAvailable(castMemberId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks whether a cast session is unavailable for the provided cast ID.
     *
     * @param castId The ID of the cast session to check availability for.
     * @return true if the cast session is unavailable, false otherwise.
     */
    public boolean isCastAvailable(final int castId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isCastAvailable(castId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a promo is available.
     *
     * @param promoId The id of the promo to check.
     * @return true if the promo is not available, false otherwise.
     */
    public boolean isPromoAvailable(final int promoId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isPromoAvailable(promoId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a cinema is available.
     *
     * @param cinemaId The id of the cinema to check.
     * @return true if the cinema is not available, false otherwise.
     */
    public boolean isCinemaAvailable(final int cinemaId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isCinemaAvailable(cinemaId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a user card is available.
     *
     * @param username   The username of the user to check.
     * @param cinemaCode The id of the cinema
     * @return true if the user is not available, false otherwise.
     */
    public boolean isCardAvailable(final String username, final int cinemaCode) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isCardAvailable(username, cinemaCode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of all users from the database.
     *
     * @return a list of User objects.
     */
    public List<User> getUsers() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getUsers();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of all registrars from the database.
     *
     * @return a list of Registrar objects.
     */
    public List<Registrar> getRegistrars() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getRegistrars();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new registrar to the database with the provided password and details.
     *
     * @param password the password for the new registrar.
     * @param registrar the Registrar object containing the new registrar's details.
     */
    public void addRegistrar(final String password, final Registrar registrar) {
        Objects.requireNonNull(getConnection());
        final String accountQuery = "INSERT INTO ACCOUNT (Username, PASSWORD, Nome, Cognome) VALUES (?,?,?,?)";
        final String regQuery = "INSERT INTO REGISTRATORE (Username, EmailCinema, CodiceCinema) VALUES (?,?,?)";
        try {
            getConnection().setAutoCommit(false);
            setPreparedStatement(getConnection().prepareStatement(accountQuery));
            getPreparedStatement().setString(1, registrar.getUsername());
            getPreparedStatement().setString(2, password);
            getPreparedStatement().setString(3, registrar.getName());
            getPreparedStatement().setString(4, registrar.getLastName());
            getPreparedStatement().executeUpdate();
            setPreparedStatement(getConnection().prepareStatement(regQuery));
            getPreparedStatement().setString(1, registrar.getUsername());
            getPreparedStatement().setString(2, registrar.getEmailCinema());
            getPreparedStatement().setInt(3, registrar.getCinema());
            getPreparedStatement().executeUpdate();
            getConnection().commit();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding registrar: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of all cinemas from the database.
     *
     * @return a list of Cinema objects.
     */
    public List<Cinema> getCinemas() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getCinemas();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new cinema to the database with the provided details.
     *
     * @param cinema the Cinema object containing the new cinema's details.
     */
    public void addCinema(final Cinema cinema) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO CINEMA "
                    + "(Nome, Ind_Via, Ind_CAP, Ind_Civico, Ind_Citta)"
                    + " VALUES (?,?,?,?,?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, cinema.nome());
            getPreparedStatement().setString(2, cinema.indVia());
            getPreparedStatement().setString(3, cinema.indCAP());
            getPreparedStatement().setInt(4, cinema.civico());
            getPreparedStatement().setString(PARAMETER_INDEX, cinema.citta());
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding cinema: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a cinema from the database identified by the provided code.
     *
     * @param code the code of the cinema to be deleted.
     * @return true if the cinema was successfully deleted, false otherwise.
     */
    public boolean deleteCinema(final int code) {
        Objects.requireNonNull(getConnection());
        try {
            final String deletePromoQuery = "DELETE FROM CINEMA "
                    + "WHERE Codice = ? ";
            setPreparedStatement(getConnection().prepareStatement(deletePromoQuery));
            getPreparedStatement().setInt(1, code);
            final int rowsAffectedPromo = getPreparedStatement().executeUpdate();
            return rowsAffectedPromo > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting cinema: " + ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a cast with the specified ID exists.
     *
     * @param castId The ID of the cast to be checked.
     * @return {@code true} if a cast with the specified ID exists, {@code false} otherwise.
     */
    public boolean isCast(final int castId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isCast(castId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the most recently added cast.
     *
     * @return the ID of the last cast.
     */
    public Integer getLastCastId() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getLastCastId();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the cast with the specified ID is empty.
     *
     * @param castId the ID of the cast to check.
     * @return {@code true} if the cast is empty, {@code false} otherwise.
     */
    public boolean isEmptyCast(final int castId) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isEmptyCast(castId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the film genres with the specified ID is empty.
     *
     * @param filmCode the ID of the film to check.
     * @return {@code true} if the film genres is empty, {@code false} otherwise.
     */
    public boolean isEmptyGenreFilm(final int filmCode) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isEmptyGenreFilm(filmCode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the series genres with the specified ID is empty.
     *
     * @param seriesCode the ID of the series to check.
     * @return {@code true} if the series genres is empty, {@code false} otherwise.
     */
    public boolean isEmptyGenreSeries(final int seriesCode) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isEmptyGenreSeries(seriesCode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the series with the specified ID is empty.
     *
     * @param seriesCode the ID of the series to check.
     * @return {@code true} if the series is empty, {@code false} otherwise.
     */
    public boolean isEmptySeries(final int seriesCode) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isEmptySeries(seriesCode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
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
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isEmptySeason(seriesCode, seasonNumber);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes the multimedia content associated with the specified cast ID.
     *
     * @param castId the ID of the cast whose multimedia content is to be deleted.
     */
    public void deleteMultimediaCast(final int castId) {
        Objects.requireNonNull(getConnection());
        try {
            final String seriesQuery = "SELECT DISTINCT CodiceSerie FROM STAGIONE "
                    + "WHERE CodiceCast = ?";
            setPreparedStatement(getConnection().prepareStatement(seriesQuery));
            getPreparedStatement().setInt(1, castId);
            setResultSet(getPreparedStatement().executeQuery());
            while (getResultSet().next()) {
                final int seriesId = getResultSet().getInt("CodiceSerie");
                final String seasonDeleteQuery = "DELETE FROM STAGIONE "
                        + "WHERE CodiceCast = ?";
                setPreparedStatement(getConnection().prepareStatement(seasonDeleteQuery));
                getPreparedStatement().setInt(1, castId);
                getPreparedStatement().executeUpdate();
                final String seriesDeleteQuery = "DELETE FROM SERIE "
                        + "WHERE Codice = ?";
                setPreparedStatement(getConnection().prepareStatement(seriesDeleteQuery));
                getPreparedStatement().setInt(1, seriesId);
                getPreparedStatement().executeUpdate();
            }
            final String filmQuery = "DELETE FROM FILM "
                    + "WHERE CodiceCast = ?";
            setPreparedStatement(getConnection().prepareStatement(filmQuery));
            getPreparedStatement().setInt(1, castId);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting cinema: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of cast member codes that are linked to a specified cast member.
     * This method executes a SQL query to fetch the linked cast members from the database.
     *
     * @param castMemberCode the unique code of the cast member whose linked cast members are to be retrieved
     * @return a list of integers representing the codes of the cast members linked to the specified cast member
     */
    public List<Integer> getCastLinked(final int castMemberCode) {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getCastLinked(castMemberCode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of multiples from the database.
     *
     * @return a list of integers representing the multiples.
     */
    public List<Integer> getMultiples() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getMultiples();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a template promotion with the specified percentage to the database.
     *
     * @param percentage the percentage of the template promotion to add.
     */
    public void addTemplatePromo(final int percentage) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO TEMPLATEPROMO "
                    + "(PercentualeSconto) "
                    + "VALUES (?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, percentage);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding template promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of template promotions from the database.
     *
     * @return a list of TemplatePromo objects.
     */
    public List<TemplatePromo> getTemplatePromos() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getTemplatePromos();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of single promotions from the database.
     *
     * @return a list of SinglePromo objects.
     */
    public List<SinglePromo> getSinglePromos() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getSinglePromos();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of genre promotions from the database.
     *
     * @return a list of GenrePromo objects.
     */
    public List<GenrePromo> getGenrePromos() {
        try (AdminGetOps mgr = new AdminGetOps()) {
            return mgr.getGenrePromos();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Adds a promotion with the specified code and expiration date to the database.
     *
     * @param code the code of the promotion to add.
     * @param expiration the expiration date of the promotion.
     */
    public void addPromo(final int code, final LocalDate expiration) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO PROMO "
                    + "(CodiceTemplatePromo, Scadenza)"
                    + " VALUES (?,?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, code);
            getPreparedStatement().setDate(2, Date.valueOf(expiration));
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a template promotion with the specified code is available in the database.
     *
     * @param codePromo the code of the template promotion to check.
     * @return {@code true} if the template promotion is available, {@code false} otherwise.
     */
    public boolean isTemplatePromoAvailable(final int codePromo) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isTemplatePromoAvailable(codePromo);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a multiple is available for the specified genre promotion in the database.
     *
     * @param genrePromo the genre promotion to check.
     * @return {@code true} if the multiple is available, {@code false} otherwise.
     */
    public boolean isMultipleAvailable(final int genrePromo) {
        try (AdminAvailabilityOps mgr = new AdminAvailabilityOps()) {
            return mgr.isMultipleAvailable(genrePromo);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a template promotion with the specified code from the database.
     *
     * @param code the code of the template promotion to delete.
     * @return {@code true} if the template promotion was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteTemplatePromo(final int code) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM TEMPLATEPROMO WHERE CodicePromo = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, code);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting template promo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new genre with the specified description to the database.
     *
     * @param genre The name of the genre to add.
     * @param description A description of the genre.
     */
    public void addGenre(final String genre, final String description) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO GENERE "
                    + "(Nome, Descrizione)"
                    + " VALUES (?,?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, genre);
            getPreparedStatement().setString(2, description);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding genre: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a section from the database.
     *
     * @param section The name of the section to delete.
     * @return {@code true} if the section was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteSection(final String section) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM SEZIONE WHERE Nome = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, section);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting section: " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a new section with the specified description to the database.
     *
     * @param section The name of the section to add.
     * @param description A description of the section.
     */
    public void addSection(final String section, final String description) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO SEZIONE "
                    + "(Nome, Dettaglio)"
                    + " VALUES (?,?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, section);
            getPreparedStatement().setString(2, description);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding section: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a genre from the database.
     *
     * @param genre The name of the genre to delete.
     * @return {@code true} if the genre was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteGenre(final String genre) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM GENERE WHERE Nome = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, genre);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting genre: " + ex.getMessage(), ex);
        }
    }

    /**
     * Associates a genre with a film in the database.
     *
     * @param filmId The ID of the film to which the genre will be added.
     * @param genre The name of the genre to add to the film.
     */
    public void addGenreToFilm(final int filmId, final String genre) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO CATEGORIZZAZIONE_FILM "
                    + "(NomeGenere, CodiceFilm) "
                    + "VALUES (?,?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, genre);
            getPreparedStatement().setInt(2, filmId);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding genre to film: " + ex.getMessage(), ex);
        }
    }

    /**
     * Removes a genre association from a film in the database.
     *
     * @param filmCode The ID of the film from which the genre will be removed.
     * @param genre The name of the genre to remove from the film.
     * @return {@code true} if the genre was successfully removed from the film, {@code false} otherwise.
     */
    public boolean deleteGenreToFilm(final int filmCode, final String genre) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM CATEGORIZZAZIONE_FILM "
                    + "WHERE NomeGenere = ? AND CodiceFilm = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, genre);
            getPreparedStatement().setInt(2, filmCode);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting genre from film: " + ex.getMessage(), ex);
        }
    }

    /**
     * Associates a genre with a series in the database.
     *
     * @param seriesId The ID of the series to which the genre will be added.
     * @param genre The name of the genre to add to the series.
     */
    public void addGenreToSeries(final int seriesId, final String genre) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO CATEGORIZZAZIONE_SERIE "
                    + "(NomeGenere, CodiceSerie) "
                    + "VALUES (?,?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, genre);
            getPreparedStatement().setInt(2, seriesId);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding genre to series: " + ex.getMessage(), ex);
        }
    }

    /**
     * Removes a genre association from a series in the database.
     *
     * @param seriesCode The ID of the series from which the genre will be removed.
     * @param genre The name of the genre to remove from the series.
     * @return {@code true} if the genre was successfully removed from the series, {@code false} otherwise.
     */
    public boolean deleteGenreToSeries(final int seriesCode, final String genre) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "DELETE FROM CATEGORIZZAZIONE_SERIE "
                    + "WHERE NomeGenere = ? AND CodiceSerie = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, genre);
            getPreparedStatement().setInt(2, seriesCode);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting genre from serie: " + ex.getMessage(), ex);
        }
    }

    private Optional<Integer> getCinemaCode(final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT CodiceCinema FROM tessera WHERE UsernameUtente = ? LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, username);
            setResultSet(getPreparedStatement().executeQuery());
            if (getResultSet().next()) {
                return Optional.of(getResultSet().getInt("CodiceCinema"));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving cinemaCode: " + ex.getMessage(), ex);
        }
    }

    private List<UserRanking> getBestReviewersRanking() {
        final String query =
                "SELECT UsernameUtente, SUM(NumeroValutazioni) AS TotaleValutazioni "
                        + "FROM ("
                        + "SELECT UsernameUtente, COUNT(*) AS NumeroValutazioni "
                        + "FROM recfilm "
                        + "GROUP BY UsernameUtente "
                        + "UNION ALL "
                        + "SELECT UsernameUtente, COUNT(*) AS NumeroValutazioni "
                        + "FROM recserie "
                        + "GROUP BY UsernameUtente) AS RecensioniTotali "
                        + "GROUP BY UsernameUtente "
                        + "ORDER BY TotaleValutazioni DESC "
                        + "LIMIT 5";
        return getResult(query);
    }

    private List<UserRanking> getWorstUtilityReviewersRanking() {
        final String query =
                "SELECT UsernameUtenteValutato, "
                        + "SUM(CASE WHEN Positiva = TRUE THEN 1 ELSE -1 END) / COUNT(*) AS MediaValutazione "
                        + "FROM (SELECT UsernameUtenteValutato, Positiva FROM valutazione_film "
                        + "UNION ALL "
                        + "SELECT UsernameUtenteValutato, Positiva FROM valutazione_serie) AS AllReviews "
                        + "GROUP BY UsernameUtenteValutato "
                        + "ORDER BY MediaValutazione ASC "
                        + "LIMIT 5";
        return getResult(query);
    }

    private List<UserRanking> getBestUtilityReviewersRanking() {
        final String query =
                "SELECT UsernameUtenteValutato, "
                        + "SUM(CASE WHEN Positiva = TRUE THEN 1 ELSE -1 END) / COUNT(*) AS MediaValutazione "
                        + "FROM (SELECT UsernameUtenteValutato, Positiva FROM valutazione_film "
                        + "UNION ALL "
                        + "SELECT UsernameUtenteValutato, Positiva FROM valutazione_serie) AS AllReviews "
                        + "GROUP BY UsernameUtenteValutato "
                        + "ORDER BY MediaValutazione "
                        + "LIMIT 5";
        return getResult(query);
    }

    private List<UserRanking> getResult(final String query) {
        try {
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<UserRanking> rankings = new ArrayList<>();
            while (getResultSet().next()) {
                rankings.add(new UserRanking(
                        getResultSet().getString(1),
                        getResultSet().getInt(2)));
            }
            return rankings;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving rankings: " + ex.getMessage(), ex);
        }
    }

    private void updateSeries(final int seriesCode, final int duration, final boolean add) {
        Objects.requireNonNull(getConnection());
        try {
            final String addQuery = "UPDATE SERIE "
                    + "SET DurataComplessiva = DurataComplessiva + ?, "
                    + "NumeroEpisodi = NumeroEpisodi + 1 "
                    + "WHERE Codice = ?";
            final String subtractQuery = "UPDATE SERIE "
                    + "SET DurataComplessiva = DurataComplessiva - ?, "
                    + "NumeroEpisodi = NumeroEpisodi - 1 "
                    + "WHERE Codice = ?";
            if (add) {
                setPreparedStatement(getConnection().prepareStatement(addQuery));
            } else {
                setPreparedStatement(getConnection().prepareStatement(subtractQuery));
            }
            getPreparedStatement().setInt(1, duration);
            getPreparedStatement().setInt(2, seriesCode);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error updating series: " + ex.getMessage(), ex);
        }
    }

    private void assignPrizeTag(final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "UPDATE UTENTE "
                    + "SET TargaPremio = 1 "
                    + "WHERE Username = ? ";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, username);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error assigning Prize Tag: " + ex.getMessage(), ex);
        }
    }
}
