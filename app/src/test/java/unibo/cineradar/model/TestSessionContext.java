package unibo.cineradar.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unibo.cineradar.model.utente.Amministratore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestSessionContext {
    private static SessionContext session;

    @BeforeAll
    static void setUpContext() {
        session = new SessionContext(new Amministratore("test", "Test", "Testing"));
    }

    @Test
    void notNull() {
        assertNotNull(session);
        assertEquals("Testing", session.getCurrentlyLoggedAccount().getCognome());
    }
}
