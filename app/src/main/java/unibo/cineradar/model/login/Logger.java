package unibo.cineradar.model.login;

import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;
import unibo.cineradar.utilities.security.HashingAlgorithm;
import unibo.cineradar.utilities.security.HashingUtilities;
import unibo.cineradar.utilities.security.PasswordChecker;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;

import java.sql.Date;
import java.util.List;
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
    public static Optional<Account> logIn(final String username, final char[] password, final LoginType type) {
        try (DBManager mgr = new DBManager()) {
            final Optional<String> gotPassword = mgr.getAccountCredentials(username);
            if (gotPassword.isPresent()
                    && new PasswordChecker(HashingAlgorithm.SHA_512)
                    .checkPassword(password, gotPassword.get())) {
                switch (type) {
                    case ADMINISTRATION -> {
                        final List<String> admDetails = mgr.getAdministrationDetails(username);
                        if (!admDetails.isEmpty()) {
                            return Optional.of(new Administrator(
                                    admDetails.get(0),
                                    admDetails.get(1),
                                    admDetails.get(2),
                                    admDetails.get(3)
                            ));
                        } else {
                            return Optional.empty();
                        }
                    }
                    case REGISTRATION -> {
                        final List<String> rgsDetails = mgr.getRegistrarDetails(username);
                        if (!rgsDetails.isEmpty()) {
                            return Optional.of(new Registrar(
                                    rgsDetails.get(0),
                                    rgsDetails.get(1),
                                    rgsDetails.get(2),
                                    rgsDetails.get(3),
                                    Integer.parseInt(rgsDetails.get(4))
                            ));
                        } else {
                            return Optional.empty();
                        }
                    }
                    case USER -> {
                        final List<String> userDetails = mgr.getUserDetails(username);
                        if (!userDetails.isEmpty()) {
                            return Optional.of(new User(
                                    userDetails.get(0),
                                    userDetails.get(1),
                                    userDetails.get(2),
                                    userDetails.get(3),
                                    Boolean.parseBoolean(userDetails.get(4))
                            ));
                        } else {
                            return Optional.empty();
                        }
                    }
                    default -> throw new IllegalStateException("Unhandled LoginType: " + type);
                }
            }
        }
        return Optional.empty();
    }


}
