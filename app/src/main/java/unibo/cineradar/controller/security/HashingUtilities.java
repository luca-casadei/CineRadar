package unibo.cineradar.controller.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

final class HashingUtilities {
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

    static String getHashedString(final String plainText,
                                  final HashingAlgorithm algorithm) throws NoSuchAlgorithmException {
        final byte[] hashedBytes = createHash(plainText, algorithm, SALT);
        return Hex.encodeHexString(hashedBytes);
    }
}
