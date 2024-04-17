package unibo.cineradar.model.utente;

/**
 * The mapping of a generic account of the service.
 */
public class Account {
    private final String username;
    private final String name;
    private final String lastName;

    /**
     * The constructor to be used by the subclasses.
     *
     * @param username The username of the account.
     * @param name     The first name of the user owning the account.
     * @param lastName  The last name of the user owning the account.
     */
    protected Account(final String username,
                      final String name,
                      final String lastName) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
    }

    /**
     * Gets the username of the account.
     *
     * @return A string representing the username of the account.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * Gets the first name of the account.
     *
     * @return A string representing the first name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Gets the last name of the account.
     *
     * @return A string containing the last name of the account.
     */
    public final String getLastName() {
        return this.lastName;
    }
}
