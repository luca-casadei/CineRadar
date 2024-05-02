package unibo.cineradar.model.cast;

import java.time.LocalDate;

/**
 * Represents a director in the cast of a film or a production.
 */
public class Director extends CastMember {


    /**
     * Constructs a new Director instance.
     *
     * @param id              the ID of the director
     * @param name            the name of the director
     * @param lastname        the last name of the director
     * @param birthDate       the birth date of the director
     * @param careerDebutDate the career debut date of the director
     * @param stageName       the stage name of the director
     */
    public Director(final int id,
                    final String name,
                    final String lastname,
                    final LocalDate birthDate,
                    final LocalDate careerDebutDate,
                    final String stageName) {
        super(id, name, lastname, birthDate, careerDebutDate, stageName);
    }
}
