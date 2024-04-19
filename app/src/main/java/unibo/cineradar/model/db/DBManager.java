package unibo.cineradar.model.db;

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

    private List<String> iterateOverRowResults(final String... columNames) throws SQLException {
        final List<String> results = new ArrayList<>();
        if (this.resultSet.next()) {
            for (final String colName : columNames) {
                final String res = this.resultSet.getString(colName);
                results.add(Objects.isNull(res) ? "" : res);
            }
        }
        return List.copyOf(results);
    }

    /**
     * Gets the details of a user given its username.
     *
     * @param username The username of the user.
     * @return A list of details of the retrieved account.
     */
    public List<String> getUserDetails(final String username) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT utente.Username, Nome, Cognome, TargaPremio, utente.DataNascita "
                    + "FROM utente JOIN account "
                    + "ON utente.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            return iterateOverRowResults("Username", "Nome", "Cognome", "TargaPremio", "DataNascita");
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
    public List<String> getRegistrarDetails(final String username) {
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
            return iterateOverRowResults("Username", "Nome", "Cognome", "EmailCinema", "CodiceCinema");
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
    public List<String> getAdministrationDetails(final String username) {
        Objects.requireNonNull(this.dbConnection);
        try {
            final String query = "SELECT amministratore.Username, Nome, Cognome, NumeroTelefono "
                    + "FROM amministratore JOIN account "
                    + "ON amministratore.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            return iterateOverRowResults("Username", "Nome", "Cognome", "NumeroTelefono");
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
}
