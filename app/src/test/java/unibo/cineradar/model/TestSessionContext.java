package unibo.cineradar.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unibo.cineradar.model.context.SessionContext;
import unibo.cineradar.model.context.SessionFactory;
import unibo.cineradar.model.login.LoginType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestSessionContext {
    private static SessionContext session;

    @BeforeAll
    static void setUpContext() {
        session = SessionFactory
                .getSession("luca", "granella".toCharArray(), LoginType.USER)
                .orElse(null);
        assertNotNull(session);
    }

    @Test
    void testAccountValues() {
        assertEquals("luca", session.getUsername());
        assertEquals("Casadei", session.getLastName());
        assertEquals("Luca", session.getFirstName());
    }
}
