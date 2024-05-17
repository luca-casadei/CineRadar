package unibo.cineradar.model.utente;

import java.time.LocalDate;
import java.time.Period;

/**
 * The user type of account.
 */
public final class User extends Account {
    private final LocalDate birthDate;
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
                final LocalDate birthDate,
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
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /**
     * Calculates the user's current age in years.
     *
     * @return the user's current age in years.
     */
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
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
