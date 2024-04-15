package unibo.cineradar.model.login;

import unibo.cineradar.controller.security.HashingAlgorithm;
import unibo.cineradar.controller.security.PasswordChecker;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.utente.Account;

import java.util.Optional;

/**
 * Class used to log the account to the application.
 */
public final class Logger {

    private Logger() {

    }

    /**
     * Logs an account.
     *
     * @param username The username of the account to log.
     * @param password The password of the account to log.
     * @return An instance of Account inside an Optional if successful, empty otherwise.
     */
    public static Optional<Account> logIn(final String username, final String password) {
        try (DBManager mgr = new DBManager()) {
            final Optional<String> gotPassword = mgr.getUserCredentials(username);
            return gotPassword.map((hashedPassword) -> {
                if (new PasswordChecker(HashingAlgorithm.SHA_512).checkPassword(password, hashedPassword)) {
                    throw new UnsupportedOperationException();
                } else {
                    return null;
                }
            });
        }
    }


}
