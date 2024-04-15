package unibo.cineradar.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public final class DBManager implements AutoCloseable {
    private final Connection dbConnection;
    private final boolean connectionSuccessful;

    public DBManager() {
        Connection tmpConnection;
        boolean connectionStatus;
        try {
            tmpConnection = this.getDbConnection();
            connectionStatus = true;
        } catch (SQLException ex) {
            tmpConnection = null;
            connectionStatus = false;
        }
        this.dbConnection = tmpConnection;
        this.connectionSuccessful = connectionStatus;
    }

    private Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://"
                + DBConfig.getDbServer()
                + ":" + DBConfig.getPort()
                + "/" + DBConfig.getDBName()
                + "?user=" + DBConfig.getUsername()
                + "&password=" + DBConfig.getPassword());
    }

    public boolean hasConnectionSucceeded() {
        return this.connectionSuccessful;
    }

    public String getUserCredentials(final String username) {
        final String query = "SELECT Password FROM ACCOUNT WHERE Username = ?";
        String result;
        try {
            final PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            } else {
                result = "";
            }
        } catch (SQLException ex) {
            result = "";
        }
        return result;
    }

    @Override
    public void close() {
        Objects.requireNonNull(this.dbConnection);
        try {
            this.dbConnection.close();
        } catch (SQLException ignored) {

        }
    }
}
