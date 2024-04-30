package unibo.cineradar.model.cast;

import java.util.Date;

public class Actor extends CastMember{


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
    public Actor(int id, String name, String lastname, Date birthDate, Date careerDebutDate, String stageName) {
        super(id, name, lastname, birthDate, careerDebutDate, stageName);
    }
}
