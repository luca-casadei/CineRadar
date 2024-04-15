package unibo.cineradar.model.utente;

import unibo.cineradar.model.cinema.Cinema;

/**
 * The class of the registration account.
 */
public final class Registratore extends Account {
    private final String emailCinema;
    private final Cinema cinema;

    /**
     * Constructs a registration account.
     *
     * @param username    The username of the account.
     * @param nome        The name of the account.
     * @param cognome     The last name of the account.
     * @param emailCinema The email associated with the cinema.
     * @param cinema      The cinema instance.
     */
    public Registratore(final String username,
                        final String nome,
                        final String cognome,
                        final String emailCinema,
                        final Cinema cinema) {
        super(username, nome, cognome);
        this.emailCinema = emailCinema;
        this.cinema = cinema;
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
     * Gets the cinema associated with the registrator.
     *
     * @return The instance of the cinema associated with the registrator.
     */
    public Cinema getCinema() {
        return cinema;
    }
}
