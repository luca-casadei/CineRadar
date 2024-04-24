package unibo.cineradar.model.context;

import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.context.registrar.RegistrarContext;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.login.Logger;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;

import java.util.Objects;
import java.util.Optional;

/**
 * Factory to create non-generic sessions.
 */
public final class SessionFactory {
    private SessionFactory() {
    }

    /**
     * Main method for the creation of the session.
     *
     * @param username The username of the account.
     * @param password The password for authentication.
     * @return Optional containing the session if the creation was successful, Optional.empty() otherwise.
     */
    public static Optional<SessionContext> getSession(final String username,
                                                      final char[] password) {
        final Account logged = Logger.logIn(username, password).orElse(null);
        if (!Objects.isNull(logged)) {
            if (logged instanceof User) {
                return Optional.of(new UserContext(logged));
            } else if (logged instanceof Administrator) {
                return Optional.of(new AdministratorContext(logged));
            } else if (logged instanceof Registrar) {
                return Optional.of(new RegistrarContext(logged));
            } else {
                throw new IllegalArgumentException("Invalid logged in user");
            }
        } else {
            return Optional.empty();
        }
    }
}
