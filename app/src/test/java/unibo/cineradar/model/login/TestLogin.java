package unibo.cineradar.model.login;

import org.junit.jupiter.api.Test;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Amministratore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLogin {
    @Test
    void testAdministrationLogin() {
        final Account tmpAdm = Logger.logIn("admin", "panettone!", LoginType.ADMINISTRATION).get();
        assertTrue(tmpAdm instanceof Amministratore);
        assertEquals("1234567890", ((Amministratore) tmpAdm).getNumeroTelefono());
    }
}
