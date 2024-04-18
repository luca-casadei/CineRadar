package unibo.cineradar.utilities.security;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

/**
 * Utilities for hashing passwords.
 */
public final class HashingUtilities {
    private static final byte[] SALT = {127, 54, 36, 102, 19, 81, 42, 89, 30, 28, 77, 95, 13, 14, 114, 62};

    private HashingUtilities() {
    }

    private static byte[] createHash(
            final char[] plain,
            final HashingAlgorithm algorithm) throws NoSuchAlgorithmException {
        final MessageDigest digester = MessageDigest.getInstance(algorithm.getAlgorithmName());
        digester.update(HashingUtilities.SALT);
        return digester.digest(toByteArray(plain));
    }

    /**
     * Gets the hashed string using the preferred algorithm.
     *
     * @param plainText The plain text to hash.
     * @param algorithm The algorithm used to hash the plain text.
     * @return A hashed string of the plain text
     */
    public static String getHashedString(final char[] plainText,
                                         final HashingAlgorithm algorithm) {
        final byte[] hashedBytes;
        try {
            hashedBytes = createHash(plainText, algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return Hex.encodeHexString(hashedBytes);
    }

    private static byte[] toByteArray(final char[] charArray) {
        final CharBuffer charBuffer = CharBuffer.wrap(charArray);
        final ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        final byte[] byteArray = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return byteArray;
    }
}
