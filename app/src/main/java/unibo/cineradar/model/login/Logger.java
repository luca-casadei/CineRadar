package unibo.cineradar.model.login;

import unibo.cineradar.utilities.security.HashingAlgorithm;
import unibo.cineradar.utilities.security.PasswordChecker;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;

import java.util.List;
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
     * @param type     The type of account to fetch.
     * @return An instance of Account inside an Optional if successful, empty otherwise.
     */
    public static Optional<Account> logIn(final String username, final String password, final LoginType type) {
        try (DBManager mgr = new DBManager()) {
            final Optional<String> gotPassword = mgr.getUserCredentials(username);
            if (gotPassword.isPresent()
                    && new PasswordChecker(HashingAlgorithm.SHA_512)
                    .checkPassword(password, gotPassword.get())) {
                switch (type) {
                    case ADMINISTRATION -> {
                        final List<String> admDetails = mgr.getAdministrationDetails(username);
                        return Optional.of(new Administrator(
                                admDetails.get(0),
                                admDetails.get(1),
                                admDetails.get(2),
                                admDetails.get(3)
                        ));
                    }
                    case REGISTRATION -> {
                        throw new UnsupportedOperationException("Not supported or implemented.");
                    }
                    case USER -> {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                    default -> {
                        throw new IllegalStateException("Unhandled LoginType: " + type);
                    }
                }
            }
        }
        return Optional.empty();
    }


}
