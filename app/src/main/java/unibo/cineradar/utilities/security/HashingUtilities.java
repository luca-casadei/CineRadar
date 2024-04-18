package unibo.cineradar.utilities.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * Utilities for hashing passwords.
 */
public final class HashingUtilities {
    private static final byte[] SALT = {127, 54, 36, 102, 19, 81, 42, 89, 30, 28, 77, 95, 13, 14, 114, 62};

    private HashingUtilities() {
    }

    private static byte[] createHash(
            final String plain,
            final HashingAlgorithm algorithm,
            final byte[] salt) throws NoSuchAlgorithmException {
        final MessageDigest digester = MessageDigest.getInstance(algorithm.getAlgorithmName());
        digester.update(salt);
        return digester.digest(plain.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gets the hashed string using the preferred algorithm.
     *
     * @param plainText The plain text to hash.
     * @param algorithm The algorithm used to hash the plain text.
     * @return An hashed string of the plain text
     * @throws NoSuchAlgorithmException Exception thrown if the algorithm is not recognized.
     */
    public static String getHashedString(final String plainText,
                                         final HashingAlgorithm algorithm) {
        final byte[] hashedBytes;
        try {
            hashedBytes = createHash(plainText, algorithm, SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return Hex.encodeHexString(hashedBytes);
    }
}
