package unibo.cineradar.model.utente;

/**
 * The user type of account.
 */
public final class Utente extends Account {
    private final String dataNascita;
    private final boolean targaPremio;

    /**
     * Constructs an user type account.
     *
     * @param username    The username of the account.
     * @param nome        The name of the account.
     * @param cognome     The last name of the account.
     * @param dataNascita The birth date of the user.
     * @param targaPremio The boolean value that states if the user is prized.
     */
    public Utente(final String username,
                  final String nome,
                  final String cognome,
                  final String dataNascita,
                  final boolean targaPremio) {
        super(username, nome, cognome);
        this.dataNascita = dataNascita;
        this.targaPremio = targaPremio;
    }

    /**
     * Gets the birthdate of the user.
     *
     * @return A string containing the birthdate of the user.
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     * Checks if the user is prized.
     *
     * @return True if the user is prized, false otherwise.
     */
    public boolean isTargaPremio() {
        return targaPremio;
    }
}
