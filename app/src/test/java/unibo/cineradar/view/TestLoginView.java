package unibo.cineradar.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests the login view.
 */
class TestLoginView {
    private static LogInView login;

    @BeforeAll
    static void initializeFrame() {
        login = new LogInView();
    }

    @Test
    void testCreation() {
        login.display(true);
    }

    @Test
    void testDestroy() {
        login.destroy();
    }
}
