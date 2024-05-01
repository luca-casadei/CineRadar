package unibo.cineradar.model.cast;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the cast of a film or a production.
 */
public final class Cast {
    private final List<CastMember> castMemberList;

    /**
     * Constructs an empty Cast instance.
     */
    public Cast() {
        castMemberList = new ArrayList<>();
    }

    /**
     * Constructs a Cast instance with the given list of cast members.
     *
     * @param castMemberList the list of cast members
     */
    public Cast(final List<CastMember> castMemberList) {
        this.castMemberList = castMemberList;
    }

    /**
     * Adds a cast member to the cast.
     *
     * @param castMember the cast member to add
     */
    public void addCastMember(final CastMember castMember) {
        this.castMemberList.add(castMember);
    }
}
