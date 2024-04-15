package unibo.cineradar.model.utente;

/**
 * The mapping of a generic account of the service.
 */
public class Account {
    private final String username;
    private final String nome;
    private final String cognome;

    /**
     * The constructor to be used by the subclasses.
     *
     * @param username The username of the account.
     * @param nome     The first name of the user owning the account.
     * @param cognome  The last name of the user owning the account.
     */
    protected Account(final String username,
                      final String nome,
                      final String cognome) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
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
    public final String getNome() {
        return this.nome;
    }

    /**
     * Gets the last name of the account.
     *
     * @return A string containing the last name of the account.
     */
    public final String getCognome() {
        return this.cognome;
    }
}
