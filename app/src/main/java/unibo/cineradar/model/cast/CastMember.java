package unibo.cineradar.model.cast;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a cast member of a film or cinematographic production.
 */
public class CastMember {
    private final int id;
    private final String name;
    private final String lastname;
    private final LocalDate birthDate;
    private final LocalDate careerDebutDate;
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
    CastMember(final int id,
               final String name,
               final String lastname,
               final LocalDate birthDate,
               final LocalDate careerDebutDate,
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
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /**
     * Returns a copy of the career debut date of the cast member.
     *
     * @return a copy of the career debut date of the cast member
     */
    public LocalDate getCareerDebutDate() {
        return this.careerDebutDate;
    }

    /**
     * Returns the stage name of the cast member.
     *
     * @return the stage name of the cast member
     */
    public String getStageName() {
        return this.stageName;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    /**
     * Indicates whether some other CastMember is equal to this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CastMember castMember) {
            return castMember.id == this.id;
        } else {
            return false;
        }
    }

}
