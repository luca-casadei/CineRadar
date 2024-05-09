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
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.review.SeriesReview;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.User;

import java.sql.Date;
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
    private static final String ID_SERIES_NAME = "CodiceSerie";
    private static final String USERNAME_NAME = "UsernameUtente";
    private static final String FOUR_VALUES = "VALUES (?,?,?,?)";
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final int FOURTH_PARAMETER = 4;
    private static final int FIFTH_PARAMETER = 5;

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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, userAge);
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
            this.getPreparedStatement().setString(FIRST_PARAMETER, username);
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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, userAge);
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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
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
     * Retrieves reviews made by a user from the database.
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
            this.getPreparedStatement().setString(FIRST_PARAMETER, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Review review;
                if (this.getResultSet().getInt(ID_FILM_NAME) != NULL
                        && this.getResultSet().getInt(ID_SERIES_NAME) == NULL) {
                    review = new FilmReview(
                            this.getResultSet().getInt(ID_FILM_NAME),
                            this.getResultSet().getString("TitoloFilm"),
                            this.getResultSet().getString(USERNAME_NAME),
                            this.getResultSet().getString("TitoloRecensione"),
                            this.getResultSet().getString("DescrizioneRecensione"),
                            this.getResultSet().getInt("VotoComplessivoRecensione")
                    );
                } else if (this.getResultSet().getInt(ID_FILM_NAME) == NULL
                        && this.getResultSet().getInt(ID_SERIES_NAME) != NULL) {
                    review = new SeriesReview(
                            this.getResultSet().getInt(ID_SERIES_NAME),
                            this.getResultSet().getString("TitoloSerie"),
                            this.getResultSet().getString(USERNAME_NAME),
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
     * Gets a list of reviews for a single series.
     *
     * @param seriesId The unique ID of the series.
     * @return A list of reviews.
     */
    public List<Review> getSeriesReviews(final int seriesId) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * from recserie WHERE recserie.UsernameUtente = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, seriesId);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                reviews.add(new Review(
                        this.getResultSet().getString(USERNAME_NAME),
                        this.getResultSet().getString("Titolo"),
                        this.getResultSet().getString("Descrizione"),
                        this.getResultSet().getInt("VotoComplessivo")
                ));
            }
            return List.copyOf(reviews);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves reviews of a particular film.
     *
     * @param filmId The id of the specific film.
     * @return A list of reviews of the given film.
     * @throws IllegalArgumentException If an SQL exception occurs.
     */
    public List<Review> getFilmReviews(final int filmId) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT film.Codice AS CodiceFilm,
                    film.Titolo AS TitoloFilm,
                    recfilm.UsernameUtente,
                    recfilm.Titolo AS TitoloRecensione,
                    recfilm.Descrizione AS DescrizioneRecensione,
                    recfilm.VotoComplessivo AS VotoComplessivoRecensione
                    FROM recfilm
                    JOIN film ON recfilm.CodiceFilm = film.Codice
                    WHERE CodiceFilm = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Review review;
                review = new FilmReview(
                        this.getResultSet().getInt(ID_FILM_NAME),
                        this.getResultSet().getString("TitoloFilm"),
                        this.getResultSet().getString(USERNAME_NAME),
                        this.getResultSet().getString("TitoloRecensione"),
                        this.getResultSet().getString("DescrizioneRecensione"),
                        this.getResultSet().getInt("VotoComplessivoRecensione")
                );
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
            this.getPreparedStatement().setString(FIRST_PARAMETER, username);
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
            this.getPreparedStatement().setString(FIRST_PARAMETER, username);
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
            this.getPreparedStatement().setString(FIRST_PARAMETER, genre);
            this.getPreparedStatement().setString(SECOND_PARAMETER, username);
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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
            this.getPreparedStatement().setString(SECOND_PARAMETER, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Adds visualization of an episode.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @param userName  The username of the visualizer.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean visualizeEpisode(final int seriesId, final int seasonId, final int episodeId, final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO visualizzazioni_episodio(UsernameUtente, "
                    + "CodiceSerie, "
                    + "NumeroEpisodio, "
                    + "NumeroStagione, "
                    + "DataVisualizzazione) "
                    + "VALUES(?, ?, ?, ?, ?)";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(FIRST_PARAMETER, userName);
            this.getPreparedStatement().setInt(SECOND_PARAMETER, seriesId);
            this.getPreparedStatement().setInt(THIRD_PARAMETER, episodeId);
            this.getPreparedStatement().setInt(FOURTH_PARAMETER, seasonId);
            this.getPreparedStatement().setDate(FIFTH_PARAMETER, new Date(System.currentTimeMillis()));
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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
            this.getPreparedStatement().setString(SECOND_PARAMETER, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Remove a visualization of an episode.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @param userName  The username of the visualizer.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean forgetEpisode(final int seriesId, final int seasonId, final int episodeId, final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "DELETE FROM visualizzazioni_episodio WHERE "
                    + "UsernameUtente = ? AND "
                    + "CodiceSerie = ? AND "
                    + "NumeroEpisodio = ? AND "
                    + "NumeroStagione = ?";
            forgetCommons(seriesId, seasonId, episodeId, userName, query);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    private void forgetCommons(final int seriesId,
                               final int seasonId,
                               final int episodeId,
                               final String userName,
                               final String query) throws SQLException {
        this.setPreparedStatement(this.getConnection().prepareStatement(query));
        this.getPreparedStatement().setString(FIRST_PARAMETER, userName);
        this.getPreparedStatement().setInt(SECOND_PARAMETER, seriesId);
        this.getPreparedStatement().setInt(THIRD_PARAMETER, episodeId);
        this.getPreparedStatement().setInt(FOURTH_PARAMETER, seasonId);
        this.setResultSet(this.getPreparedStatement().executeQuery());
    }

    /**
     * Adds a review to the specified series.
     *
     * @param seriesId The id of the series.
     * @param username The username of the reviewer.
     * @param title    The title of the review (caption).
     * @param desc     Full description of the review.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean reviewSeries(final int seriesId,
                                final String username,
                                final String title,
                                final String desc) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO recserie(CodiceSerie, UsernameUtente, Titolo, Descrizione) "
                    + FOUR_VALUES;
            return commonReviewOperations(seriesId, username, title, desc, query);
        } catch (SQLException ex) {
            return false;
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
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO valutazione_film "
                    + "(UsernameUtenteValutato, CodiceRecFilm, UsernameUtente, positiva)"
                    + FOUR_VALUES;
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(FIRST_PARAMETER, recUsername);
            this.getPreparedStatement().setString(SECOND_PARAMETER, username);
            this.getPreparedStatement().setInt(THIRD_PARAMETER, filmRecId);
            this.getPreparedStatement().setBoolean(FOURTH_PARAMETER, positive);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Adds a review to the specified series.
     *
     * @param filmId   The id of the film.
     * @param username The username of the reviewer.
     * @param title    The title of the review (caption).
     * @param desc     Full description of the review.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean reviewFilm(final int filmId,
                              final String username,
                              final String title,
                              final String desc) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO recfilm(CodiceFilm, UsernameUtente, Titolo, Descrizione) "
                    + FOUR_VALUES;
            return commonReviewOperations(filmId, username, title, desc, query);
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Calculates the average of the votes from the sections.
     *
     * @param filmId   The ID of the movie.
     * @param username The username of the reviewer.
     * @return True if the operation succeeds, false otherwise.
     */
    public boolean averageSectionFilmVote(final int filmId, final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    UPDATE recfilm
                    SET VotoComplessivo = ( SELECT AVG(Voto) FROM sezionamento_film
                    WHERE sezionamento_film.UsernameUtente = ?
                    AND sezionamento_film.CodiceRecFilm = ? )
                    WHERE recfilm.UsernameUtente = ?
                    AND recfilm.CodiceFilm = ?""";
            return averageCommons(filmId, username, query);
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean averageCommons(final int id,
                                   final String username,
                                   final String query) throws SQLException {
        this.setPreparedStatement(this.getConnection().prepareStatement(query));
        this.getPreparedStatement().setString(FIRST_PARAMETER, username);
        this.getPreparedStatement().setInt(SECOND_PARAMETER, id);
        this.getPreparedStatement().setString(THIRD_PARAMETER, username);
        this.getPreparedStatement().setInt(FOURTH_PARAMETER, id);
        this.setResultSet(this.getPreparedStatement().executeQuery());
        return true;
    }

    /**
     * Calculates the average of the votes from the sections.
     *
     * @param seriesId The ID of the series.
     * @param username The username of the reviewer.
     * @return True if the operation succeeds, false otherwise.
     */
    public boolean averageSectionSeriesVote(final int seriesId, final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    UPDATE recserie
                    SET VotoComplessivo = ( SELECT AVG(Voto) FROM sezionamento_serie
                    WHERE sezionamento_serie.UsernameUtente = ?
                    AND sezionamento_serie.CodiceRecSerie = ? )
                    WHERE recserie.UsernameUtente = ?
                    AND recserie.CodiceSerie = ?""";
            return averageCommons(seriesId, username, query);
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean commonReviewOperations(final int id,
                                           final String username,
                                           final String title,
                                           final String desc,
                                           final String query) throws SQLException {
        this.setPreparedStatement(this.getConnection().prepareStatement(query));
        this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
        this.getPreparedStatement().setString(SECOND_PARAMETER, username);
        this.getPreparedStatement().setString(THIRD_PARAMETER, title);
        this.getPreparedStatement().setString(FOURTH_PARAMETER, desc);
        this.setResultSet(this.getPreparedStatement().executeQuery());
        return true;
    }

    /**
     * Retrieves the list of all sections.
     *
     * @return The list of all sections.
     */
    public List<Section> getSections() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * FROM sezione";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Section> sections = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Section section = new Section(
                        this.getResultSet().getString("Nome"),
                        this.getResultSet().getString("Dettaglio")
                );
                sections.add(section);
            }
            return List.copyOf(sections);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }


    /**
     * Sets the sections of the film review.
     *
     * @param sectName     The name of the section to review.
     * @param username     The username of the reviewer.
     * @param multimediaId The code of the film.
     * @param score        The score of the section.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean addFilmReviewSections(final String sectName,
                                         final String username,
                                         final int multimediaId,
                                         final int score) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO sezionamento_film (NomeSezione, UsernameUtente, CodiceRecFilm, Voto) "
                    + FOUR_VALUES;
            return commonSectionQueries(sectName, username, multimediaId, score, query);
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean commonSectionQueries(final String sectName,
                                         final String username,
                                         final int multimediaId,
                                         final int score,
                                         final String query) throws SQLException {
        this.setPreparedStatement(this.getConnection().prepareStatement(query));
        this.getPreparedStatement().setString(FIRST_PARAMETER, sectName);
        this.getPreparedStatement().setString(SECOND_PARAMETER, username);
        this.getPreparedStatement().setInt(THIRD_PARAMETER, multimediaId);
        this.getPreparedStatement().setInt(FOURTH_PARAMETER, score);
        this.setResultSet(this.getPreparedStatement().executeQuery());
        return true;
    }

    /**
     * Sets the sections of the series review.
     *
     * @param sectName     The name of the section to review.
     * @param username     The username of the reviewer.
     * @param multimediaId The code of the review.
     * @param score        The score of the section.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean addSeriesReviewSection(final String sectName,
                                          final String username,
                                          final int multimediaId,
                                          final int score) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO sezionamento_serie (NomeSezione, UsernameUtente, CodiceRecSerie, Voto) "
                    + FOUR_VALUES;
            return commonSectionQueries(sectName, username, multimediaId, score, query);
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Gets the viewed episodes of a series.
     *
     * @param seriesCode   The series code.
     * @param seasonNumber The season number.
     * @param userName     The username.
     * @return A list of episodes.
     */
    public List<Episode> getViewedEpisodes(final int seriesCode,
                                           final int seasonNumber,
                                           final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                         SELECT episodio.* FROM episodio
                         JOIN visualizzazioni_episodio\s
                             ON visualizzazioni_episodio.NumeroEpisodio = episodio.NumeroEpisodio
                             AND visualizzazioni_episodio.NumeroStagione = episodio.NumeroStagione
                             AND visualizzazioni_episodio.CodiceSerie = episodio.CodiceSerie
                         WHERE visualizzazioni_episodio.UsernameUtente = ?
                             AND visualizzazioni_episodio.CodiceSerie = ?
                             AND visualizzazioni_episodio.NumeroStagione = ?
                    """;
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(THIRD_PARAMETER, seasonNumber);
            this.getPreparedStatement().setInt(SECOND_PARAMETER, seriesCode);
            this.getPreparedStatement().setString(FIRST_PARAMETER, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Episode> eps = new ArrayList<>();
            while (this.getResultSet().next()) {
                eps.add(
                        new Episode(this.getResultSet().getInt("NumeroEpisodio"),
                                this.getResultSet().getInt("CodiceSerie"),
                                this.getResultSet().getInt("NumeroStagione"),
                                this.getResultSet().getInt("DurataMin"))
                );
            }
            return List.copyOf(eps);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
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
            this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
            this.getPreparedStatement().setString(SECOND_PARAMETER, userName);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return this.getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Checks if the episode has been viewed or not.
     *
     * @param seriesId  The specific series id.
     * @param seasonId  The specific season id.
     * @param episodeId The specific episode id.
     * @param userName  The username of the user.
     * @return True if the film has been viewed, false otherwise.
     */
    public boolean isEpisodeViewed(final int seriesId, final int seasonId, final int episodeId, final String userName) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT UsernameUtente "
                    + "FROM visualizzazioni_episodio "
                    + "WHERE "
                    + "UsernameUtente = ? AND "
                    + "CodiceSerie = ? AND "
                    + "NumeroEpisodio = ? AND "
                    + "NumeroStagione = ?";
            forgetCommons(seriesId, seasonId, episodeId, userName, query);
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
    public Map<Film, Cast> getDetailedFilms() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT film.Codice AS CodiceFilm,\s
                    film.Titolo AS TitoloFilm,\s
                    film.EtaLimite AS EtaLimiteFilm,\s
                    film.Trama AS TramaFilm,\s
                    film.Durata AS DurataFilm,\s
                    film.CodiceCast AS CodiceCastFilm,
                    membrocast.Codice AS CodiceMembroCast,\s
                    membrocast.Nome AS NomeMembroCast,\s
                    membrocast.Cognome AS CognomeMembroCast,\s
                    membrocast.DataNascita AS DataNascitaMembroCast,\s
                    membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast,\s
                    membrocast.NomeArte AS NomeArteMembroCast,\s
                    membrocast.TipoAttore AS TipoAttoreMembroCast,\s
                    membrocast.TipoRegista AS TipoRegistaMembroCast\s
                    FROM film\s
                    JOIN casting ON film.CodiceCast = casting.Codice\s
                    JOIN partecipazione_cast ON casting.codice = partecipazione_cast.CodiceCast
                    JOIN membrocast ON partecipazione_cast.CodiceMembro = membrocast.Codice""";
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
    public List<Serie> getDetailedSeries() {
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
            final List<Serie> detailedSeries = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Serie serie = new Serie(
                        this.getResultSet().getInt(ID_SERIES_NAME),
                        this.getResultSet().getString("TitoloSerie"),
                        this.getResultSet().getInt("EtaLimiteSerie"),
                        this.getResultSet().getString("TramaSerie"),
                        this.getResultSet().getInt("DurataComplessivaSerie"),
                        this.getResultSet().getInt("NumeroEpisodiSerie")
                );
                final Season season = new Season(
                        this.getResultSet().getInt(ID_SERIES_NAME),
                        this.getResultSet().getInt("NumeroStagione"),
                        this.getResultSet().getString("SuntoStagione")
                );
                final Episode episode = new Episode(
                        this.getResultSet().getInt(ID_SERIES_NAME),
                        this.getResultSet().getInt("NumeroStagione"),
                        this.getResultSet().getInt("NumeroEpisodio"),
                        this.getResultSet().getInt("DurataEpisodio")
                );
                final CastMember castMember = getNewCastMember();

                if (!detailedSeries.contains(serie)) {
                    season.addEpisode(episode);
                    season.addCastMember(castMember);
                    serie.addSeason(season);
                    detailedSeries.add(serie);
                } else {
                    if (!detailedSeries.get(detailedSeries.indexOf(serie))
                            .getSeasons()
                            .contains(season)) {
                        season.addCastMember(castMember);
                        detailedSeries.get(detailedSeries.indexOf(serie)).addSeason(season);
                    } else {
                        if (!detailedSeries.get(detailedSeries.indexOf(serie))
                                .getSeason(season)
                                .getEpisodes()
                                .contains(episode)) {
                            detailedSeries.get(detailedSeries.indexOf(serie)).getSeason(season).addCastMember(castMember);
                            detailedSeries.get(detailedSeries.indexOf(serie)).getSeason(season).addEpisode(episode);
                        }
                    }
                }
            }
            return List.copyOf(detailedSeries);
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
