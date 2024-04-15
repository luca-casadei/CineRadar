package unibo.cineradar.model.db;

/**
 * Utility class to contain DB connection strings.
 */
public final class DBConfig {
    /**
     * Server name where the DB is located.
     */
    private static final String DB_SERVER = "casadei.ddns.net";
    /**
     * To be used if the server is unresponsive.
     */
    private static final String LOCALHOST = "localhost";
    /**
     * MariaDB port.
     */
    private static final String PORT = "3306";
    /**
     * MariaDB username.
     */
    private static final String USERNAME = "master@cineradar";
    /**
     * Password to log into MariaDB.
     */
    private static final String PASSWORD = "panettone!";
    /**
     * Name of the DB to connect to.
     */
    private static final String DB_NAME = "cineradar";

    private DBConfig() {
    }

    /**
     * Gets the DB Server name.
     *
     * @return A string representing the server name.
     */
    public static String getDbServer() {
        return DB_SERVER;
    }

    /**
     * Gets the string to connect to the local host.
     *
     * @return A string containing the localhost name.
     */
    public static String getLocalhost() {
        return LOCALHOST;
    }

    /**
     * Gets the port to connect to.
     *
     * @return A string with the port number.
     */
    public static String getPort() {
        return PORT;
    }

    /**
     * The username of the DB.
     *
     * @return A string with the username of the DB.
     */
    public static String getUsername() {
        return USERNAME;
    }

    /**
     * The password of the DB.
     *
     * @return A string containing the password of the database.
     */
    public static String getPassword() {
        return PASSWORD;
    }

    /**
     * The name of the database.
     *
     * @return A string with the name of the database.
     */
    public static String getDBName() {
        return DB_NAME;
    }

}
