package unibo.cineradar.model.cast;

import java.util.ArrayList;
import java.util.List;

public class Cast {
    private final List<CastMember> castMemberList;

    public Cast() {
        castMemberList = new ArrayList<>();
    }

    public Cast(List<CastMember> castMemberList) {
        this.castMemberList = castMemberList;
    }

    public void addCastMember(CastMember castMember) {
        this.castMemberList.add(castMember);
    }
}
