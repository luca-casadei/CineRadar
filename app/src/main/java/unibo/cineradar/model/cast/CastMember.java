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
    protected CastMember(final int id,
                         final String name,
                         final String lastname,
                         final Date birthDate,
                         final Date careerDebutDate,
                         final String stageName) {
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
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name of the cast member.
     *
     * @return the name of the cast member
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the last name of the cast member.
     *
     * @return the last name of the cast member
     */
    public String getLastName() {
        return this.lastname;
    }

    /**
     * Returns a copy of the birth date of the cast member.
     *
     * @return a copy of the birth date of the cast member
     */
    public Date getBirthDate() {
        return new Date(this.birthDate.getTime());
    }

    /**
     * Returns a copy of the career debut date of the cast member.
     *
     * @return a copy of the career debut date of the cast member
     */
    public Date getCareerDebutDate() {
        return new Date(this.careerDebutDate.getTime());
    }

    /**
     * Returns the stage name of the cast member.
     *
     * @return the stage name of the cast member
     */
    public String getStageName() {
        return this.stageName;
    }
}
