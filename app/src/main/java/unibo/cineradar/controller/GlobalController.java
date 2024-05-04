package unibo.cineradar.controller;

import unibo.cineradar.model.global.GlobalDBCalls;
import unibo.cineradar.model.multimedia.Genre;

import java.util.List;

/**
 * Some global functions that do not depend on from the session.
 */
public final class GlobalController {
    private GlobalController() {

    }

    /**
     * Gets a list of currently inserted genres.
     *
     * @return A list of genres.
     */
    public static List<Genre> getGenreList() {
        return GlobalDBCalls.getGenres();
    }
}
