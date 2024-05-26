package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.promo.Promo;
import unibo.cineradar.model.ranking.CastRanking;
import unibo.cineradar.model.ranking.EvalType;
import unibo.cineradar.model.ranking.UserRanking;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Administrator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

    private static final int PARAMETER_INDEX1 = 5;
    private static final int PARAMETER_INDEX2 = 6;
    private static final int PARAMETER_INDEX3 = 7;
    private static final String CODICE_SERIE = "CodiceSerie";

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
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves the list of all requests.
     *
     * @return The list of all requests.
     */
    public List<Request> getRequests() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * "
                    + "FROM richiesta";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Request> requests = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Request request = new Request(
                        this.getResultSet().getInt("Numero"),
                        this.getResultSet().getString("UsernameUtente"),
                        this.getResultSet().getBoolean("Tipo"),
                        this.getResultSet().getString("Titolo"),
                        this.getResultSet().getString("Descrizione"),
                        this.getResultSet().getBoolean("Chiusa"),
                        this.getResultSet().getDate("AnnoUscita")
                );
                requests.add(request);
            }
            return List.copyOf(requests);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves the list of all films.
     *
     * @return The list of all films.
     */
    public List<Film> getFilms() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM film";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Film> films = new ArrayList<>();
            while (getResultSet().next()) {
                final Film film = new Film(
                        getResultSet().getInt("Codice"),
                        getResultSet().getString("Titolo"),
                        getResultSet().getInt("EtaLimite"),
                        getResultSet().getString("Trama"),
                        getResultSet().getInt("Durata"),
                        getResultSet().getInt("CodiceCast")
                );
                films.add(film);
            }
            return List.copyOf(films);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves the list of all the series.
     *
     * @return The list of all the series.
     */
    public List<Serie> getSeries() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM serie";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Serie> series = new ArrayList<>();
            while (getResultSet().next()) {
                final Serie serie = new Serie(
                        getResultSet().getInt("Codice"),
                        getResultSet().getString("Titolo"),
                        getResultSet().getInt("EtaLimite"),
                        getResultSet().getString("Trama"),
                        getResultSet().getInt("DurataComplessiva"),
                        getResultSet().getInt("NumeroEpisodi")
                );
                series.add(serie);
            }
            return series;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
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
            throw new IllegalArgumentException("Error deleting series", ex);
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
            throw new IllegalArgumentException("Error deleting film", ex);
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
            getPreparedStatement().setInt(PARAMETER_INDEX1, film.getCastId());
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding film", ex);
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
            getPreparedStatement().setInt(PARAMETER_INDEX1, serie.getNumberOfEpisodes());
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding series", ex);
        }
    }

    /**
     * Retrieves details of films including their cast from the database.
     *
     * @return A map containing films as keys and their corresponding cast as values.
     * @throws IllegalArgumentException If an SQL exception occurs.
     */
    public Map<Film, Cast> getFilmsDetails() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT film.Codice AS CodiceFilm, "
                    + "film.Titolo AS TitoloFilm, "
                    + "film.EtaLimite AS EtaLimiteFilm, "
                    + "film.Trama AS TramaFilm, "
                    + "film.Durata AS DurataFilm, "
                    + "film.CodiceCast AS CodiceCastFilm, "
                    + "membrocast.Codice AS CodiceMembroCast, "
                    + "membrocast.Nome AS NomeMembroCast, "
                    + "membrocast.Cognome AS CognomeMembroCast, "
                    + "membrocast.DataNascita AS DataNascitaMembroCast, "
                    + "membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast, "
                    + "membrocast.NomeArte AS NomeArteMembroCast, "
                    + "membrocast.TipoAttore AS TipoAttoreMembroCast, "
                    + "membrocast.TipoRegista AS TipoRegistaMembroCast "
                    + "FROM film "
                    + "JOIN casting ON film.CodiceCast = casting.Codice "
                    + "JOIN partecipazione_cast ON casting.codice = partecipazione_cast.CodiceCast "
                    + "JOIN membrocast ON partecipazione_cast.CodiceMembro = membrocast.Codice";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final Map<Film, Cast> detailedFilms = new HashMap<>();
            while (getResultSet().next()) {
                final int filmCode = getResultSet().getInt("CodiceFilm");
                final Film film = new Film(
                        filmCode,
                        getResultSet().getString("TitoloFilm"),
                        getResultSet().getInt("EtaLimiteFilm"),
                        getResultSet().getString("TramaFilm"),
                        getResultSet().getInt("DurataFilm"),
                        getResultSet().getInt("CodiceCastFilm")
                );
                final Cast cast = detailedFilms.getOrDefault(film, new Cast());
                cast.addCastMember(new CastMember(
                        getResultSet().getInt("CodiceMembroCast"),
                        getResultSet().getString("NomeMembroCast"),
                        getResultSet().getString("CognomeMembroCast"),
                        getResultSet().getDate("DataNascitaMembroCast").toLocalDate(),
                        getResultSet().getDate("DataDebuttoCarrieraMembroCast").toLocalDate(),
                        getResultSet().getString("NomeArteMembroCast")
                ));
                detailedFilms.put(film, cast);
            }
            return Map.copyOf(detailedFilms);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves detailed information about series, including seasons, episodes, and cast members.
     *
     * @return A list of Series objects containing detailed information about series, seasons, episodes, and cast members.
     * @throws IllegalArgumentException If there's an issue with executing the SQL query.
     */
    public List<Serie> getDetailedSeries() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT serie.Codice AS CodiceSerie,"
                    + " stagione.NumeroStagione,"
                    + " episodio.NumeroEpisodio,"
                    + " serie.Titolo AS TitoloSerie,"
                    + " serie.EtaLimite AS EtaLimiteSerie,"
                    + " serie.Trama AS TramaSerie,"
                    + " serie.DurataComplessiva AS DurataComplessivaSerie,"
                    + " serie.NumeroEpisodi AS NumeroEpisodiSerie,"
                    + " stagione.Sunto AS SuntoStagione,"
                    + " episodio.DurataMin AS DurataEpisodio,"
                    + " casting.Nome AS NomeCasting,"
                    + " casting.Codice AS CodiceCast,"
                    + " membrocast.Codice AS CodiceMembroCast,"
                    + " membrocast.Nome AS NomeMembroCast,"
                    + " membrocast.Cognome AS CognomeMembroCast,"
                    + " membrocast.DataNascita AS DataNascitaMembroCast,"
                    + " membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast,"
                    + " membrocast.NomeArte AS NomeArteMembroCast,"
                    + " membrocast.TipoAttore AS TipoAttoreMembroCast,"
                    + " membrocast.TipoRegista AS TipoRegistaMembroCast"
                    + " FROM serie"
                    + " JOIN stagione ON serie.Codice = stagione.CodiceSerie"
                    + " JOIN episodio ON episodio.NumeroStagione = stagione.NumeroStagione"
                    + " AND episodio.CodiceSerie = stagione.CodiceSerie"
                    + " JOIN casting ON casting.Codice = stagione.CodiceCast"
                    + " AND episodio.CodiceSerie = stagione.CodiceSerie"
                    + " JOIN partecipazione_cast ON partecipazione_cast.CodiceCast = casting.Codice"
                    + " JOIN membrocast ON membrocast.Codice = partecipazione_cast.CodiceMembro"
                    + " ORDER BY CodiceSerie";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return processResultSet();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Adds a new cast member to the database.
     *
     * @param castMember The cast member object to add to the database.
     */
    public void addCastMember(final CastMember castMember) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT"
                    + " INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, castMember.getName());
            getPreparedStatement().setString(2, castMember.getLastName());
            getPreparedStatement().setDate(3, Date.valueOf(castMember.getBirthDate()));
            getPreparedStatement().setBoolean(4, castMember instanceof Actor);
            getPreparedStatement().setBoolean(PARAMETER_INDEX1, castMember instanceof Director);
            if (!Objects.isNull(castMember.getCareerDebutDate())) {
                getPreparedStatement().setDate(PARAMETER_INDEX2, Date.valueOf(castMember.getCareerDebutDate()));
            } else {
                getPreparedStatement().setNull(PARAMETER_INDEX2, java.sql.Types.DATE);
            }
            if (!Objects.isNull(castMember.getStageName())) {
                getPreparedStatement().setString(PARAMETER_INDEX3, castMember.getStageName());
            } else {
                getPreparedStatement().setNull(PARAMETER_INDEX3, java.sql.Types.VARCHAR);
            }
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding cast member", ex);
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
            throw new IllegalArgumentException("Error deleting cast member", ex);
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
            throw new IllegalArgumentException("Error adding season", ex);
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
            final String query = "DELETE FROM stagione WHERE CodiceSerie = ? AND NumeroStagione = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting season", ex);
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
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding episode", ex);
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
            final String query = "DELETE FROM episodio WHERE CodiceSerie = ? AND NumeroStagione = ? AND NumeroEpisodio = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            getPreparedStatement().setInt(3, episodeNumber);
            final int rowsAffected = getPreparedStatement().executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error deleting episode", ex);
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
     * @throws IllegalArgumentException if an invalid evaluation type is provided.
     * @throws IllegalArgumentException if there is an error retrieving rankings from the database.
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
     * @throws IllegalArgumentException if an invalid evaluation type is provided.
     * @throws IllegalArgumentException if there is an error retrieving rankings from the database.
     */
    public List<CastRanking> getCastRankings(final EvalType evaluationType) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = switch (evaluationType) {
                case BEST_DIRECTORS -> "SELECT Nome, Cognome, COUNT(*) AS NumeroPresenze "
                        + "FROM partecipazione_cast "
                        + "JOIN membrocast ON membrocast.Codice = partecipazione_cast.CodiceMembro "
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
            throw new IllegalArgumentException("Error retrieving cast rankings", ex);
        }
    }

    /**
     * Retrieves a list of cast members from the database.
     *
     * @return A list of CastMember objects representing the cast members.
     * @throws IllegalArgumentException If an error occurs while retrieving cast members from the database.
     */
    public List<CastMember> getCastMembers() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM membrocast";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<CastMember> members = new ArrayList<>();
            while (getResultSet().next()) {
                if (getResultSet().getBoolean("TipoAttore")) {
                    members.add(new Actor(
                            getResultSet().getInt(1),
                            getResultSet().getString(2),
                            getResultSet().getString(3),
                            getResultSet().getDate(4).toLocalDate(),
                            getResultSet().getDate(PARAMETER_INDEX3).toLocalDate(),
                            getResultSet().getString(8)
                    ));
                } else {
                    members.add(new Director(
                            getResultSet().getInt(1),
                            getResultSet().getString(2),
                            getResultSet().getString(3),
                            getResultSet().getDate(4).toLocalDate(),
                            getResultSet().getDate(PARAMETER_INDEX3).toLocalDate(),
                            getResultSet().getString(8)
                    ));
                }
            }
            return List.copyOf(members);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving cast members", ex);
        }
    }

    /**
     * Retrieves a list of casting details from the database.
     *
     * @return A list of Casting objects representing the casting details.
     * @throws IllegalArgumentException If an error occurs while retrieving casting details from the database.
     */
    public List<Casting> getCasting() {
        final String query = "SELECT * FROM casting";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<Casting> casting = new ArrayList<>();
            while (resultSet.next()) {
                casting.add(new Casting(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }
            return casting;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving casting", ex);
        }
    }

    /**
     * Adds a new casting detail with the given name to the database.
     *
     * @param name The name of the casting detail to be added.
     * @throws IllegalArgumentException If an error occurs while adding the casting detail to the database.
     */
    public void addCast(final String name) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT"
                    + " INTO casting (Nome)"
                    + " VALUES (?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, name);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding casting", ex);
        }
    }

    /**
     * Deletes a casting detail from the database based on the provided ID.
     *
     * @param id The ID of the casting detail to be deleted.
     * @return True if the casting detail was successfully deleted, false otherwise.
     * @throws IllegalArgumentException If an error occurs while deleting the casting detail from the database.
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
            throw new IllegalArgumentException("Error deleting casting", ex);
        }
    }

    /**
     * Retrieves the ID of the last added series from the database.
     *
     * @return The ID of the last series added to the system.
     * @throws IllegalArgumentException If there is an error retrieving the series ID from the database.
     */
    public Integer getLastSeriesId() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice"
                    + " FROM serie"
                    + " ORDER BY Codice DESC"
                    + " LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());

            if (getResultSet().next()) {
                return getResultSet().getInt(1);
            } else {
                throw new IllegalArgumentException("Error retrieving series id");
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving series id", ex);
        }
    }

    /**
     * Retrieves the ID of the last added season for a given series from the database.
     *
     * @param seriesCode The code identifying the series.
     * @return The ID of the last season added to the specified series.
     * @throws IllegalArgumentException If there is an error retrieving the season ID from the database.
     */
    public Integer getLastSeasonId(final int seriesCode) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT NumeroStagione"
                    + " FROM stagione"
                    + " WHERE stagione.CodiceSerie = ?"
                    + " ORDER BY NumeroStagione DESC"
                    + " LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesCode);
            setResultSet(getPreparedStatement().executeQuery());

            if (getResultSet().next()) {
                return getResultSet().getInt(1);
            } else {
                throw new IllegalArgumentException("Error retrieving season id");
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving season id", ex);
        }
    }

    /**
     * Marks a request as complete in the database.
     *
     * @param code The code of the request to be marked as complete.
     * @return True if the request was successfully marked as complete, false otherwise.
     * @throws IllegalArgumentException If there is an error updating the request status in the database.
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
            throw new IllegalArgumentException("Error updating request status", ex);
        }
    }

    /**
     * Retrieves the list of promotional offers from the database.
     * This method executes an SQL query to fetch promo details by joining the 'templatepromo' and 'promo' tables.
     *
     * @return a list of {@link Promo} objects representing the current promotional offers.
     * @throws IllegalArgumentException if there is an error while retrieving the promotional offers.
     * @throws NullPointerException if the database connection is null.
     */
    public List<Promo> getPromos() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT templatepromo.CodicePromo, templatepromo.PercentualeSconto, promo.Scadenza"
                    + " FROM templatepromo"
                    + " JOIN promo ON templatepromo.CodicePromo = promo.CodiceTemplatePromo";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Promo> promos = new ArrayList<>();
            while (getResultSet().next()) {
                final Promo promo = new Promo(
                        getResultSet().getInt("CodicePromo"),
                        getResultSet().getInt("PercentualeSconto"),
                        getResultSet().getDate("Scadenza").toLocalDate()
                );
                promos.add(promo);
            }
            return List.copyOf(promos);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error updating request status", ex);
        }
    }

    /**
     * Adds a new promotional offer to the database.
     * This method inserts a new promo into the 'templatepromo' table if necessary and then into the 'promo' table.
     * If the percentage discount already exists, it uses the existing template promo code.
     *
     * @param promo the {@link Promo} object representing the promotional offer to be added.
     * @throws IllegalArgumentException if there is an error while adding the promotional offer.
     * @throws IllegalStateException if there is an error rolling back the transaction.
     * @throws NullPointerException if the database connection is null.
     */
    public void addPromo(final Promo promo) {
        Objects.requireNonNull(getConnection());
        final String checkPromoQuery = "SELECT CodicePromo FROM TEMPLATEPROMO WHERE PercentualeSconto = ?";
        final String templatePromoQuery = "INSERT INTO TEMPLATEPROMO (PercentualeSconto) VALUES (?)";
        final String promoQuery = "INSERT INTO PROMO (CodiceTemplatePromo, Scadenza) VALUES (?, ?)";
        try {
            getConnection().setAutoCommit(false);
            setPreparedStatement(getConnection().prepareStatement(checkPromoQuery));
            getPreparedStatement().setInt(1, promo.percentageDiscount());
            ResultSet rs = getPreparedStatement().executeQuery();
            int generatedCodicePromo = 0;
            if (rs.next()) {
                generatedCodicePromo = rs.getInt(1);
            } else {
                setPreparedStatement(getConnection().prepareStatement(templatePromoQuery, Statement.RETURN_GENERATED_KEYS));
                getPreparedStatement().setInt(1, promo.percentageDiscount());
                getPreparedStatement().executeUpdate();
                rs = getPreparedStatement().getGeneratedKeys();
                if (rs.next()) {
                    generatedCodicePromo = rs.getInt(1);
                }
            }
            setPreparedStatement(getConnection().prepareStatement(promoQuery));
            getPreparedStatement().setInt(1, generatedCodicePromo);
            getPreparedStatement().setDate(2, Date.valueOf(promo.expiration()));
            getPreparedStatement().executeUpdate();
            getConnection().commit();
        } catch (SQLException ex) {
            SQLException rollbackEx = null;
            try {
                getConnection().rollback();
            } catch (SQLException e) {
                rollbackEx = e;
            }
            if (rollbackEx != null) {
                ex.addSuppressed(rollbackEx);
            }
            throw new IllegalArgumentException("Error adding promo", ex);
        }
    }

    /**
     * Deletes a promotional offer from the database based on the provided code and expiration date.
     * This method executes an SQL query to delete the promo from the 'promo' table.
     *
     * @param code the unique code of the promotional offer to be deleted.
     * @param expiration the expiration date of the promotional offer to be deleted.
     * @return {@code true} if the promotional offer was successfully deleted, {@code false} otherwise.
     * @throws IllegalArgumentException if there is an error while deleting the promotional offer.
     * @throws NullPointerException if the database connection is null.
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
            throw new IllegalArgumentException("Error deleting promo", ex);
        }
    }

    /**
     * Retrieves the list of cards from the database.
     * This method executes an SQL query to fetch cards details.
     *
     * @return a list of {@link CardReg} objects representing the cards.
     * @throws IllegalArgumentException if there is an error while retrieving the cards.
     * @throws NullPointerException if the database connection is null.
     */
    public List<CardReg> getCards() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM tessera";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<CardReg> cards = new ArrayList<>();
            while (getResultSet().next()) {
                final CardReg card = new CardReg(
                        getResultSet().getString("UsernameUtente"),
                        getResultSet().getDate("DataRinnovo").toLocalDate(),
                        getResultSet().getInt("CodiceCinema"),
                        getResultSet().getInt("NumeroTessera")
                );
                cards.add(card);
            }
            return List.copyOf(cards);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error updating request status", ex);
        }
    }

    /**
     * Assigns a promotional code to a user for a specific cinema.
     *
     * @param promoCode The promotional code to assign.
     * @param expiration The expiration date of the promotional code.
     * @param cinemaCode The code representing the cinema.
     * @param username The username of the user to whom the promotional code will be assigned.
     * @throws NullPointerException If the connection object is null.
     * @throws IllegalArgumentException If an error occurs while executing the SQL query.
     */
    public void assignPromo(
            final int promoCode, final LocalDate expiration, final int cinemaCode, final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO"
                    + " premi_tessera(CodicePromoPromo, Scadenza, CodiceCinema, UsernameUtente)"
                    + " VALUES (?, ?, ?, ?)";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, promoCode);
            getPreparedStatement().setDate(2, Date.valueOf(expiration));
            getPreparedStatement().setInt(3, cinemaCode);
            getPreparedStatement().setString(4, username);
            getPreparedStatement().executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding casting", ex);
        }
    }

    /**
     * Retrieves detailed information about the cast with the specified ID.
     *
     * @param castId The unique identifier of the cast.
     * @return A list of CastMember objects representing detailed information about the cast.
     * @throws IllegalArgumentException If an error occurs while retrieving the cast details.
     */
    public List<CastMember> getDetailedCast(final int castId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT membrocast.Codice, membrocast.Nome, membrocast.Cognome, "
                    + "membrocast.DataNascita, membrocast.DataDebuttoCarriera, membrocast.NomeArte "
                    + "FROM membrocast "
                    + "INNER JOIN partecipazione_cast ON membrocast.Codice = partecipazione_cast.CodiceMembro "
                    + "WHERE partecipazione_cast.CodiceCast = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castId);
            setResultSet(getPreparedStatement().executeQuery());
            final List<CastMember> castMembers = new ArrayList<>();
            while (getResultSet().next()) {
                final CastMember castMember = new CastMember(
                        getResultSet().getInt("Codice"),
                        getResultSet().getString("Nome"),
                        getResultSet().getString("Cognome"),
                        getResultSet().getDate("DataNascita").toLocalDate(),
                        getResultSet().getDate("DataDebuttoCarriera").toLocalDate(),
                        getResultSet().getString("NomeArte")
                );
                castMembers.add(castMember);
            }
            return List.copyOf(castMembers);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving details of cast", ex);
        }
    }

    /**
     * Adds a cast member to the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be added.
     * @param castCode       The code representing the cast to which the member will be added.
     * @throws IllegalArgumentException If an error occurs while adding the cast member to the cast.
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
            throw new IllegalArgumentException("Error adding castmember to cast", ex);
        }
    }

    /**
     * Deletes a cast member from the specified cast.
     *
     * @param castMemberCode The code representing the cast member to be deleted.
     * @param castCode       The code representing the cast from which the member will be deleted.
     * @return True if the deletion was successful, false otherwise.
     * @throws IllegalArgumentException If an error occurs while deleting the cast member from the cast.
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
            throw new IllegalArgumentException("Error deleting castmember from cast", ex);
        }
    }

    /**
     * Assigns the best five reviewers to a promotional event.
     *
     * @param promoCode         The unique identifier of the promotional event.
     * @param expiration        The expiration date of the promotional event.
     * @param bestNumberRatings A list of UserRanking objects representing the best reviewers.
     * @throws IllegalArgumentException If an error occurs while assigning the best reviewers to the promotional event.
     */
    public void assignPromoBestFiveReviewers(
            final int promoCode, final LocalDate expiration, final List<UserRanking> bestNumberRatings) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "INSERT INTO "
                    + "premi_tessera(CodicePromoPromo, Scadenza, CodiceCinema, UsernameUtente) "
                    + "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE CodicePromoPromo = CodicePromoPromo";
            for (final UserRanking userRanking : bestNumberRatings) {
                final Optional<Integer> cinemaCode = getCinemaCode(userRanking.username());
                setPreparedStatement(getConnection().prepareStatement(query));
                if (cinemaCode.isPresent()) {
                    getPreparedStatement().setInt(1, promoCode);
                    getPreparedStatement().setDate(2, Date.valueOf(expiration));
                    getPreparedStatement().setInt(3, cinemaCode.get());
                    getPreparedStatement().setString(4, userRanking.username());
                    getPreparedStatement().executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error adding promo to all users", ex);
        }
    }

    /**
     * Deletes a user from the system.
     *
     * @param username The username of the user to be deleted.
     * @return True if the user deletion was successful, false otherwise.
     * @throws IllegalArgumentException If an error occurs while deleting the user.
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
            throw new IllegalArgumentException("Error deleting castmember from cast", ex);
        }
    }

    /**
     * Retrieves the cinema code associated with the given username from the database.
     *
     * @param username The username for which to retrieve the cinema code.
     * @return An Optional containing the cinema code if found, otherwise empty.
     * @throws IllegalArgumentException If an error occurs while retrieving the cinema code.
     */
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
            throw new IllegalArgumentException("Error retrieving cinemaCode", ex);
        }
    }

    /**
     * Retrieves the ranking of the top five reviewers based on the total number of reviews provided.
     *
     * @return A list of UserRanking objects representing the top five reviewers.
     */
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

    /**
     * Retrieves the ranking of the bottom five utility reviewers based on the average utility score of their reviews.
     *
     * @return A list of UserRanking objects representing the bottom five utility reviewers.
     */
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

    /**
     * Retrieves the ranking of the top five utility reviewers based on the average utility score of their reviews.
     *
     * @return A list of UserRanking objects representing the top five utility reviewers.
     */
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

    /**
     * Executes the given SQL query and retrieves the result set,
     * then processes the result set to construct a list of UserRanking objects.
     *
     * @param query The SQL query to execute.
     * @return A list of UserRanking objects constructed from the result set.
     * @throws IllegalArgumentException If an error occurs while retrieving the rankings.
     */
    private List<UserRanking> getResult(final String query) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<UserRanking> rankings = new ArrayList<>();
            while (resultSet.next()) {
                rankings.add(new UserRanking(
                        resultSet.getString(1),
                        resultSet.getInt(2)));
            }
            return rankings;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving rankings", ex);
        }
    }

    /**
     * Processes the ResultSet obtained from the executed SQL query and constructs Serie, Season, Episode, and CastMember objects.
     *
     * @return A list of Serie objects containing detailed information about series, seasons, episodes, and cast members.
     * @throws SQLException If there's an issue with processing the ResultSet.
     */
    private List<Serie> processResultSet() throws SQLException {
        final List<Serie> detailedSeries = new ArrayList<>();
        while (this.getResultSet().next()) {
            final Serie serie = createSerieFromResultSet();
            final Season season = createSeasonFromResultSet();
            final Episode episode = createEpisodeFromResultSet();
            final CastMember castMember = getNewCastMember();

            addToDetailedSeries(detailedSeries, serie, season, episode, castMember);
        }
        return List.copyOf(detailedSeries);
    }

    /**
     * Creates a Serie object from the current row of the ResultSet.
     *
     * @return A Serie object representing the current series.
     * @throws SQLException If there's an issue with retrieving data from the ResultSet.
     */
    private Serie createSerieFromResultSet() throws SQLException {
        return new Serie(
                this.getResultSet().getInt(CODICE_SERIE),
                this.getResultSet().getString("TitoloSerie"),
                this.getResultSet().getInt("EtaLimiteSerie"),
                this.getResultSet().getString("TramaSerie"),
                this.getResultSet().getInt("DurataComplessivaSerie"),
                this.getResultSet().getInt("NumeroEpisodiSerie")
        );
    }

    /**
     * Creates a Season object from the current row of the ResultSet.
     *
     * @return A Season object representing the current season.
     * @throws SQLException If there's an issue with retrieving data from the ResultSet.
     */
    private Season createSeasonFromResultSet() throws SQLException {
        return new Season(
                this.getResultSet().getInt(CODICE_SERIE),
                this.getResultSet().getInt("NumeroStagione"),
                this.getResultSet().getString("SuntoStagione"),
                this.getResultSet().getInt("CodiceCast")
        );
    }

    /**
     * Creates an Episode object from the current row of the ResultSet.
     *
     * @return An Episode object representing the current episode.
     * @throws SQLException If there's an issue with retrieving data from the ResultSet.
     */
    private Episode createEpisodeFromResultSet() throws SQLException {
        return new Episode(
                this.getResultSet().getInt(CODICE_SERIE),
                this.getResultSet().getInt("NumeroStagione"),
                this.getResultSet().getInt("NumeroEpisodio"),
                this.getResultSet().getInt("DurataEpisodio")
        );
    }

    /**
     * Adds a Serie, Season, Episode, and CastMember to the list of detailed series.
     *
     * @param detailedSeries The list of detailed series.
     * @param serie          The Serie object to add.
     * @param season         The Season object to add.
     * @param episode        The Episode object to add.
     * @param castMember     The CastMember object to add.
     */
    private void addToDetailedSeries(
            final List<Serie> detailedSeries, final Serie serie,
            final Season season, final Episode episode, final CastMember castMember) {
        if (!detailedSeries.contains(serie)) {
            season.addEpisode(episode);
            season.addCastMember(castMember);
            serie.addSeason(season);
            detailedSeries.add(serie);
        } else {
            final Serie existingSerie = detailedSeries.get(detailedSeries.indexOf(serie));
            if (!existingSerie.getSeasons().contains(season)) {
                season.addCastMember(castMember);
                existingSerie.addSeason(season);
            } else {
                final Season existingSeason = existingSerie.getSeason(season);
                if (!existingSeason.getEpisodes().contains(episode)) {
                    existingSeason.addCastMember(castMember);
                    existingSeason.addEpisode(episode);
                }
            }
        }
    }

    /**
     * Retrieves a new CastMember object from the current row of the ResultSet.
     *
     * @return A CastMember object representing a member of the cast.
     * @throws SQLException             If there's an issue with retrieving data from the ResultSet.
     * @throws IllegalArgumentException If the member's type cannot be determined.
     */
    private CastMember getNewCastMember() throws SQLException {
        final int code = this.getResultSet().getInt("CodiceMembroCast");
        final String name = this.getResultSet().getString("NomeMembroCast");
        final String surname = this.getResultSet().getString("CognomeMembroCast");
        final LocalDate birthDate = this.getResultSet().getDate("DataNascitaMembroCast").toLocalDate();
        final LocalDate debutDate = this.getResultSet().getDate("DataDebuttoCarrieraMembroCast").toLocalDate();
        final String artisticName = this.getResultSet().getString("NomeArteMembroCast");

        if (this.getResultSet().getBoolean("TipoAttoreMembroCast")
                && !this.getResultSet().getBoolean("TipoRegistaMembroCast")) {
            return new Actor(code, name, surname, birthDate, debutDate, artisticName);
        } else if (!this.getResultSet().getBoolean("TipoAttoreMembroCast")
                && this.getResultSet().getBoolean("TipoRegistaMembroCast")) {
            return new Director(code, name, surname, birthDate, debutDate, artisticName);
        }
        throw new IllegalArgumentException();
    }
}
