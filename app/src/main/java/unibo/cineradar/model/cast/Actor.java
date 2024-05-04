package unibo.cineradar.model.cast;

import java.time.LocalDate;

/**
 * Represents an actor in the cast of a film or a production.
 */
public final class Actor extends CastMember {

    /**
     * Constructs a new Actor instance.
     *
     * @param id              the ID of the actor
     * @param name            the name of the actor
     * @param lastname        the last name of the actor
     * @param birthDate       the birth date of the actor
     * @param careerDebutDate the career debut date of the actor
     * @param stageName       the stage name of the actor
     */
    public Actor(final int id,
                 final String name,
                 final String lastname,
                 final LocalDate birthDate,
                 final LocalDate careerDebutDate,
                 final String stageName) {
        super(id, name, lastname, birthDate, careerDebutDate, stageName);
    }
}
