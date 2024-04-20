package unibo.cineradar.model.login;

import unibo.cineradar.utilities.security.HashingAlgorithm;
import unibo.cineradar.utilities.security.HashingUtilities;
import unibo.cineradar.utilities.security.PasswordChecker;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.utente.Account;

import java.sql.Date;
import java.util.Optional;

/**
 * Class used to log the account to the application.
 */
public final class Logger {

    private Logger() {

    }

    /**
     * Registers a new account with the user type into the database.
     *
     * @param username      The username of the account.
     * @param plainPassword The password plain text.
     * @param name          The first name of the user.
     * @param surname       The last name of the user.
     * @param date          The birthdate of the user.
     * @return True if the insertion was successful, false otherwise.
     */
    public static boolean signIn(final String username,
                                 final char[] plainPassword,
                                 final String name,
                                 final String surname,
                                 final Date date) {
        try (DBManager mgr = new DBManager()) {
            return mgr.insertUser(username,
                    HashingUtilities.getHashedString(plainPassword, HashingAlgorithm.SHA_512),
                    name,
                    surname,
                    date);
        }
    }

    /**
     * Logs an account.
     *
     * @param username The username of the account to log.
     * @param password The password of the account to log.
     * @param type     The type of account to fetch.
     * @return An instance of Account inside an Optional if successful, empty otherwise.
     */
    public static Optional<? extends Account> logIn(final String username, final char[] password, final LoginType type) {
        try (DBManager mgr = new DBManager()) {
            final Optional<String> gotPassword = mgr.getAccountCredentials(username);
            if (gotPassword.isPresent()
                    && new PasswordChecker(HashingAlgorithm.SHA_512)
                    .checkPassword(password, gotPassword.get())) {
                switch (type) {
                    case ADMINISTRATION -> {
                        return mgr.getAdministrationDetails(username);
                    }
                    case REGISTRATION -> {
                        return mgr.getRegistrarDetails(username);
                    }
                    case USER -> {
                        return mgr.getUserDetails(username);
                    }
                    default -> throw new IllegalStateException("Unhandled LoginType: " + type);
                }
            }
        }
        return Optional.empty();
    }


}
