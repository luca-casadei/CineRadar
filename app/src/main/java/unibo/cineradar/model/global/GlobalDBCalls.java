package unibo.cineradar.model.global;

import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.multimedia.Genre;

import java.util.List;

/**
 * A utility class to be used outside of a session.
 */
public final class GlobalDBCalls {
    private GlobalDBCalls() {

    }

    /**
     * Gets a list of genres.
     *
     * @return A list of currently inserted genres.
     */
    public static List<Genre> getGenres() {
        try (DBManager mgr = new DBManager()) {
            return mgr.getGenres();
        }
    }
}
