package unibo.cineradar.model.utente;

/**
 * A type of account to be used for administration purposes.
 */
public final class Amministratore extends Account {
    private final String numeroTelefono;

    /**
     * The constructor of the administration account.
     *
     * @param username The username of the account.
     * @param nome     The first name of the account.
     * @param cognome  The last name of the account.
     */
    public Amministratore(final String username,
                          final String nome,
                          final String cognome) {
        this(username, nome, cognome, null);
    }

    /**
     * The constructor of the administration account with the phone number.
     *
     * @param username       The username of the account.
     * @param nome           The first name of the account.
     * @param cognome        The last name of the account.
     * @param numeroTelefono The phone number of the administrator.
     */
    public Amministratore(final String username,
                          final String nome,
                          final String cognome,
                          final String numeroTelefono) {
        super(username, nome, cognome);
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * Gets the phone number of the administrator's account.
     *
     * @return A string representing the phone number.
     */
    public String getNumeroTelefono() {
        return this.numeroTelefono;
    }
}
