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
     * Adds a cast member to the cast.
     *
     * @param castMember the cast member to add
     */
    public void addCastMember(final CastMember castMember) {
        this.castMemberList.add(castMember);
    }

    /**
     * Retrieves the cast member list.
     *
     * @return The list of cast member.
     */
    public List<CastMember> getCastMemberList() {
        return List.copyOf(castMemberList);
    }
}
