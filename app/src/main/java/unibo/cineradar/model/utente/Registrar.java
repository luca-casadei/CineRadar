package unibo.cineradar.model.utente;

/**
 * The class of the registration account.
 */
public final class Registrar extends Account {
    private final String emailCinema;
    private final int codCinema;

    /**
     * Constructs a registration account.
     *
     * @param username    The username of the account.
     * @param name        The name of the account.
     * @param lastName    The last name of the account.
     * @param emailCinema The email associated with the cinema.
     * @param codCinema   The cinema code.
     */
    public Registrar(final String username,
                     final String name,
                     final String lastName,
                     final String emailCinema,
                     final int codCinema) {
        super(username, name, lastName);
        this.emailCinema = emailCinema;
        this.codCinema = codCinema;
    }

    /**
     * Gets the cinema email.
     *
     * @return A string containing the email associated with the cinema.
     */
    public String getEmailCinema() {
        return emailCinema;
    }

    /**
     * Gets the cinema code associated with the registration.
     *
     * @return The code of the cinema associated with the registration.
     */
    public int getCinema() {
        return codCinema;
    }
}
