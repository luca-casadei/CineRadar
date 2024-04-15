package unibo.cineradar.utilities.security;

import java.security.NoSuchAlgorithmException;

/**
 * Class used for password checking.
 */
public final class PasswordChecker {
    private final HashingAlgorithm chosenAlgorithm;

    /**
     * The constructor of a password checker.
     *
     * @param algorithm The algorithm that the password checker will be using.
     */
    public PasswordChecker(final HashingAlgorithm algorithm) {
        this.chosenAlgorithm = algorithm;
    }

    /**
     * Checks if the given plain text password matches the hashed one.
     *
     * @param password       The plain text password.
     * @param hashedPassword The reference hashed password.
     * @return True if the two passwords match, false otherwise.
     */
    public boolean checkPassword(final String password, final String hashedPassword) {
        try {
            return HashingUtilities.getHashedString(
                    password,
                    this.chosenAlgorithm
            ).equals(hashedPassword);
        } catch (NoSuchAlgorithmException exception) {
            return false;
        }
    }
}
