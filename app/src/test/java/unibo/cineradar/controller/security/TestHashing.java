package unibo.cineradar.controller.security;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import unibo.cineradar.utilities.security.HashingAlgorithm;
import unibo.cineradar.utilities.security.HashingUtilities;
import unibo.cineradar.utilities.security.PasswordChecker;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestHashing {
    private static final char[] PLAIN = "nic2arlo!dsa#co2m".toCharArray();
    private static final HashingAlgorithm ALGORITHM = HashingAlgorithm.SHA_512;
    private static final String HASHED = "7ba4a8b4b449d21b0ccd4d82b60562d2abb80bb5ca61"
            + "dc6e620cbee9b5adb9ab013be961e73ef56eee0636287"
            + "fa72ade938b5b51df3b4e17a8249cfb7c7aeacb";

    @Test
    void testHashing() throws NoSuchAlgorithmException {
        assertEquals(HASHED, HashingUtilities.getHashedString(PLAIN, ALGORITHM));
    }

    @Test
    void testCheckPassword() {
        final PasswordChecker sha512pc = new PasswordChecker(ALGORITHM);
        assertTrue(sha512pc.checkPassword(PLAIN, HASHED));
        assertFalse(sha512pc.checkPassword("Ciao!".toCharArray(), HASHED));
        //Mocking an algorithm that does not exist.
        final HashingAlgorithm nonExistantAlgo = Mockito.mock(HashingAlgorithm.class);
        Mockito.when(nonExistantAlgo.getAlgorithmName()).thenReturn("Junk");
        final PasswordChecker brokenPasswordCheker = new PasswordChecker(nonExistantAlgo);
        assertThrows(IllegalArgumentException.class, () -> {
            brokenPasswordCheker.checkPassword(PLAIN, HASHED);
        });
    }

    @Test
    void testGetHashedPassword() {
        // MODIFY THIS STRING AND UNCOMMENT THE LAST METHOD CALL TO GET PASSWORD
        final String plain = "romolo";
        assertNotEquals("", HashingUtilities.getHashedString(plain.toCharArray(), ALGORITHM));

        // COMMENT LINE AFTER USE
        // assertEquals("", HashingUtilities.getHashedString(plain.toCharArray(), ALGORITHM));
    }
}
