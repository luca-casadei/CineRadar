package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SerieReview;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.sql.Types.NULL;

/**
 * Database operations that the user can perform.
 */
@SuppressFBWarnings(
        value = {
                "OBL_UNSATISFIED_OBLIGATION"
        },
        justification = "The parent class satisfies the obligation."
)
public final class UserOps extends DBManager {

    private static final String ID_NAME = "Codice";
    private static final String TITLE_NAME = "Titolo";
    private static final String LIMIT_AGE_NAME = "EtaLimite";
    private static final String PLOT_NAME = "Trama";
    private static final String ID_FILM_NAME = "CodiceFilm";
    private static final String ID_SERIE_NAME = "CodiceSerie";

    /**
     * Retrieves the list of all films.
     *
     * @param userAge The limited age to be respected.
     * @return The list of all films.
     */
    public List<Film> getFilms(final int userAge) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * FROM film "
                    + "WHERE film.EtaLimite <= ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, userAge);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Film> films = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Film film = new Film(
                        this.getResultSet().getInt(ID_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getInt(LIMIT_AGE_NAME),
                        this.getResultSet().getString(PLOT_NAME),
                        this.getResultSet().getInt("Durata"),
                        this.getResultSet().getInt("CodiceCast")
                );
                films.add(film);
            }
            return List.copyOf(films);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Gets the details of a user given its username.
     *
     * @param username The username of the user.
     * @return A list of details of the retrieved account.
     */
    public Optional<User> getUserDetails(final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT utente.Username, Nome, Cognome, TargaPremio, utente.DataNascita "
                    + "FROM utente JOIN account "
                    + "ON utente.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                return Optional.of(new User(
                        this.getResultSet().getString("Username"),
                        this.getResultSet().getString("Nome"),
                        this.getResultSet().getString("Cognome"),
                        this.getResultSet().getDate("DataNascita").toLocalDate(),
                        this.getResultSet().getBoolean("TargaPremio")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Retrieves the list of all the series.
     *
     * @param userAge The limited age to be respected.
     * @return The list of all the series.
     */
    public List<Serie> getSeries(final int userAge) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * FROM serie "
                    + "WHERE serie.EtaLimite <= ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, userAge);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Serie> series = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Serie serie = new Serie(
                        this.getResultSet().getInt(ID_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getInt(LIMIT_AGE_NAME),
                        this.getResultSet().getString(PLOT_NAME),
                        this.getResultSet().getInt("DurataComplessiva"),
                        this.getResultSet().getInt("NumeroEpisodi")
                );
                series.add(serie);
            }
            return series;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves a film from the database by its ID.
     *
     * @param id The ID of the film.
     * @return An Optional containing the film if found, otherwise empty.
     * @throws IllegalStateException If an SQL exception occurs.
     */
    public Optional<Film> getFilm(final int id) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * FROM film "
                    + "WHERE film.Codice = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, id);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                return Optional.of(new Film(
                        this.getResultSet().getInt(ID_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getInt(LIMIT_AGE_NAME),
                        this.getResultSet().getString(PLOT_NAME),
                        this.getResultSet().getInt("Durata"),
                        this.getResultSet().getInt("CodiceCast")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Retrieves a series from the database by its ID.
     *
     * @param id The ID of the series.
     * @return An Optional containing the series if found, otherwise empty.
     * @throws IllegalStateException If an SQL exception occurs.
     */
    public Optional<Serie> getSerie(final int id) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * FROM serie "
                    + "WHERE serie.Codice = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, id);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                return Optional.of(new Serie(
                        this.getResultSet().getInt(ID_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getInt(LIMIT_AGE_NAME),
                        this.getResultSet().getString(PLOT_NAME),
                        this.getResultSet().getInt("Durata"),
                        this.getResultSet().getInt("NumeroEpisodi")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Retrieves reviews for a user from the database.
     *
     * @param username The username of the user.
     * @return A list of reviews written by the user.
     * @throws IllegalArgumentException If an SQL exception occurs.
     */
    public List<Review> getReviews(final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT\s
                        recensioni_totali.UsernameUtente,
                        recensioni_totali.CodiceFilm,
                        film.Titolo AS TitoloFilm,
                        recensioni_totali.CodiceSerie,
                        serie.Titolo AS TitoloSerie,
                        recensioni_totali.TitoloRecensione,
                        recensioni_totali.DescrizioneRecensione,
                        recensioni_totali.VotoComplessivoRecensione
                    FROM (
                        SELECT\s
                            UsernameUtente,
                            CodiceSerie,
                            NULL AS CodiceFilm,
                            Titolo AS TitoloSerie,
                            NULL AS TitoloFilm,
                            Titolo AS TitoloRecensione,
                            Descrizione AS DescrizioneRecensione,
                            VotoComplessivo AS VotoComplessivoRecensione
                        FROM\s
                            recserie
                        UNION ALL
                        SELECT\s
                            UsernameUtente,
                            NULL AS CodiceSerie,
                            CodiceFilm,
                            NULL AS TitoloSerie,
                            Titolo AS TitoloFilm,
                            Titolo AS TitoloRecensione,
                            Descrizione AS DescrizioneRecensione,
                            VotoComplessivo AS VotoComplessivoRecensione
                        FROM\s
                            recfilm
                    ) AS recensioni_totali
                    LEFT JOIN film ON recensioni_totali.CodiceFilm = film.Codice
                    LEFT JOIN serie ON recensioni_totali.CodiceSerie = serie.Codice
                    WHERE UsernameUtente = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Review review;
                if (this.getResultSet().getInt(ID_FILM_NAME) != NULL
                        && this.getResultSet().getInt(ID_SERIE_NAME) == NULL) {
                    review = new FilmReview(
                            this.getResultSet().getInt(ID_FILM_NAME),
                            this.getResultSet().getString("TitoloFilm"),
                            this.getResultSet().getString("UsernameUtente"),
                            this.getResultSet().getString("TitoloRecensione"),
                            this.getResultSet().getString("DescrizioneRecensione"),
                            this.getResultSet().getInt("VotoComplessivoRecensione")
                    );
                } else if (this.getResultSet().getInt(ID_FILM_NAME) == NULL
                        && this.getResultSet().getInt(ID_SERIE_NAME) != NULL) {
                    review = new SerieReview(
                            this.getResultSet().getInt(ID_SERIE_NAME),
                            this.getResultSet().getString("TitoloSerie"),
                            this.getResultSet().getString("UsernameUtente"),
                            this.getResultSet().getString("TitoloRecensione"),
                            this.getResultSet().getString("DescrizioneRecensione"),
                            this.getResultSet().getInt("VotoComplessivoRecensione")
                    );
                } else {
                    throw new IllegalArgumentException();
                }

                reviews.add(review);
            }
            return reviews;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Removes every genre preference of the user.
     *
     * @param username The username of the user.
     */
    public void clearPreferences(final String username) {
        try {
            final String query = "DELETE FROM preferenze WHERE UsernameUtente = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Gets the preferences of a user.
     *
     * @param username The username whose preferences are to be got.
     * @return A list of genre preferences.
     */
    public List<Genre> getUserPrefs(final String username) {
        try {
            final List<Genre> g = new ArrayList<>();
            final String query =
                    "SELECT Nome,Descrizione,NumeroVisualizzati "
                            + "FROM preferenze "
                            + "join genere on genere.Nome = preferenze.NomeGenere "
                            + "WHERE preferenze.UsernameUtente = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            while (this.getResultSet().next()) {
                g.add(
                        new Genre(this.getResultSet().getString("Nome"),
                                this.getResultSet().getString("Descrizione"),
                                this.getResultSet().getInt("NumeroVisualizzati"))
                );
            }
            return List.copyOf(g);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Adds a preference for the user.
     *
     * @param genre    The genre to add.
     * @param username The username of the user.
     */
    public void addPreference(final String genre, final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO preferenze(NomeGenere, UsernameUtente)"
                    + " VALUES(?, ?)";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, genre);
            this.getPreparedStatement().setString(2, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Adds visualization of a film.
     *
     * @param filmId   The ID of the film to visualize.
     * @param userName The username of the visualizer.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeFilm(final int filmId, final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO visualizzazioni_film(CodiceFilm, UsernameUtente)"
                    + " VALUES(?, ?)";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, filmId);
            this.getPreparedStatement().setString(2, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }


    /**
     * Removes visualization of a film.
     *
     * @param filmId   The ID of the film to visualize.
     * @param userName The username of the visualizer.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetFilm(final int filmId, final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "DELETE FROM visualizzazioni_film WHERE CodiceFilm = ? AND UsernameUtente = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, filmId);
            this.getPreparedStatement().setString(2, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Checks if the film has been viewed or not.
     *
     * @param filmId   The ID of the film.
     * @param userName The username of the user.
     * @return True if the film has been viewed, false otherwise.
     */
    public boolean isFilmViewed(final int filmId, final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT UsernameUtente "
                    + "FROM visualizzazioni_film "
                    + "WHERE CodiceFilm = ? AND UsernameUtente = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, filmId);
            this.getPreparedStatement().setString(2, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return this.getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Retrieves details of films including their cast from the database.
     *
     * @return A map containing films as keys and their corresponding cast as values.
     * @throws IllegalArgumentException If an SQL exception occurs.
     */
    public Map<Film, Cast> getFilmsDetails() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT film.Codice AS CodiceFilm, \n"
                    + "film.Titolo AS TitoloFilm, \n"
                    + "film.EtaLimite AS EtaLimiteFilm, \n"
                    + "film.Trama AS TramaFilm, \n"
                    + "film.Durata AS DurataFilm, \n"
                    + "film.CodiceCast AS CodiceCastFilm,\n"
                    + "membrocast.Codice AS CodiceMembroCast, \n"
                    + "membrocast.Nome AS NomeMembroCast, \n"
                    + "membrocast.Cognome AS CognomeMembroCast, \n"
                    + "membrocast.DataNascita AS DataNascitaMembroCast, \n"
                    + "membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast, \n"
                    + "membrocast.NomeArte AS NomeArteMembroCast, \n"
                    + "membrocast.TipoAttore AS TipoAttoreMembroCast, \n"
                    + "membrocast.TipoRegista AS TipoRegistaMembroCast \n"
                    + "FROM film \n"
                    + "JOIN casting ON film.CodiceCast = casting.Codice \n"
                    + "JOIN partecipazione_cast ON casting.codice = partecipazione_cast.CodiceCast\n"
                    + "JOIN membrocast ON partecipazione_cast.CodiceMembro = membrocast.Codice";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final Map<Film, Cast> detailedFilms = new HashMap<>();
            while (this.getResultSet().next()) {
                final Film film = new Film(
                        this.getResultSet().getInt(ID_FILM_NAME),
                        this.getResultSet().getString("TitoloFilm"),
                        this.getResultSet().getInt("EtaLimiteFilm"),
                        this.getResultSet().getString("TramaFilm"),
                        this.getResultSet().getInt("DurataFilm"),
                        this.getResultSet().getInt("CodiceCastFilm")
                );
                if (!detailedFilms.containsKey(film)) {
                    final Cast newCast = new Cast();
                    newCast.addCastMember(getNewCastMember());
                    detailedFilms.put(film, newCast);
                } else {
                    detailedFilms.get(film).addCastMember(getNewCastMember());
                }
            }
            return Map.copyOf(detailedFilms);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves details of series including their cast from the database.
     *
     * @return A map containing Series as keys and their corresponding Seasons and relative Cast as values.
     * @throws IllegalArgumentException If an SQL exception occurs.
     */
    public Map<Serie, Map<Season, Cast>> getDetailedSeries() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT serie.Codice AS CodiceSerie, "
                    + "stagione.NumeroStagione, "
                    + "episodio.NumeroEpisodio, "
                    + "serie.Titolo AS TitoloSerie, "
                    + "serie.EtaLimite AS EtaLimiteSerie, "
                    + "serie.Trama AS TramaSerie, "
                    + "serie.DurataComplessiva AS DurataComplessivaSerie, "
                    + "serie.NumeroEpisodi AS NumeroEpisodiSerie, "
                    + "stagione.Sunto AS SuntoStagione, "
                    + "episodio.DurataMin AS DurataEpisodio, "
                    + "casting.Nome AS NomeCasting, "
                    + "membrocast.Codice AS CodiceMembroCast, "
                    + "membrocast.Nome AS NomeMembroCast, "
                    + "membrocast.Cognome AS CognomeMembroCast, "
                    + "membrocast.DataNascita AS DataNascitaMembroCast, "
                    + "membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast, "
                    + "membrocast.NomeArte AS NomeArteMembroCast, "
                    + "membrocast.TipoAttore AS TipoAttoreMembroCast, "
                    + "membrocast.TipoRegista AS TipoRegistaMembroCast "
                    + "FROM serie "
                    + "JOIN stagione ON serie.Codice = stagione.CodiceSerie "
                    + "JOIN episodio ON episodio.NumeroStagione = stagione.NumeroStagione "
                    + "AND episodio.CodiceSerie = stagione.CodiceSerie "
                    + "JOIN casting ON casting.Codice = stagione.CodiceCast "
                    + "AND episodio.CodiceSerie = stagione.CodiceSerie "
                    + "JOIN partecipazione_cast ON partecipazione_cast.CodiceCast = casting.Codice "
                    + "JOIN membrocast ON membrocast.Codice = partecipazione_cast.CodiceMembro "
                    + "ORDER BY CodiceSerie";

            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final Map<Serie, Map<Season, Cast>> detailedSeries = new HashMap<>();
            while (this.getResultSet().next()) {
                final Serie serie = new Serie(
                        this.getResultSet().getInt(ID_SERIE_NAME),
                        this.getResultSet().getString("TitoloSerie"),
                        this.getResultSet().getInt("EtaLimiteSerie"),
                        this.getResultSet().getString("TramaSerie"),
                        this.getResultSet().getInt("DurataComplessivaSerie"),
                        this.getResultSet().getInt("NumeroEpisodiSerie")
                );
                final Season season = new Season(
                        this.getResultSet().getInt("NumeroStagione"),
                        this.getResultSet().getString("SuntoStagione")
                );
                final Episode episode = new Episode(
                        this.getResultSet().getInt("NumeroEpisodio"),
                        this.getResultSet().getInt("DurataEpisodio")
                );
                final CastMember castMember = getNewCastMember();
                if (!detailedSeries.containsKey(serie)) {
                    final Map<Season, Cast> seasonCastMap = new HashMap<>();
                    final Cast newCast = new Cast();
                    newCast.addCastMember(castMember);
                    season.addEpisode(episode);
                    seasonCastMap.put(season, newCast);
                    detailedSeries.put(serie, seasonCastMap);
                } else {
                    if (!detailedSeries.get(serie).containsKey(season)) {
                        final Cast newCast = new Cast();
                        newCast.addCastMember(castMember);
                        season.addEpisode(episode);
                        detailedSeries.get(serie).put(season, newCast);
                    } else {
                        if (detailedSeries.get(serie)
                                .keySet()
                                .stream()
                                .filter(s -> s.equals(season))
                                .findAny()
                                .map(s -> !s.getEpisodes().contains(episode)).orElse(false)) {
                            detailedSeries.get(serie)
                                    .keySet()
                                    .stream()
                                    .filter(s -> s.equals(season)).findAny().ifPresent(s -> s.addEpisode(episode));
                        }
                        detailedSeries.get(serie).get(season).addCastMember(castMember);
                    }
                }
            }
            return Map.copyOf(detailedSeries);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }


    private CastMember getNewCastMember() throws SQLException {
        if (this.getResultSet().getBoolean("TipoAttoreMembroCast")
                && !this.getResultSet().getBoolean("TipoRegistaMembroCast")) {
            return new Actor(
                    this.getResultSet().getInt("CodiceMembroCast"),
                    this.getResultSet().getString("NomeMembroCast"),
                    this.getResultSet().getString("CognomeMembroCast"),
                    this.getResultSet().getDate("DataNascitaMembroCast").toLocalDate(),
                    this.getResultSet().getDate("DataDebuttoCarrieraMembroCast").toLocalDate(),
                    this.getResultSet().getString("NomeArteMembroCast")
            );
        } else if (!this.getResultSet().getBoolean("TipoAttoreMembroCast")
                && this.getResultSet().getBoolean("TipoRegistaMembroCast")) {
            return new Director(
                    this.getResultSet().getInt("CodiceMembroCast"),
                    this.getResultSet().getString("NomeMembroCast"),
                    this.getResultSet().getString("CognomeMembroCast"),
                    this.getResultSet().getDate("DataNascitaMembroCast").toLocalDate(),
                    this.getResultSet().getDate("DataDebuttoCarrieraMembroCast").toLocalDate(),
                    this.getResultSet().getString("NomeArteMembroCast")
            );
        }
        throw new IllegalArgumentException();
    }


}
