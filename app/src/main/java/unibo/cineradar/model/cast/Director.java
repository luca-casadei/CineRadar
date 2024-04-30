package unibo.cineradar.model.cast;

import java.util.Date;

public class Director extends CastMember{


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
    public Director(int id, String name, String lastname, Date birthDate, Date careerDebutDate, String stageName) {
        super(id, name, lastname, birthDate, careerDebutDate, stageName);
    }
}
