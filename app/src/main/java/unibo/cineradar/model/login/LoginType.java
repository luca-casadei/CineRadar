package unibo.cineradar.model.login;

/**
 * Type of login to be used in the view.
 */
public enum LoginType {
    /**
     * The administrator of the application.
     */
    ADMINISTRATION,
    /**
     * User that can register a new card for users.
     */
    REGISTRATION,
    /**
     * The common user of the application.
     */
    USER
}
