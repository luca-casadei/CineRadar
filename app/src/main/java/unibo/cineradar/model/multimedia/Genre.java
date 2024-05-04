package unibo.cineradar.model.multimedia;

/**
 * A genre.
 *
 * @param name        The unique name of the genre.
 * @param description The long description of the genre.
 * @param viewNumber  The number of views for this genre.
 */
public record Genre(String name, String description, int viewNumber) {

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Genre g) {
            return g.name.equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
