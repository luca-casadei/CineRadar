package unibo.cineradar.model.cast;

import java.util.Date;

/**
 * Represents a cast member of a film or cinematographic production.
 */
public class CastMember {
    private final int id;
    private final String name;
    private final String lastname;
    private final Date birthDate;
    private final Date careerDebutDate;
    private final String stageName;

    /**
     * Constructs a new CastMember object.
     *
     * @param id              the ID of the cast member
     * @param name            the name of the cast member
     * @param lastname        the last name of the cast member
     * @param birthDate       the birth date of the cast member
     * @param careerDebutDate the career debut date of the cast member
     * @param stageName       the stage name of the cast member
     */
    protected CastMember(int id, String name, String lastname, Date birthDate, Date careerDebutDate, String stageName) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.careerDebutDate = careerDebutDate;
        this.stageName = stageName;
    }

    /**
     * Returns the ID of the cast member.
     *
     * @return the ID of the cast member
     */
    protected int getId() {
        return id;
    }

    /**
     * Returns the name of the cast member.
     *
     * @return the name of the cast member
     */
    protected String getName() {
        return name;
    }

    /**
     * Returns the last name of the cast member.
     *
     * @return the last name of the cast member
     */
    protected String getLastname() {
        return lastname;
    }

    /**
     * Returns the birth date of the cast member.
     *
     * @return the birth date of the cast member
     */
    protected Date getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the career debut date of the cast member.
     *
     * @return the career debut date of the cast member
     */
    protected Date getCareerDebutDate() {
        return careerDebutDate;
    }

    /**
     * Returns the stage name of the cast member.
     *
     * @return the stage name of the cast member
     */
    protected String getStageName() {
        return stageName;
    }
}
