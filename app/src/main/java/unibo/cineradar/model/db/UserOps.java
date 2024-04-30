package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.review.FilmReview;
import unibo.cineradar.model.review.Review;
import unibo.cineradar.model.review.SerieReview;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            final String query = "SELECT \n"
                    + "    recensioni_totali.UsernameUtente,\n"
                    + "    recensioni_totali.CodiceFilm,\n"
                    + "    film.Titolo AS TitoloFilm,\n"
                    + "    recensioni_totali.CodiceSerie,\n"
                    + "    serie.Titolo AS TitoloSerie,\n"
                    + "    recensioni_totali.TitoloRecensione,\n"
                    + "    recensioni_totali.DescrizioneRecensione,\n"
                    + "    recensioni_totali.VotoComplessivoRecensione\n"
                    + "FROM (\n"
                    + "    SELECT \n"
                    + "        UsernameUtente,\n"
                    + "        CodiceSerie,\n"
                    + "        NULL AS CodiceFilm,\n"
                    + "        Titolo AS TitoloSerie,\n"
                    + "        NULL AS TitoloFilm,\n"
                    + "        Titolo AS TitoloRecensione,\n"
                    + "        Descrizione AS DescrizioneRecensione,\n"
                    + "        VotoComplessivo AS VotoComplessivoRecensione\n"
                    + "    FROM \n"
                    + "        recserie\n"
                    + "    UNION ALL\n"
                    + "    SELECT \n"
                    + "        UsernameUtente,\n"
                    + "        NULL AS CodiceSerie,\n"
                    + "        CodiceFilm,\n"
                    + "        NULL AS TitoloSerie,\n"
                    + "        Titolo AS TitoloFilm,\n"
                    + "        Titolo AS TitoloRecensione,\n"
                    + "        Descrizione AS DescrizioneRecensione,\n"
                    + "        VotoComplessivo AS VotoComplessivoRecensione\n"
                    + "    FROM \n"
                    + "        recfilm\n"
                    + ") AS recensioni_totali\n"
                    + "LEFT JOIN film ON recensioni_totali.CodiceFilm = film.Codice\n"
                    + "LEFT JOIN serie ON recensioni_totali.CodiceSerie = serie.Codice\n"
                    + "WHERE UsernameUtente = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Review> reviews = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Review review;
                if (this.getResultSet().getInt("CodiceFilm") != NULL
                        && this.getResultSet().getInt("CodiceSerie") == NULL) {
                    review = new FilmReview(
                            this.getResultSet().getInt("CodiceFilm"),
                            this.getResultSet().getString("TitoloFilm"),
                            this.getResultSet().getString("UsernameUtente"),
                            this.getResultSet().getString("TitoloRecensione"),
                            this.getResultSet().getString("DescrizioneRecensione"),
                            this.getResultSet().getInt("VotoComplessivoRecensione")
                    );
                } else if (this.getResultSet().getInt("CodiceFilm") == NULL
                        && this.getResultSet().getInt("CodiceSerie") != NULL) {
                    review = new SerieReview(
                            this.getResultSet().getInt("CodiceSerie"),
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
}
