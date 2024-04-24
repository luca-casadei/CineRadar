package unibo.cineradar.model.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

/**
 * Class used to manage database connections and queries.
 */
public class DBManager implements AutoCloseable {
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

    /**
     * Lets the sub-operators get the statement.
     *
     * @return The current prepared statement.
     */
    protected final PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    /**
     * Sets the statement.
     *
     * @param preparedStatement The statement to set.
     */
    protected void setPreparedStatement(final PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    /**
     * Lets the sub-operators get the result set.
     *
     * @return The current prepared result set.
     */
    protected final ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Sets the result set.
     *
     * @param resultSet The result set to set.
     */
    protected void setResultSet(final ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Lets the sub-operators get the connection.
     *
     * @return The current connection.
     */
    protected final Connection getConnection() {
        Objects.requireNonNull(this.dbConnection);
        return this.dbConnection;
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
