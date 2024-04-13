package unibo.cineradar.model.db;

public final class DBConfig {
    private static final String DB_SERVER = "casadei.ddns.net";
    private static final String LOCALHOST = "localhost";
    private static final String PORT = "3306";
    private static final String USERNAME = "master@cineradar";
    private static final String PASSWORD = "panettone!";
    private static final String DB_NAME = "cineradar";

    private DBConfig() {
    }

    public static String getDbServer() {
        return DB_SERVER;
    }

    public static String getLocalhost() {
        return LOCALHOST;
    }

    public static String getPort() {
        return PORT;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getDBName() {
        return DB_NAME;
    }

}
