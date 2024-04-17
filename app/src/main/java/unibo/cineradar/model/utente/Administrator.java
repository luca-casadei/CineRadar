package unibo.cineradar.model.utente;

/**
 * A type of account to be used for administration purposes.
 */
public final class Administrator extends Account {
    private final String phoneNumber;

    /**
     * The constructor of the administration account.
     *
     * @param username The username of the account.
     * @param name     The first name of the account.
     * @param lastName The last name of the account.
     */
    public Administrator(final String username,
                         final String name,
                         final String lastName) {
        this(username, name, lastName, null);
    }

    /**
     * The constructor of the administration account with the phone number.
     *
     * @param username    The username of the account.
     * @param name        The first name of the account.
     * @param lastName    The last name of the account.
     * @param phoneNumber The phone number of the administrator.
     */
    public Administrator(final String username,
                         final String name,
                         final String lastName,
                         final String phoneNumber) {
        super(username, name, lastName);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the phone number of the administrator's account.
     *
     * @return A string representing the phone number.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
