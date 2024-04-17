package unibo.cineradar.model.utente;

/**
 * The user type of account.
 */
public final class User extends Account {
    private final String birthDate;
    private final boolean prizeTag;

    /**
     * Constructs a user type account.
     *
     * @param username  The username of the account.
     * @param name      The name of the account.
     * @param lastName  The last name of the account.
     * @param birthDate The birthdate of the user.
     * @param prizeTag  The boolean value that states if the user is prized.
     */
    public User(final String username,
                final String name,
                final String lastName,
                final String birthDate,
                final boolean prizeTag) {
        super(username, name, lastName);
        this.birthDate = birthDate;
        this.prizeTag = prizeTag;
    }

    /**
     * Gets the birthdate of the user.
     *
     * @return A string containing the birthdate of the user.
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Checks if the user is prized.
     *
     * @return True if the user is prized, false otherwise.
     */
    public boolean isPrizeTag() {
        return prizeTag;
    }
}
