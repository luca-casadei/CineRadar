package unibo.cineradar.utilities.security;

/**
 * A list of hashing algorithms to be used for password hashing.
 */
public enum HashingAlgorithm {
    /**
     * 512 output bit hashing.
     */
    SHA_512("SHA-512"),
    /**
     * 256 output bit hashing.
     */
    SHA_256("SHA-256");
    private final String algorithmName;

    /**
     * Sets the name of the algorithm.
     *
     * @param algorithmName The name to be set.
     */
    HashingAlgorithm(final String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     * Gets the name of the algorithm to be used for hashing.
     *
     * @return A string representing the name of the algorithm.
     */
    public String getAlgorithmName() {
        return this.algorithmName;
    }
}
