package unibo.cineradar.model.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDatabaseConnection {
    private static DBManager dbMgr;

    @BeforeAll
    static void createConnection() {
        dbMgr = new DBManager();
    }

    @AfterAll
    static void closeConnection() {
        dbMgr.close();
    }

    @Test
    void testConnectionSuccess() {
        assertTrue(dbMgr.hasConnectionSucceeded());
    }

    @Test
    void testGetQueries() throws NoSuchAlgorithmException {
        final String result = dbMgr.getAccountCredentials("admin").get();
        assertEquals("ea6dc907a62197d8b424b12d78b44dbd374fa"
                + "b1cef45b46897d9b88ebb6a8fa95453b15df2b79a26b8c25f"
                + "f79995e0d2e2c952dbcb70335daec71383a435b78f", result);
    }
}
