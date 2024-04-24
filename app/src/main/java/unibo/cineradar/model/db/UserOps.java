package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    /**
     * Retrieves the list of all films.
     *
     * @param userAge The limited age to be respected.
     * @return The list of all films.
     */
    public List<Film> getFilms(final int userAge) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * "
                    + "FROM film "
                    + "WHERE film.EtaLimite <= ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, userAge);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Film> films = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Film film = new Film(
                        this.getResultSet().getInt("Codice"),
                        this.getResultSet().getString("Titolo"),
                        this.getResultSet().getInt("EtaLimite"),
                        this.getResultSet().getString("Trama"),
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
            final String query = "SELECT * "
                    + "FROM serie "
                    + "WHERE serie.EtaLimite <= ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setInt(1, userAge);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Serie> series = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Serie serie = new Serie(
                        this.getResultSet().getInt("Codice"),
                        this.getResultSet().getString("Titolo"),
                        this.getResultSet().getInt("EtaLimite"),
                        this.getResultSet().getString("Trama"),
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
}
