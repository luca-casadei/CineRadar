package unibo.cineradar.model.context;

import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.context.registrar.RegistrarContext;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.login.Logger;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;

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
     * @param username  The username of the account.
     * @param password  The password for authentication.
     * @param loginType The type of the login.
     * @return Optional containing the session if the creation was successful, Optional.empty() otherwise.
     */
    public static Optional<SessionContext> getSession(final String username,
                                                      final char[] password,
                                                      final LoginType loginType) {
        final Account logged = Logger.logIn(username, password, loginType).orElse(null);
        if (!Objects.isNull(logged)) {
            switch (loginType) {
                case ADMINISTRATION -> {
                    return Optional.of(new AdministratorContext(logged));
                }
                case REGISTRATION -> {
                    return Optional.of(new RegistrarContext(logged));
                }
                case USER -> {
                    return Optional.of(new UserContext(logged));
                }
                default -> {
                    throw new IllegalArgumentException("Invalid login type: " + loginType);
                }
            }
        } else {
            return Optional.empty();
        }
    }
}
