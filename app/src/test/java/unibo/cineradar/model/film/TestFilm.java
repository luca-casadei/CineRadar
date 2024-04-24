package unibo.cineradar.model.film;

import org.junit.jupiter.api.Test;
import unibo.cineradar.model.db.UserOps;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestFilm {
    @Test
    void testGetFilms() {
        try (UserOps db = new UserOps()) {
            final List<Film> films = db.getFilms(16);
            assertNotNull(films);
        }
    }
}
