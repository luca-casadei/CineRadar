package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.FullSeriesReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.review.SeriesReview;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.User;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private static final String NAME_NAME = "Nome";
    private static final String DESC_NAME = "Descrizione";
    private static final String VOTOC_NAME = "VotoComplessivo";
    private static final String FOUR_VALUES = "VALUES (?,?,?,?)";
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final int FOURTH_PARAMETER = 4;
    private static final int FIFTH_PARAMETER = 5;
    private static final String NUM_VIEWS_NAME = "NumeroVisualizzati";
    private static final String GENRE_NAME = "NomeGenere";

    /**
     * Retrieves the list of all films.
     *
     * @param age The limited age to be respected.
     * @return The list of all films.
     */
    public List<Film> getFilms(final int age) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String filmQuery = "SELECT * FROM film WHERE film.EtaLimite <= ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(filmQuery));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, age);
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

            for (final Film film : films) {
                final int filmId = film.getFilmId();
                final String genreQuery = "SELECT NomeGenere, Descrizione, NumeroVisualizzati FROM categorizzazione_film "
                        + "JOIN genere ON categorizzazione_film.NomeGenere = genere.Nome WHERE CodiceFilm = ?";
                this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
                this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
                this.setResultSet(this.getPreparedStatement().executeQuery());

                while (this.getResultSet().next()) {
                    film.addGenre(new Genre(
                            this.getResultSet().getString(GENRE_NAME),
                            this.getResultSet().getString(DESC_NAME),
                            this.getResultSet().getInt(NUM_VIEWS_NAME)
                    ));
                }
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
                        this.getResultSet().getString(NAME_NAME),
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
     * @param age The limited age to be respected.
     * @return The list of all the series.
     */
    public List<Serie> getSeries(final int age) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String seriesQuery = "SELECT * FROM serie WHERE serie.EtaLimite <= ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(seriesQuery));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, age);
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

            final String genreQuery = "SELECT NomeGenere, Descrizione, NumeroVisualizzati, CodiceSerie "
                    + "FROM categorizzazione_serie "
                    + "JOIN genere ON categorizzazione_serie.NomeGenere = genere.Nome WHERE CodiceSerie = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
            for (final Serie serie : series) {
                this.getPreparedStatement().setInt(FIRST_PARAMETER, serie.getSeriesId());
                this.setResultSet(this.getPreparedStatement().executeQuery());
                while (this.getResultSet().next()) {
                    serie.addGenre(new Genre(
                            this.getResultSet().getString(GENRE_NAME),
                            this.getResultSet().getString(DESC_NAME),
                            this.getResultSet().getInt(NUM_VIEWS_NAME)
                    ));
                }
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
            final String query = "SELECT * FROM film WHERE film.Codice = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                final Film film = new Film(
                        this.getResultSet().getInt(ID_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getInt(LIMIT_AGE_NAME),
                        this.getResultSet().getString(PLOT_NAME),
                        this.getResultSet().getInt("Durata"),
                        this.getResultSet().getInt("CodiceCast")
                );
                final String genreQuery = "SELECT NomeGenere, Descrizione, NumeroVisualizzati FROM categorizzazione_film "
                        + "JOIN genere ON categorizzazione_film.NomeGenere = genere.Nome WHERE CodiceFilm = ?";
                this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
                this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
                this.setResultSet(this.getPreparedStatement().executeQuery());
                while (this.getResultSet().next()) {
                    film.addGenre(new Genre(
                            this.getResultSet().getString(GENRE_NAME),
                            this.getResultSet().getString(DESC_NAME),
                            this.getResultSet().getInt(NUM_VIEWS_NAME)
                    ));
                }
                return Optional.of(film);
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
            final String serieQuery = "SELECT * FROM serie WHERE serie.Codice = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(serieQuery));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                final Serie serie = new Serie(
                        this.getResultSet().getInt(ID_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getInt(LIMIT_AGE_NAME),
                        this.getResultSet().getString(PLOT_NAME),
                        this.getResultSet().getInt("DurataComplessiva"),
                        this.getResultSet().getInt("NumeroEpisodi")
                );

                final String genreQuery = "SELECT NomeGenere, Descrizione, NumeroVisualizzati FROM categorizzazione_serie "
                        + "JOIN genere ON categorizzazione_serie.NomeGenere = genere.Nome WHERE CodiceSerie = ?";
                this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
                this.getPreparedStatement().setInt(FIRST_PARAMETER, id);
                this.setResultSet(this.getPreparedStatement().executeQuery());

                while (this.getResultSet().next()) {
                    serie.addGenre(new Genre(
                            this.getResultSet().getString(GENRE_NAME),
                            this.getResultSet().getString(DESC_NAME),
                            this.getResultSet().getInt(NUM_VIEWS_NAME)
                    ));
                }

                return Optional.of(serie);
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
                            this.getResultSet().getDouble("VotoComplessivoRecensione")
                    );
                } else if (this.getResultSet().getInt(ID_FILM_NAME) == NULL
                        && this.getResultSet().getInt(ID_SERIES_NAME) != NULL) {
                    review = new SeriesReview(
                            this.getResultSet().getInt(ID_SERIES_NAME),
                            this.getResultSet().getString("TitoloSerie"),
                            this.getResultSet().getString(USERNAME_NAME),
                            this.getResultSet().getString("TitoloRecensione"),
                            this.getResultSet().getString("DescrizioneRecensione"),
                            this.getResultSet().getDouble("VotoComplessivoRecensione")
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
            final String query = """
                    SELECT recserie.*, serie.Titolo AS TitoloSerie
                    FROM recserie JOIN serie ON recserie.CodiceSerie = serie.Codice
                    WHERE recserie.CodiceSerie = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, seriesId);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                reviews.add(new SeriesReview(
                        this.getResultSet().getInt(ID_SERIES_NAME),
                        this.getResultSet().getString(TITLE_NAME + "Serie"),
                        this.getResultSet().getString(USERNAME_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getString(DESC_NAME),
                        this.getResultSet().getInt(VOTOC_NAME)
                ));
            }
            return List.copyOf(reviews);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * The full series reviews.
     *
     * @param seriesId The ID of the series.
     * @param username The username of the author.
     * @return An optional of the full series review.
     */
    public Optional<FullSeriesReview> getFullSeriesReview(final int seriesId, final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT recserie.*, serie.Titolo AS TitoloSerie
                    FROM recserie JOIN serie ON recserie.CodiceSerie = serie.Codice
                    WHERE recserie.UsernameUtente = ?
                    AND recserie.CodiceSerie = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(FIRST_PARAMETER, username);
            this.getPreparedStatement().setInt(SECOND_PARAMETER, seriesId);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                final FullSeriesReview rev = new FullSeriesReview(
                        this.getResultSet().getInt(ID_SERIES_NAME),
                        this.getResultSet().getString("TitoloSerie"),
                        this.getResultSet().getString("UsernameUtente"),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getString(DESC_NAME),
                        this.getResultSet().getInt(VOTOC_NAME)
                );
                final String secondQuery = """
                        SELECT * FROM sezionamento_serie
                        JOIN sezione ON sezionamento_serie.NomeSezione = sezione.Nome
                        WHERE sezionamento_serie.UsernameUtente = ?
                        AND sezionamento_serie.CodiceRecSerie = ?
                        """;
                this.setPreparedStatement(this.getConnection().prepareStatement(secondQuery));
                this.getPreparedStatement().setString(FIRST_PARAMETER, username);
                this.getPreparedStatement().setInt(SECOND_PARAMETER, seriesId);
                this.setResultSet(this.getPreparedStatement().executeQuery());
                while (this.getResultSet().next()) {
                    rev.addSection(new ReviewSection(
                            this.getResultSet().getInt("CodiceRecSerie"),
                            new Section(
                                    this.getResultSet().getString(NAME_NAME),
                                    this.getResultSet().getString("Dettaglio")
                            ),
                            this.getResultSet().getInt("Voto")
                    ));
                }
                return Optional.of(rev);
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Gets a full review given its username and ID.
     *
     * @param filmId   The ID of the reviewed film.
     * @param username The username of the reviewed film.
     * @return A full review.
     */
    public Optional<FullFilmReview> getFullFilmReview(final int filmId, final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT recfilm.*, film.Titolo AS TitoloFilm
                    FROM recfilm JOIN film ON recfilm.CodiceFilm = film.Codice
                    WHERE recfilm.UsernameUtente = ?
                    AND recfilm.CodiceFilm = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(FIRST_PARAMETER, username);
            this.getPreparedStatement().setInt(SECOND_PARAMETER, filmId);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                final FullFilmReview rev = new FullFilmReview(
                        this.getResultSet().getInt("CodiceFilm"),
                        this.getResultSet().getString("TitoloFilm"),
                        this.getResultSet().getString("UsernameUtente"),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getString(DESC_NAME),
                        this.getResultSet().getInt(VOTOC_NAME)
                );
                final String secondQuery = """
                        SELECT * FROM sezionamento_film
                        JOIN sezione ON sezionamento_film.NomeSezione = sezione.Nome
                        WHERE sezionamento_film.UsernameUtente = ?
                        AND sezionamento_film.CodiceRecFilm = ?
                        """;
                this.setPreparedStatement(this.getConnection().prepareStatement(secondQuery));
                this.getPreparedStatement().setString(FIRST_PARAMETER, username);
                this.getPreparedStatement().setInt(SECOND_PARAMETER, filmId);
                this.setResultSet(this.getPreparedStatement().executeQuery());
                while (this.getResultSet().next()) {
                    rev.addSection(new ReviewSection(
                            this.getResultSet().getInt("CodiceRecFilm"),
                            new Section(
                                    this.getResultSet().getString(NAME_NAME),
                                    this.getResultSet().getString("Dettaglio")
                            ),
                            this.getResultSet().getInt("Voto")
                    ));
                }
                return Optional.of(rev);
            } else {
                return Optional.empty();
            }
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
        final String query = """
                SELECT recfilm.*, film.Titolo AS TitoloFilm\s
                FROM recfilm JOIN film ON recfilm.CodiceFilm = film.Codice\s
                WHERE recfilm.CodiceFilm = ?""";
        try {
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Review review;
                review = new FilmReview(
                        this.getResultSet().getInt(ID_FILM_NAME),
                        this.getResultSet().getString(TITLE_NAME + "Film"),
                        this.getResultSet().getString(USERNAME_NAME),
                        this.getResultSet().getString(TITLE_NAME),
                        this.getResultSet().getString(DESC_NAME),
                        this.getResultSet().getInt(VOTOC_NAME)
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
                        new Genre(this.getResultSet().getString(NAME_NAME),
                                this.getResultSet().getString(DESC_NAME),
                                this.getResultSet().getInt(NUM_VIEWS_NAME))
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
     * Adds a content request to the database.
     *
     * @param type        The type of the content request (0 for movie, 1 for TV series).
     * @param title       The title of the content request.
     * @param releaseYear The release year of the content request.
     * @param description The description of the content request.
     * @param username    The username of the user making the request.
     * @return True if the operation was successful, false otherwise.
     * @throws IllegalArgumentException If an SQL exception occurs.
     */
    public boolean addRequest(final boolean type, final String title, final LocalDate releaseYear,
                              final String description, final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO richiesta(Tipo, Titolo, AnnoUscita, Descrizione, UsernameUtente) "
                    + "VALUES (?, ?, ?, ?, ?)";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setBoolean(FIRST_PARAMETER, type);
            this.getPreparedStatement().setString(SECOND_PARAMETER, title);
            this.getPreparedStatement().setDate(THIRD_PARAMETER, Date.valueOf(releaseYear));
            this.getPreparedStatement().setString(FOURTH_PARAMETER, description);
            this.getPreparedStatement().setString(FIFTH_PARAMETER, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return true;
        } catch (SQLException ex) {
            return false;
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
            return updateFilmGenreViews(filmId, true);
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
            final int affected = this.getPreparedStatement().executeUpdate();
            return affected >= 0 && updateSeriesGenreViews(seriesId, true);
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
            return updateFilmGenreViews(filmId, false);
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean updateSeriesGenreViews(final int seriesId, final boolean inc) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "UPDATE genere "
                    + "SET NumeroVisualizzati = NumeroVisualizzati " + (inc ? "+ 1 " : "- 1 ")
                    + "WHERE Nome IN ( "
                    + "SELECT categorizzazione_serie.NomeGenere "
                    + "FROM categorizzazione_serie "
                    + "WHERE categorizzazione_serie.CodiceSerie = 1 )";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, seriesId);
            return this.getPreparedStatement().executeUpdate() >= 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean updateFilmGenreViews(final int filmId, final boolean inc) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "UPDATE genere "
                    + "SET NumeroVisualizzati = NumeroVisualizzati " + (inc ? "+ 1 " : "- 1 ")
                    + "WHERE Nome IN ( "
                    + "SELECT categorizzazione_film.NomeGenere "
                    + "FROM categorizzazione_film "
                    + "WHERE categorizzazione_film.CodiceFilm = 1 )";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(FIRST_PARAMETER, filmId);
            return this.getPreparedStatement().executeUpdate() >= 0;
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
            return updateSeriesGenreViews(seriesId, false);
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
     * @param serieRecId  The ID of the review.
     * @param positive    If the review is positive or negative.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean evaluateSerieRec(final String recUsername,
                                    final String username,
                                    final int serieRecId,
                                    final boolean positive) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "INSERT INTO valutazione_serie "
                    + "(UsernameUtenteValutato, CodiceRecSerie, UsernameUtente, Positiva) "
                    + FOUR_VALUES
                    + " ON DUPLICATE KEY UPDATE Positiva = VALUES(Positiva)";
            return evaluationCommons(recUsername, username, serieRecId, positive, query);
        } catch (SQLException ex) {
            return false;
        }
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
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT * FROM valutazione_serie
                    WHERE UsernameUtenteValutato = ?
                    AND CodiceRecSerie = ?
                    AND UsernameUtente = ?
                    """;
            return findMediaCommons(recUsername, username, serieRecId, query);
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private Optional<Boolean> findMediaCommons(final String recUsername,
                                               final String username,
                                               final int serieRecId,
                                               final String query) throws SQLException {
        this.setPreparedStatement(this.getConnection().prepareStatement(query));
        this.getPreparedStatement().setString(FIRST_PARAMETER, recUsername);
        this.getPreparedStatement().setInt(SECOND_PARAMETER, serieRecId);
        this.getPreparedStatement().setString(THIRD_PARAMETER, username);
        this.setResultSet(this.getPreparedStatement().executeQuery());
        if (this.getResultSet().next()) {
            return Optional.of(this.getResultSet().getBoolean("Positiva"));
        } else {
            return Optional.empty();
        }
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
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    DELETE FROM valutazione_serie WHERE\s
                    UsernameUtenteValutato = ?\s
                    AND CodiceRecFilm = ?
                    AND UsernameUtente = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, usernameOwnerReview);
            this.getPreparedStatement().setInt(2, idSerie);
            this.getPreparedStatement().setString(3, username);
            this.getPreparedStatement().executeUpdate();
            return true;
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
                    + "(UsernameUtenteValutato, CodiceRecFilm, UsernameUtente, Positiva) "
                    + FOUR_VALUES
                    + " ON DUPLICATE KEY UPDATE Positiva = VALUES(Positiva)";
            return evaluationCommons(recUsername, username, filmRecId, positive, query);
        } catch (SQLException ex) {
            return false;
        }
    }

    private boolean evaluationCommons(final String recUsername,
                                      final String username,
                                      final int filmRecId,
                                      final boolean positive,
                                      final String query) throws SQLException {
        this.setPreparedStatement(this.getConnection().prepareStatement(query));
        this.getPreparedStatement().setString(1, recUsername);
        this.getPreparedStatement().setInt(2, filmRecId);
        this.getPreparedStatement().setString(3, username);
        this.getPreparedStatement().setBoolean(4, positive);
        this.getPreparedStatement().executeUpdate();
        return true;
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
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                       SELECT * FROM valutazione_film
                       WHERE UsernameUtenteValutato = ?
                       AND CodiceRecFilm = ?
                       AND UsernameUtente = ?
                    """;
            return findMediaCommons(recUsername, username, filmRecId, query);
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
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
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    DELETE FROM valutazione_film WHERE
                    UsernameUtenteValutato = ?
                    AND CodiceRecFilm = ?
                    AND UsernameUtente = ?""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, usernameOwnerReview);
            this.getPreparedStatement().setInt(2, idFilm);
            this.getPreparedStatement().setString(3, username);
            this.getPreparedStatement().executeUpdate();
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
                        this.getResultSet().getString(NAME_NAME),
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
                                this.getResultSet().getInt(ID_SERIES_NAME),
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
                    SELECT film.Codice AS CodiceFilm,
                    film.Titolo AS TitoloFilm,
                    film.EtaLimite AS EtaLimiteFilm,
                    film.Trama AS TramaFilm,
                    film.Durata AS DurataFilm,
                    film.CodiceCast AS CodiceCastFilm,
                    membrocast.Codice AS CodiceMembroCast,
                    membrocast.Nome AS NomeMembroCast,
                    membrocast.Cognome AS CognomeMembroCast,
                    membrocast.DataNascita AS DataNascitaMembroCast,
                    membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast,
                    membrocast.NomeArte AS NomeArteMembroCast,
                    membrocast.TipoAttore AS TipoAttoreMembroCast,
                    membrocast.TipoRegista AS TipoRegistaMembroCast
                    FROM film
                    JOIN casting ON film.CodiceCast = casting.Codice
                    JOIN partecipazione_cast ON casting.codice = partecipazione_cast.CodiceCast
                    JOIN membrocast ON partecipazione_cast.CodiceMembro = membrocast.Codice""";

            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());

            final Map<Integer, Film> filmsMap = new HashMap<>();
            final Map<Integer, Cast> castsMap = new HashMap<>();

            while (this.getResultSet().next()) {
                final int filmCode = this.getResultSet().getInt(ID_FILM_NAME);

                if (!filmsMap.containsKey(filmCode)) {
                    final Film film = new Film(
                            filmCode,
                            this.getResultSet().getString("TitoloFilm"),
                            this.getResultSet().getInt("EtaLimiteFilm"),
                            this.getResultSet().getString("TramaFilm"),
                            this.getResultSet().getInt("DurataFilm"),
                            this.getResultSet().getInt("CodiceCastFilm")
                    );
                    filmsMap.put(filmCode, film);
                }

                final CastMember member = getNewCastMember();
                castsMap.computeIfAbsent(filmCode, k -> new Cast()).addCastMember(member);
            }

            final String genreQuery = """
                    SELECT NomeGenere, CodiceFilm, Descrizione, NumeroVisualizzati
                    FROM categorizzazione_film
                    JOIN genere ON categorizzazione_film.NomeGenere = genere.Nome""";

            this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
            this.setResultSet(this.getPreparedStatement().executeQuery());

            while (this.getResultSet().next()) {
                final int filmCode = this.getResultSet().getInt(ID_FILM_NAME);
                final Genre genre = new Genre(
                        this.getResultSet().getString(GENRE_NAME),
                        this.getResultSet().getString(DESC_NAME),
                        this.getResultSet().getInt(NUM_VIEWS_NAME)
                );

                if (filmsMap.containsKey(filmCode)) {
                    filmsMap.get(filmCode).addGenre(genre);
                }
            }

            final Map<Film, Cast> detailedFilms = new HashMap<>();
            for (final Map.Entry<Integer, Film> entry : filmsMap.entrySet()) {
                final int filmCode = entry.getKey();
                final Film film = entry.getValue();
                final Cast cast = castsMap.get(filmCode);
                detailedFilms.put(film, cast);
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
                    + "casting.Codice AS CodiceCast, "
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
            final Map<Integer, Serie> seriesMap = new HashMap<>();

            while (this.getResultSet().next()) {
                final int seriesCode = this.getResultSet().getInt(ID_SERIES_NAME);

                if (!seriesMap.containsKey(seriesCode)) {
                    final Serie serie = new Serie(
                            seriesCode,
                            this.getResultSet().getString("TitoloSerie"),
                            this.getResultSet().getInt("EtaLimiteSerie"),
                            this.getResultSet().getString("TramaSerie"),
                            this.getResultSet().getInt("DurataComplessivaSerie"),
                            this.getResultSet().getInt("NumeroEpisodiSerie")
                    );
                    seriesMap.put(seriesCode, serie);
                }

                final Season season = new Season(
                        seriesCode,
                        this.getResultSet().getInt("NumeroStagione"),
                        this.getResultSet().getString("SuntoStagione"),
                        this.getResultSet().getInt("CodiceCast")
                );
                final Episode episode = new Episode(
                        seriesCode,
                        this.getResultSet().getInt("NumeroStagione"),
                        this.getResultSet().getInt("NumeroEpisodio"),
                        this.getResultSet().getInt("DurataEpisodio")
                );
                final CastMember castMember = getNewCastMember();

                final Serie serie = seriesMap.get(seriesCode);

                if (!serie.getSeasons().contains(season)) {
                    season.addEpisode(episode);
                    season.addCastMember(castMember);
                    serie.addSeason(season);
                } else {
                    final Season existingSeason = serie.getSeason(season);
                    if (!existingSeason.getEpisodes().contains(episode)) {
                        existingSeason.addEpisode(episode);
                    }
                    if (!existingSeason.getCast().getCastMemberList().contains(castMember)) {
                        existingSeason.addCastMember(castMember);
                    }
                }
            }

            final String genreQuery = """
                    SELECT NomeGenere, CodiceSerie, Descrizione, NumeroVisualizzati
                    FROM categorizzazione_serie
                    JOIN genere ON categorizzazione_serie.NomeGenere = genere.Nome""";

            this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
            this.setResultSet(this.getPreparedStatement().executeQuery());

            while (this.getResultSet().next()) {
                final int seriesCode = this.getResultSet().getInt(ID_SERIES_NAME);
                final Genre genre = new Genre(
                        this.getResultSet().getString(GENRE_NAME),
                        this.getResultSet().getString(DESC_NAME),
                        this.getResultSet().getInt(NUM_VIEWS_NAME)
                );

                if (seriesMap.containsKey(seriesCode)) {
                    seriesMap.get(seriesCode).addGenre(genre);
                }
            }

            return List.copyOf(seriesMap.values());
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }


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
        } else if (this.getResultSet().getBoolean("TipoAttoreMembroCast")
                && this.getResultSet().getBoolean("TipoRegistaMembroCast")) {
            return new CastMember(code, name, surname, birthDate, debutDate, artisticName);
        }
        throw new SQLException();
    }

    /**
     * Retrieves a list of film genres sorted by the number of views,
     * including all available information about the genres.
     *
     * @return A list of Genre objects containing film genres sorted by the number of views,
     * including all available genre details.
     * @throws IllegalArgumentException If an SQL exception occurs during query execution.
     */
    public List<Genre> getFilmGenresRanking() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT
                        genere.Nome,
                        genere.Descrizione,
                        genere.NumeroVisualizzati
                    FROM genere
                    ORDER BY genere.NumeroVisualizzati DESC""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return getGenreList();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves a list of series genres sorted by the number of views,
     * including all available information about the genres.
     *
     * @return A list of Genre objects containing series genres sorted by the number of views,
     * including all available genre details.
     * @throws IllegalArgumentException If an SQL exception occurs during query execution.
     */
    public List<Genre> getSeriesGenresRanking() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = """
                    SELECT\s
                        genere.Nome,
                        genere.Descrizione,
                        genere.NumeroVisualizzati
                    FROM genere
                    ORDER BY genere.NumeroVisualizzati DESC""";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return getGenreList();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private List<Genre> getGenreList() throws SQLException {
        final List<Genre> genres = new ArrayList<>();
        while (this.getResultSet().next()) {
            genres.add(new Genre(
                    this.getResultSet().getString("Nome"),
                    this.getResultSet().getString(DESC_NAME),
                    this.getResultSet().getInt(NUM_VIEWS_NAME)
            ));
        }
        return List.copyOf(genres);
    }
}
