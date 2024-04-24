package unibo.cineradar.model.film;

import org.junit.jupiter.api.Test;
import unibo.cineradar.model.db.DBManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestFilm {
    @Test
    void testGetFilms() {
        try (DBManager db = new DBManager()) {
            final List<Film> films = db.getFilms(16);
            assertNotNull(films);
        }
    }
}
