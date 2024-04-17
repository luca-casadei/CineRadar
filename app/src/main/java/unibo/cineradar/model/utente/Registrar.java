package unibo.cineradar.model.utente;

import unibo.cineradar.model.cinema.Cinema;

/**
 * The class of the registration account.
 */
public final class Registrar extends Account {
    private final String emailCinema;
    private final Cinema cinema;

    /**
     * Constructs a registration account.
     *
     * @param username    The username of the account.
     * @param name        The name of the account.
     * @param lastName    The last name of the account.
     * @param emailCinema The email associated with the cinema.
     * @param cinema      The cinema instance.
     */
    public Registrar(final String username,
                     final String name,
                     final String lastName,
                     final String emailCinema,
                     final Cinema cinema) {
        super(username, name, lastName);
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
     * Gets the cinema associated with the registration.
     *
     * @return The instance of the cinema associated with the registration.
     */
    public Cinema getCinema() {
        return cinema;
    }
}
