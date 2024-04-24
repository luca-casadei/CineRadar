package unibo.cineradar.model.db;

import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.model.film.Film;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class used to manage database connections and queries.
 */
public final class DBManager implements AutoCloseable {
    private static final int FALLBACK_LOGIN_TIMEOUT = 5;
    private final Connection dbConnection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    /**
     * The constructor of the database management class.
     */
    public DBManager() {
        Connection tmpDbConn = null;
        try {
            DriverManager.setLoginTimeout(1);
            tmpDbConn = this.getDbConnection();
        } catch (SQLException ignored1) {
            try {
                DriverManager.setLoginTimeout(FALLBACK_LOGIN_TIMEOUT);
                tmpDbConn = this.getFallBackDbConnection();
            } catch (SQLException ignored) {
            }
        }
        this.dbConnection = tmpDbConn;
    }

    private Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.getMainConnectionString()
                + DBConfig.getLocalhost()
                + ":" + DBConfig.getPort()
                + "/" + DBConfig.getDBName()
                + "?user=" + DBConfig.getUsername()
                + "&password=" + DBConfig.getPassword());
    }

    private Connection getFallBackDbConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.getMainConnectionString()
                + DBConfig.getDbServer() + ":"
                + DBConfig.getPort() + "/"
                + DBConfig.getDBName() + "?user="
                + DBConfig.getUsername() + "&password="
                + DBConfig.getPassword());
    }

    /**
     * Checks if the application has successfully connected to the database.
     *
     * @return True if the connection was successful, false otherwise.
     */
    public boolean hasConnectionSucceeded() {
        return !Objects.isNull(this.dbConnection);
    }

    /**
     * Gets the hashed password of the selected user.
     *
     * @param username The username of the account whose password is to get.
     * @return An Optional containing a String with the hashed password if the user is found, empty otherwise.
     */
    public Optional<String> getAccountCredentials(final String username) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT Password FROM ACCOUNT WHERE Username = ?";
            final Optional<String> result;
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(resultSet.getString(1));
            } else {
                result = Optional.empty();
            }
            return result;
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Gets the details of a user given its username.
     *
     * @param username The username of the user.
     * @return A list of details of the retrieved account.
     */
    public Optional<User> getUserDetails(final String username) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT utente.Username, Nome, Cognome, TargaPremio, utente.DataNascita "
                    + "FROM utente JOIN account "
                    + "ON utente.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            if (this.resultSet.next()) {
                return Optional.of(new User(
                        this.resultSet.getString("Username"),
                        this.resultSet.getString("Nome"),
                        this.resultSet.getString("Cognome"),
                        this.resultSet.getDate("DataNascita").toLocalDate(),
                        this.resultSet.getBoolean("TargaPremio")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Gets the details of a registrar given its username.
     *
     * @param username The username of the registrar.
     * @return A list of details of the retrieved account.
     */
    public Optional<Registrar> getRegistrarDetails(final String username) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT registratore.Username, Nome, Cognome, registratore.EmailCinema, "
                    + "registratore.CodiceCinema "
                    + "FROM registratore JOIN account "
                    + "ON registratore.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            if (this.resultSet.next()) {
                return Optional.of(new Registrar(
                        this.resultSet.getString("Username"),
                        this.resultSet.getString("Nome"),
                        this.resultSet.getString("Cognome"),
                        this.resultSet.getString("EmailCinema"),
                        this.resultSet.getInt("CodiceCinema")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Gets the details of an administrator given its username.
     *
     * @param username The username of the administrator to fetch.
     * @return A list of details of the retrieved account.
     */
    public Optional<Administrator> getAdministrationDetails(final String username) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT amministratore.Username, Nome, Cognome, NumeroTelefono "
                    + "FROM amministratore JOIN account "
                    + "ON amministratore.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            if (this.resultSet.next()) {
                return Optional.of(new Administrator(
                        this.resultSet.getString("Username"),
                        this.resultSet.getString("Nome"),
                        this.resultSet.getString("Cognome"),
                        this.resultSet.getString("NumeroTelefono")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves the list of all films.
     *
     * @param userAge The limited age to be respected.
     * @return The list of all films.
     */
    public List<Film> getFilms(final int userAge) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT * "
                    + "FROM film "
                    + "WHERE film.EtaLimite <= ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setInt(1, userAge);
            this.resultSet = this.preparedStatement.executeQuery();
            final List<Film> films = new ArrayList<>();
            while (this.resultSet.next()) {
                final Film film = new Film(
                        this.resultSet.getInt("Codice"),
                        this.resultSet.getString("Titolo"),
                        this.resultSet.getInt("EtaLimite"),
                        this.resultSet.getString("Trama"),
                        this.resultSet.getInt("Durata"),
                        this.resultSet.getInt("CodiceCast")
                );
                films.add(film);
            }
            return films;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Retrieves the list of all the series.
     *
     * @param userAge The limited age to be respected.
     * @return The list of all the series.
     */
    public List<Serie> getSeries(final int userAge) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT * "
                    + "FROM serie "
                    + "WHERE serie.EtaLimite <= ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setInt(1, userAge);
            this.resultSet = this.preparedStatement.executeQuery();
            final List<Serie> series = new ArrayList<>();
            while (this.resultSet.next()) {
                final Serie serie = new Serie(
                        this.resultSet.getInt("Codice"),
                        this.resultSet.getString("Titolo"),
                        this.resultSet.getInt("EtaLimite"),
                        this.resultSet.getString("Trama"),
                        this.resultSet.getInt("DurataComplessiva"),
                        this.resultSet.getInt("NumeroEpisodi")
                );
                series.add(serie);
            }
            return series;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private boolean insertAccount(final String username,
                                  final String password,
                                  final String name,
                                  final String surname) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "INSERT INTO account(Username, Password, Nome, Cognome) VALUES(?, ?, ?, ?)";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.preparedStatement.setString(2, password);
            this.preparedStatement.setString(3, name);
            this.preparedStatement.setString(4, surname);
            this.resultSet = this.preparedStatement.executeQuery();
        } catch (SQLException ignored) {
            return false;
        }
        return true;
    }

    /**
     * Inserts a new user into the database via query.
     *
     * @param username  The username of the user.
     * @param password  The password of the user.
     * @param name      The first name of the user.
     * @param surname   The last name of the user.
     * @param birthDate The birthdate of the user.
     * @return True if the query doesn't fail, false otherwise.
     */
    public boolean insertUser(final String username,
                              final String password,
                              final String name,
                              final String surname,
                              final Date birthDate) {
        Objects.requireNonNull(this.dbConnection);
        if (insertAccount(username, password, name, surname)) {
            try {
                final String query = "INSERT INTO utente(Username, TargaPremio, DataNascita) VALUES(?, ?, ?)";
                this.preparedStatement = this.dbConnection.prepareStatement(query);
                this.preparedStatement.setString(1, username);
                this.preparedStatement.setBoolean(2, false);
                this.preparedStatement.setDate(3, birthDate);
                this.resultSet = this.preparedStatement.executeQuery();
            } catch (SQLException ignored) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Closes every connection with the autoclose.
     */
    @Override
    public void close() {
        try {
            if (!Objects.isNull(this.resultSet)) {
                this.resultSet.close();
            }
        } catch (SQLException ignored) {
        } finally {
            try {
                if (!Objects.isNull(this.preparedStatement)) {
                    this.preparedStatement.close();
                }
            } catch (SQLException ignored) {
            } finally {
                if (this.hasConnectionSucceeded()) {
                    try {
                        this.dbConnection.close();
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
    }

    /**
     * Retrieves the list of all requests.
     *
     * @return The list of all requests.
     */
    public List<Request> getRequests() {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT * "
                    + "FROM richiesta ";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.resultSet = this.preparedStatement.executeQuery();
            final List<Request> requests = new ArrayList<>();
            while (this.resultSet.next()) {
                final Request request = new Request(
                        this.resultSet.getInt("Numero"),
                        this.resultSet.getString("UsernameUtente"),
                        this.resultSet.getBoolean("Tipo"),
                        this.resultSet.getString("Titolo"),
                        this.resultSet.getString("Descrizione"),
                        this.resultSet.getBoolean("Chiusa"),
                        this.resultSet.getDate("AnnoUscita")
                );
                requests.add(request);
            }
            return requests;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
