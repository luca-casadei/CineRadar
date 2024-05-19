package unibo.cineradar.model.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.review.FullFilmReview;
import unibo.cineradar.model.review.FullSeriesReview;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//CHECKSTYLE: MagicNumber OFF

class TestReviewMaking {
    private static final int REVIEWED_FILM_ID = 4;
    private static final int REVIEWED_SERIES_ID = 2;
    private static final AdministratorContext EVIL_ADMIN_CTX = new AdministratorContext(new Administrator(
            "admin", "blah", "blah"
    ));
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
        assertTrue(EVIL_ADMIN_CTX.delSeriesReview(REVIEWED_SERIES_ID, USER));
        assertTrue(EVIL_ADMIN_CTX.delFilmReview(REVIEWED_FILM_ID, USER));
    }

    @Test
    void makeFilmReviewTest() {
        final List<Section> sections = ctx.getSections();
        if (!ctx.isFilmViewed(REVIEWED_FILM_ID)) {
            ctx.visualizeFilm(REVIEWED_FILM_ID);
        }
        ctx.reviewFilm(REVIEWED_FILM_ID, "Ke roba ragazzi", "Molto spassoso", List.of(
                new ReviewSection(REVIEWED_FILM_ID, sections.get(0), 10)
        ));
        if (!ctx.isFilmViewed(REVIEWED_FILM_ID)) {
            assertTrue(ctx.visualizeFilm(REVIEWED_FILM_ID));
        }
        final FullFilmReview ffr = ctx.getFullFilmReview(REVIEWED_FILM_ID, USER);
        assertNotNull(ffr);
        ffr.getSections().forEach(s -> {
            assertEquals(sections.get(0).name(), s.section().name());
            assertEquals(10, s.score());
        });
        final double expectedAverage = 10.0d;
        assertEquals(expectedAverage, ffr.getOverallRating());
        EVIL_ADMIN_CTX.delFilmReview(REVIEWED_FILM_ID, USER);
    }

    @Test
    void makeSeriesReviewTest() {
        /*
         * Visualizes the series
         */
        final Serie srs = ctx.getDetailedSeries().get(REVIEWED_SERIES_ID - 1);
        srs.getSeasons().forEach(season -> season.getEpisodes().forEach(episode -> {
            if (!ctx.isEpisodeViewed(episode.seriesId(), episode.seasonId(), episode.id())) {
                ctx.visualizeEpisode(
                        episode.seriesId(),
                        episode.seasonId(),
                        episode.id()
                );
            }
        }));

        final List<Section> sections = ctx.getSections();
        final List<Integer> scores = List.of(10, 8);
        ctx.reviewSeries(REVIEWED_SERIES_ID, "Ke bella sta serie", "Molto spassosa", List.of(
                new ReviewSection(REVIEWED_SERIES_ID, sections.get(0), scores.get(0)),
                new ReviewSection(REVIEWED_SERIES_ID, sections.get(1), scores.get(1))
        ));
        final FullSeriesReview fsr = ctx.getFullSeriesReview(REVIEWED_SERIES_ID, USER);
        assertNotNull(fsr);
        final List<Integer> gotScores = new ArrayList<>();
        fsr.getSections().forEach(s -> {
            gotScores.add(s.score());
        });
        assertIterableEquals(scores, gotScores);
        final int expectedAverage = scores.stream().reduce(0, Integer::sum) / scores.size();
        assertEquals(expectedAverage, fsr.getOverallRating());
        assertTrue(EVIL_ADMIN_CTX.delSeriesReview(REVIEWED_SERIES_ID, USER));
    }
}

//CHECKSTYLE: MagicNumber ON
