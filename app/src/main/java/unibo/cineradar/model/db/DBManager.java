package unibo.cineradar.model.db;

import java.sql.Connection;
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
    private final Connection dbConnection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    /**
     * The constructor of the database management class.
     */
    public DBManager() {
        Connection tmpDbConn = null;
        try {
            tmpDbConn = this.getDbConnection();
        } catch (SQLException ignored) {
        }
        this.dbConnection = tmpDbConn;
    }

    private Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://"
                + DBConfig.getDbServer()
                + ":" + DBConfig.getPort()
                + "/" + DBConfig.getDBName()
                + "?user=" + DBConfig.getUsername()
                + "&password=" + DBConfig.getPassword());
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
    public Optional<String> getUserCredentials(final String username) {
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
            throw (IllegalStateException) new IllegalStateException().initCause(ex);
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
            final List<String> results = new ArrayList<>();
            this.preparedStatement = this.dbConnection.prepareStatement(query);
            this.preparedStatement.setString(1, username);
            this.resultSet = this.preparedStatement.executeQuery();
            if (this.resultSet.next()) {
                results.add(this.resultSet.getString(1));
                results.add(this.resultSet.getString(2));
                results.add(this.resultSet.getString(3));
                results.add(this.resultSet.getString(4));
            }
            return List.copyOf(results);
        } catch (SQLException ex) {
            throw (IllegalArgumentException) new IllegalArgumentException().initCause(ex);
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
