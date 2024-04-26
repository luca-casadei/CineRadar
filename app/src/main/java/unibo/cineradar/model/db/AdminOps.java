package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Administrator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
}
