package unibo.cineradar.model.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

//CHECKSTYLE: MagicNumber OFF

class TestReviewMaking {
    private static final int REVIEWED_FILM_ID = 4;
    private static final String USER = "luca";
    private static UserContext ctx;

    @BeforeAll
    static void setUpBeforeClass() {
        ctx = new UserContext(new User(
                USER,
                "Luca",
                "Casadei",
                LocalDate.of(2003, 6, 19),
                false
        ));
        assertTrue(new AdministratorContext(new Administrator(
                "admin", "Blah", "Blah"
        )).delFilmReview(REVIEWED_FILM_ID, USER));
    }

    @Test
    void makeFilmReviewTest() {
        final List<Section> sections = ctx.getSections();
        ctx.reviewFilm(REVIEWED_FILM_ID, "Ke roba ragazzi", "Molto spassoso", List.of(
                new ReviewSection(REVIEWED_FILM_ID, sections.get(0), 10)
        ));
        if (!ctx.isFilmViewed(REVIEWED_FILM_ID)) {
            assertTrue(ctx.visualizeFilm(REVIEWED_FILM_ID));
        }
        final FullFilmReview ffr = ctx.getFullFilmReview(REVIEWED_FILM_ID, USER);
        assertNotNull(ffr);
        ffr.getSections().forEach(s -> {
            assertEquals(sections.get(0).getName(), s.section().getName());
            assertEquals(10, s.score());
        });
    }
}

//CHECKSTYLE: MagicNumber ON
