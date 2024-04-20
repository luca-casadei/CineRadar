package unibo.cineradar.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unibo.cineradar.model.utente.Administrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSessionContext {
    private static SessionContext session;

    @BeforeAll
    static void setUpContext() {
        session = new SessionContext(new Administrator("test", "Test", "Testing"));
    }

    @Test
    void notNull() {
        assertTrue(session.isValid());
        assertEquals("Testing", session.currentlyLoggedAccount().getLastName());
    }
}
